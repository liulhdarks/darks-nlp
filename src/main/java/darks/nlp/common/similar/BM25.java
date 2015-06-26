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
package darks.nlp.common.similar;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.jblas.DoubleMatrix;

import darks.learning.common.utils.FreqCount;
import darks.nlp.common.beans.Sentence;
import darks.nlp.common.beans.Term;

public class BM25
{
	
	private static final double SMOOTH = 0.5;
	
	private Map<String, Double> idfMap = null;
	
	private long totalDocsCount;
	
	private int avgDocLength;
	
	private FreqCount<String>[] termDocsFreq;
	
	private double b = 0.75;
	
	private double k1 = 2;
	
	public BM25()
	{
		
	}
	
	@SuppressWarnings("unchecked")
	public void initialize(Collection<Sentence> docs)
	{
		FreqCount<String> termDocFreq = new FreqCount<String>();
		idfMap = new HashMap<String, Double>();
		totalDocsCount = docs.size();
		avgDocLength = 0;
		int docIndex = 0;
		termDocsFreq = new FreqCount[docs.size()];
		for (Sentence doc : docs)
		{
			Set<String> repeat = new HashSet<String>();
			avgDocLength += doc.getTerms().size();
			for (Term term : doc.getTerms())
			{
				String name = term.getName();
				if (!repeat.contains(name))
				{
					termDocFreq.addValue(name);
					repeat.add(name);
				}
				termDocsFreq[docIndex].addValue(name);
			}
			docIndex++;
		}
		avgDocLength /= totalDocsCount;
		Iterator<Entry<String, Long>> it = termDocFreq.entrySetIterator();
		while (it.hasNext())
		{
			Entry<String, Long> entry = it.next();
			String name = entry.getKey();
			long count = entry.getValue();
			double idf = Math.log((totalDocsCount - count + SMOOTH) / (count + SMOOTH));
			idfMap.put(name, idf);
		}
	}
	
	/**
	 * IDF(qi)*(fi*(k1+1)/(fi+k1*(1-b+b*dl/avgdl)))
	 * @param sentence
	 * @param docIndex
	 * @return
	 */
	public double compute(Sentence sentence, int docIndex)
	{
		if (idfMap == null || sentence == null || sentence.getTerms() == null)
			return 0;
		int docLength = sentence.getTerms().size();
		double score = 0;
		for (Term term : sentence.getTerms())
		{
			String name = term.getName();
			double freqTermInDoc = termDocsFreq[docIndex].getValue(name);
			Double idf = idfMap.get(name);
			if (idf == null)
				continue;
			score += idf * freqTermInDoc * (k1 + 1) / 
					(freqTermInDoc + k1 * (1 - b + b * (double)docLength / (double)avgDocLength));
		}
		return score;
	}
	
	public DoubleMatrix computeAll(Sentence sentence)
	{
		if (idfMap == null)
			return null;
		DoubleMatrix result = new DoubleMatrix((int)totalDocsCount);
		for (int i = 0; i < totalDocsCount; i++)
		{
			double s = compute(sentence, i);
			result.put(i, s);
		}
		return result;
	}
}
