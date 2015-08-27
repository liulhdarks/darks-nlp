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

import java.io.Serializable;

import darks.nlp.common.NatureTagger.NatureTag;

public class Term implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8618127000528330714L;
	
	protected String name;
	
	protected NatureTag natureTag;
	
	public Term()
	{
		
	}
	
	public Term(String name)
	{
		this.name = name;
	}
	
	public Term(String name, NatureTag natureTag)
	{
		this.name = name;
		this.natureTag = natureTag;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public NatureTag getNatureTag()
	{
		return natureTag;
	}

	public void setNatureTag(NatureTag natureTag)
	{
		this.natureTag = natureTag;
	}
	
	
}
