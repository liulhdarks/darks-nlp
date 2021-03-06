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
package darks.nlp.common.beans;

import java.util.Collection;

public class Sentence
{
	
	private String content;

	private Collection<Term> terms;
	
	public Sentence()
	{
		
	}
	
	public Sentence(Collection<Term> terms)
	{
		this.terms = terms;
	}

	public Collection<Term> getTerms()
	{
		return terms;
	}

	public void setTerms(Collection<Term> terms)
	{
		this.terms = terms;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	@Override
	public String toString()
	{
		return "Sentence [content=" + content + ", terms=" + terms + "]";
	}
	
	
}
