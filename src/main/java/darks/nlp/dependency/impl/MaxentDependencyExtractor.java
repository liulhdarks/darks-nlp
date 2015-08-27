/**
 * 
 * Copyright 2015 The Darks NLP Project (Liu lihua)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package darks.nlp.dependency.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import darks.nlp.common.beans.Term;
import darks.nlp.corpus.conll.CoNLLCorpus;
import darks.nlp.corpus.conll.CoNLLSentence;
import darks.nlp.corpus.conll.CoNLLTerm;
import darks.nlp.utils.StringUtils;
import darks.nlp.utils.ThreadUtils;

/**
 * Maxent dependency feature extractor
 * 
 * @author lihua.llh
 *
 */
public class MaxentDependencyExtractor
{

	private static final Logger log = LoggerFactory.getLogger(MaxentDependencyExtractor.class);
	
	public static final String DEFAULT_LABEL = "null";
	
	private static final int WIN_SIZE = 2;
	
	private static final String BLANK_TERM = "##空白##";
	
	private static final String ROOT_TERN = "##核心##";
	
	private static final String BLANK_NATURE = "null";
	
	private static final String ROOT_NATURE = "root";
	
	private long logDelay = 10000;
	
	public MaxentDependencyExtractor()
	{
		
	}
	
	public List<MaxentDependencyFeature> extractTrainFeature(CoNLLCorpus corpus, int threadCount)
	{
		List<MaxentDependencyFeature> features = null;
		if (threadCount <= 1)
			features = extractTrainFeatureSingleThread(corpus);
		else
			features = extractTrainFeatureMultiThread(corpus, threadCount);
		return features;
	}
	
	private List<MaxentDependencyFeature> extractTrainFeatureSingleThread(CoNLLCorpus corpus)
	{
		log.info("Start to extract dependency train feature.senteces count " + corpus.getSentences().size());
		long st = System.currentTimeMillis();
		List<MaxentDependencyFeature> result = new LinkedList<MaxentDependencyFeature>();
		List<CoNLLSentence> sentences = corpus.getSentences();
		for (int p = 0; p < sentences.size(); p++)
		{
			CoNLLSentence sentence = sentences.get(p);
			extractSentenceFeature(result, sentence);
			if (System.currentTimeMillis() - st > 10000)
			{
				st = System.currentTimeMillis();
				log.info("Extracting dependency progress " + (p + 1) + "/" + sentences.size());
			}
		}
		return result;
	}
	
	private List<MaxentDependencyFeature> extractTrainFeatureMultiThread(CoNLLCorpus corpus, int threadCount)
	{
		log.info("Start to extract dependency train feature by multi-thread.senteces count " + corpus.getSentences().size());
		List<MaxentDependencyFeature> result = new LinkedList<MaxentDependencyFeature>();
		ExecutorService threadPool = Executors.newCachedThreadPool();
		List<CoNLLSentence> sentences = corpus.getSentences();
		int pageSize = sentences.size() / threadCount;
		List<Future<List<MaxentDependencyFeature>>> futures = new ArrayList<Future<List<MaxentDependencyFeature>>>(threadCount);
		for (int t = 0; t < threadCount; t++)
		{
			int start = t * pageSize;
			int end = start + pageSize;
			if (t == threadCount - 1 && end < sentences.size() - 1)
				end = sentences.size() - 1;
			futures.add(threadPool.submit(new ExtractThread(start, end, sentences)));
		}
		for (Future<List<MaxentDependencyFeature>> future : futures)
		{
			try
			{
				List<MaxentDependencyFeature> list = future.get();
				result.addAll(list);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private void extractSentenceFeature(List<MaxentDependencyFeature> result, CoNLLSentence sentence)
	{
		List<CoNLLTerm> terms = sentence.getTerms();
		for (int i = 0; i < terms.size(); i++)
		{
			CoNLLTerm curTerm = terms.get(i);
			for (int j = 0; j < terms.size(); j++)
			{
				if (i == j)
					continue;
				MaxentDependencyFeature feature = new MaxentDependencyFeature();
				feature.feature = extractWordsFeature(terms, i, j);
				if (curTerm.getCenterId() == j)
					feature.label = curTerm.getLabel();
				else
					feature.label = DEFAULT_LABEL;
				result.add(feature);
			}
		}
	}
	
	public List<String> extractWordsFeature(List<? extends Term> terms, int curIndex, int targetIndex)
	{
		List<String> feature = new LinkedList<String>();
		for (int i = curIndex - WIN_SIZE; i <= curIndex + WIN_SIZE; i++)
		{
			String[] infos = getTermNameNature(terms, i);
			String name = infos[0];
			String nature = infos[1];
			int offset = i - curIndex;
			feature.add(StringUtils.stringBuffer(name, 'i', offset));
			feature.add(StringUtils.stringBuffer(nature, 'i', offset));
		}
		for (int i = targetIndex - WIN_SIZE; i <= targetIndex + WIN_SIZE; i++)
		{
			String[] infos = getTermNameNature(terms, i);
			String name = infos[0];
			String nature = infos[1];
			int offset = i - targetIndex;
			feature.add(StringUtils.stringBuffer(name, 'j', offset));
			feature.add(StringUtils.stringBuffer(nature, 'j', offset));
		}
		int distance = curIndex - targetIndex;
		String[] curTerm = getTermNameNature(terms, curIndex);
		String[] targetTerm = getTermNameNature(terms, targetIndex);
		String[] prevCurTerm = getTermNameNature(terms, curIndex - 1);
		String[] prevTargetTerm = getTermNameNature(terms, targetIndex - 1);
		
		feature.add(StringUtils.stringBuffer(curTerm[0], '→', targetTerm[0]));
		feature.add(StringUtils.stringBuffer(curTerm[1], '→', targetTerm[1]));
		feature.add(StringUtils.stringBuffer(curTerm[0], '→', targetTerm[0], distance));
		feature.add(StringUtils.stringBuffer(curTerm[1], '→', targetTerm[1], distance));
		
		feature.add(StringUtils.stringBuffer(prevCurTerm[0], '@', curTerm[0], '→', targetTerm[0]));
		feature.add(StringUtils.stringBuffer(curTerm[0], '→', prevTargetTerm[0], '@', targetTerm[0]));
		feature.add(StringUtils.stringBuffer(prevCurTerm[1], '@', curTerm[1], '→', targetTerm[1]));
		feature.add(StringUtils.stringBuffer(curTerm[1], '→', prevTargetTerm[1], '@', targetTerm[1]));
		return feature;
	}
	
	private String[] getTermNameNature(List<? extends Term> terms, int index)
	{
		String name = null;
		String nature = null;
		if (index < 0 || index >= terms.size())
		{
			name = BLANK_TERM;
			nature = BLANK_NATURE;
		}
		else
		{
			Term term = terms.get(index);
			if (term instanceof CoNLLTerm)
			{
				CoNLLTerm conllTerm = (CoNLLTerm) term;
				if (conllTerm.isRoot())
				{
					name = ROOT_TERN;
					nature = ROOT_NATURE;
				}
			}
			if (name == null)
				name = term.getName();
			if (nature == null)
				nature = term.getNatureTag().toString();
		}
		return new String[]{name, nature};
	}



	class MaxentDependencyFeature
	{
		List<String> feature;
		
		String label;
	}
	
	class ExtractThread implements Callable<List<MaxentDependencyFeature>>
	{
		
		int start;
		
		int end;
		
		List<CoNLLSentence> sentences;
		
		public ExtractThread(int start, int end, List<CoNLLSentence> sentences)
		{
			this.start = start;
			this.end = end;
			this.sentences = sentences;
		}

		@Override
		public List<MaxentDependencyFeature> call() throws Exception
		{
			log.info(Thread.currentThread().getName() + " Extracting dependency start (" +  + start + ":" + end + ")");
			List<MaxentDependencyFeature> result = new LinkedList<MaxentDependencyFeature>();
			long st = System.currentTimeMillis();
			int count = end - start + 1;
			for (int p = start; p <= end; p++)
			{
				CoNLLSentence sentence = sentences.get(p);
				extractSentenceFeature(result, sentence);
				if (System.currentTimeMillis() - st > logDelay)
				{
					ThreadUtils.threadSleep(100);
					st = System.currentTimeMillis();
					int index = p - start + 1;
					int per = (int)((float) index / (float) count * 100.f);
					log.info(Thread.currentThread().getName() + " Extracting dependency progress " + p + "/(" +  + start + ":" + end + ")" + per + "%");
				}
			}
			log.info(Thread.currentThread().getName() + " Extracting dependency finished.result count " + result.size());
			return result;
		}
	}

	public long getLogDelay()
	{
		return logDelay;
	}

	public void setLogDelay(long logDelay)
	{
		this.logDelay = logDelay;
	}
	
}
