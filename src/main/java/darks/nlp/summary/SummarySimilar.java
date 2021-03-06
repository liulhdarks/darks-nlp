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

/**
 * Summary similar abstract class
 * 
 * @author lihua.llh
 *
 */
public abstract class SummarySimilar
{
	
	protected Collection<Sentence> sentences;
	
	public SummarySimilar()
	{
	}
	
	public SummarySimilar(Collection<Sentence> sentences)
	{
		this.sentences = sentences;
	}
	
	public void initialize(Collection<Sentence> sentences)
	{
		
	}

	public abstract DoubleMatrix similar(Sentence sentence);
	
}
