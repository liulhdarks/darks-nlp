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
package darks.nlp.summary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.jblas.DoubleMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import darks.nlp.common.beans.Sentence;

/**
 * Extract summary of document by text rank algorithm.
 * 
 * @author lihua.llh
 *
 */
public class TextRankSummary
{
	
	private static final Logger log = LoggerFactory.getLogger(TextRankSummary.class);
	
	private static final double DEFAULT_D = 0.85;
	
    final static double MIN_ERROR = 0.001;
	
	private SummarySimilar similar;
	
	private DoubleMatrix vector;

	private DoubleMatrix weights;
	
	private DoubleMatrix weightsSum;
	
	private int sentenceCount;
	
	private TreeMap<Double, Integer> indexSortMap;
	
	private List<Sentence> sentences;
	
	public TextRankSummary()
	{
		this(new BM25SummarySimilar());
	}
	
	public TextRankSummary(SummarySimilar similar)
	{
		this.similar = similar;
	}

	/**
	 * Initialize text rank summary
	 * 
	 * @param docs
	 */
	public void initialize(List<Sentence> sentences)
	{
		this.sentences = sentences;
		similar.initialize(sentences);
		sentenceCount = sentences.size();
		vector = DoubleMatrix.ones(sentenceCount);
		weightsSum = new DoubleMatrix(sentenceCount);
		weights = DoubleMatrix.zeros(sentenceCount, sentenceCount);
		int index = 0;
		for (Sentence sentence : sentences)
		{
			DoubleMatrix matrix = similar.similar(sentence);
			weights.putRow(index, matrix);
			weightsSum.put(index, matrix.sum() - matrix.get(index));
			index++;
		}
	}
	
	/**
	 * Iterate text tank
	 * 
	 * @param iterateNumber Iterate Number
	 * @return
	 */
	public boolean iteration(int iterateNumber)
	{
		return iteration(iterateNumber, DEFAULT_D);
	}
	/**
	 * Iterate text tank
	 * 
	 * @param iterateNumber Iterate Number
	 * @param d Suggest 0.85
	 * @return
	 */
	public boolean iteration(int iterateNumber, double d)
	{
		for (int epoch = 1; epoch <= iterateNumber; epoch++)
		{
//			log.debug("Iterating number " + epoch);
			double error = 0;
			DoubleMatrix output = DoubleMatrix.zeros(sentenceCount);
			for (int i = 0; i < sentenceCount; i++)
			{
				double m = 1 - d;
				for (int j = 0; j < sentenceCount; j++)
				{
					if (j == i || Double.compare(weightsSum.get(j), 0) == 0)
						continue;
					m += d * weights.get(j, i) / weightsSum.get(j) * vector.get(j);
				}
				output.put(i, m);
				double diff = Math.abs(m - vector.get(i));
				if (Double.compare(diff, error) > 0)
				{
					error = diff;
				}
			}
			vector = output;
			if (error <= MIN_ERROR) break;
		}
		return true;
	}
	
	public List<Integer> getTopIndexList(int topSize)
	{
		buildSortTop();
		List<Integer> result = new ArrayList<Integer>(topSize);
		for (Integer index : indexSortMap.values())
		{
			result.add(index);
			if (result.size() >= topSize)
				break;
		}
		return result;
	}
	
	public List<Sentence> getTopSentenceList(int topSize)
	{
		List<Integer> ids = getTopIndexList(topSize);
		List<Sentence> result = new ArrayList<Sentence>(topSize);
		for (Integer id : ids)
		{
			result.add(sentences.get(id));
		}
		return result;
	}
	
	private void buildSortTop()
	{
		if (indexSortMap != null)
			return;
		indexSortMap = new TreeMap<Double, Integer>(Collections.reverseOrder());
		for (int i = 0; i < sentenceCount; i++)
		{
			indexSortMap.put(vector.get(i), i);
		}
	}
}
