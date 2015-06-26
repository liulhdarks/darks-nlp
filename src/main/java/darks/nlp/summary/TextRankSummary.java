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
import darks.nlp.common.similar.BM25;

/**
 * Extract summary of document by text rank algorithm.
 * 
 * @author lihua.llh
 *
 */
public class TextRankSummary
{
	
	BM25 bm25;
	
	DoubleMatrix vector;
	
	public TextRankSummary()
	{
		
	}

	/**
	 * Initialize text rank summary
	 * 
	 * @param docs
	 */
	public void initialize(Collection<Sentence> docs)
	{
		
	}
}
