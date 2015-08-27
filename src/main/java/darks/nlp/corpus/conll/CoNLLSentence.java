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
package darks.nlp.corpus.conll;

import java.util.ArrayList;
import java.util.List;

import darks.nlp.common.beans.Term;

public class CoNLLSentence
{
	List<CoNLLTerm> terms = new ArrayList<CoNLLTerm>();
	
	public CoNLLSentence()
	{
		CoNLLTerm rootTerm = new CoNLLTerm();
		rootTerm.isRoot = true;
		rootTerm.id = 0;
		rootTerm.centerId = -1;
		terms.add(rootTerm);
	}
	
	public void addTerm(CoNLLTerm term)
	{
		terms.add(term);
	}
	
	public void addTerm(Term term)
	{
		terms.add(new CoNLLTerm(term.getName(), term.getNatureTag()));
	}
	
	public void addTermLine(String line)
	{
		CoNLLTerm term = new CoNLLTerm();
		if (term.parse(line))
			terms.add(term);
	}

	public List<CoNLLTerm> getTerms()
	{
		return terms;
	}
	
	
}
