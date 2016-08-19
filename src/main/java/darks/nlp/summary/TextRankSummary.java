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
import java.util.List;
import java.util.TreeSet;

import org.jblas.DoubleMatrix;

import darks.nlp.common.beans.Sentence;

/**
 * Extract summary of document by text rank algorithm.
 * 
 * @author lihua.llh
 *
 */
public class TextRankSummary
{
	
	private static final double DEFAULT_D = 0.85;
	
    final static double MIN_ERROR = 0.001;
	
	private SummarySimilar similar;
	
	private DoubleMatrix vector;

	private DoubleMatrix weights;
	
	private DoubleMatrix weightsSum;
	
	private int sentenceCount;
	
//	private TreeMap<Double, Integer> indexSortMap;
	private TreeSet<SortIndex> indexSortSet;
	
	private List<Sentence> sentences;
	
	SummaryFilter summaryFilter = new DefaultSummaryFilter();
	
	public TextRankSummary()
	{
		this(new BM25SummarySimilar());
	}
	
	public TextRankSummary(SummarySimilar similar)
	{
		this.similar = similar;
	}
    
    public TextRankSummary(SummarySimilar similar, SummaryFilter summaryFilter)
    {
        this.similar = similar;
        this.summaryFilter = summaryFilter;
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
		for (SortIndex si : indexSortSet)
		{
			result.add(si.index);
			if (result.size() >= topSize)
				break;
		}
		return result;
	}
    
    public List<Sentence> getTopSentenceList(int topSize)
    {
        buildSortTop();
        List<Sentence> result = new ArrayList<Sentence>(topSize);
        for (SortIndex si : indexSortSet)
        {
            Sentence sentence = sentences.get(si.index);
            if (summaryFilter.filter(sentence))
                continue;
            result.add(sentence);
            if (result.size() >= topSize)
                break;
        }
        return result;
    }
	
	private void buildSortTop()
	{
	    if (indexSortSet != null)
	        return;
	    indexSortSet = new TreeSet<SortIndex>();
	    for (int i = 0; i < sentenceCount; i++)
	    {
	        indexSortSet.add(new SortIndex(i, vector.get(i)));
	    }
	}
	
	class SortIndex implements Comparable<SortIndex>{
	    
	    int index;
	    double weight;
	    
        public SortIndex(int index, double weight) {
            this.index = index;
            this.weight = weight;
        }

        @Override
        public int compareTo(SortIndex o) {
            int p = Double.compare(o.weight, weight);
            if (p == 0) {
                p = index - o.index;
            }
            return p;
        }
	    
	}
	
	static class DefaultSummaryFilter implements SummaryFilter {

        @Override
        public boolean filter(Sentence sentence) {
            String content = sentence.getContent();
            if (content == null || content.length() < 5)
                return true;
            else
                return false;
        }
	    
	}
}
