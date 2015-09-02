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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import darks.nlp.common.beans.Keyword;

/**
 * Text rank keywords
 * 
 * @author lihua.llh
 *
 */
public class TextRankKeywords
{

	private static final int WIN_SIZE = 5;

	final static double D = 0.85;

	final static double MIN_DIFF = 0.001;

	Map<String, Set<String>> wordsMap = new HashMap<String, Set<String>>();
	
	List<Map.Entry<String, Double>> sortIndexList;

	public void extract(List<String> terms, int iterationCount)
	{
		addWords(terms);
		extract(iterationCount);
	}

	public void extract(int iterationCount)
	{
		Map<String, Double> score = new HashMap<String, Double>();
		for (int p = 0; p < iterationCount; p++)
		{
			Map<String, Double> m = new HashMap<String, Double>();
			double maxDiff = 0;
			for (Map.Entry<String, Set<String>> entry : wordsMap.entrySet())
			{
				String key = entry.getKey();
				Set<String> value = entry.getValue();
				m.put(key, 1 - D);
				for (String element : value)
				{
					int size = wordsMap.get(element).size();
					if (key.equals(element) || size == 0)
						continue;
					m.put(key, m.get(key) + D / size * (score.get(element) == null ? 0 : score.get(element)));
				}
				maxDiff = Math.max(maxDiff,
						Math.abs(m.get(key) - (score.get(key) == null ? 0 : score.get(key))));
			}
			score = m;
			if (maxDiff <= MIN_DIFF)
				break;
		}
		buildSortIndex(score);
	}
	
	private void buildSortIndex(Map<String, Double> score)
	{
		sortIndexList = new ArrayList<Map.Entry<String, Double>>(score.entrySet());
        Collections.sort(sortIndexList, new Comparator<Map.Entry<String, Double>>()
        {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2)
            {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
	}
	
	public Set<Keyword> getKeywords(int topSize)
	{
		Set<Keyword> keywords = new LinkedHashSet<Keyword>();
		for (int i = 0; i < Math.min(topSize, sortIndexList.size()); i++)
		{
			Entry<String, Double> entry = sortIndexList.get(i);
			keywords.add(new Keyword(entry.getKey(), entry.getValue()));
		}
		return keywords;
	}

	public void addWords(List<String> terms)
	{
		int termSize = terms.size();
		for (int i = 0; i < termSize; i++)
		{
			String curTerm = terms.get(i);
			for (int j = i - 1; j >= Math.max(0, i - WIN_SIZE + 1); j--)
			{
				String winTerm = terms.get(j);
				getTermSet(curTerm).add(winTerm);
				getTermSet(winTerm).add(curTerm);
			}
			for (int j = i + 1; j < Math.min(i + WIN_SIZE, termSize); j++)
			{
				String winTerm = terms.get(j);
				getTermSet(curTerm).add(winTerm);
				getTermSet(winTerm).add(curTerm);
			}
		}
	}

	private Set<String> getTermSet(String term)
	{
		Set<String> termSet = wordsMap.get(term);
		if (termSet == null)
		{
			termSet = new HashSet<String>();
			wordsMap.put(term, termSet);
		}
		return termSet;
	}

}
