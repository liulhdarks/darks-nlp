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

import java.util.Collection;

import org.jblas.DoubleMatrix;

import darks.nlp.common.beans.Sentence;
import darks.nlp.common.similar.JaroWinkler;

/**
 * 
 * @author lihua.llh
 *
 */
public class JaroWinklerSummarySimilar extends SummarySimilar
{

	Collection<Sentence> sentences;
	
	@Override
	public void initialize(Collection<Sentence> sentences)
	{
		this.sentences = sentences;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DoubleMatrix similar(Sentence sentence)
	{
		int index = 0;
		DoubleMatrix matrix = new DoubleMatrix(sentences.size());
		for (Sentence target : sentences)
		{
			double v = JaroWinkler.similar(target.getContent(), sentence.getContent());
			matrix.put(index++, v);
		}
		return matrix;
	}

}
