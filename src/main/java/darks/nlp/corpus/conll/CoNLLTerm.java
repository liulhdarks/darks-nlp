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

import darks.nlp.common.NatureTagger.NatureTag;
import darks.nlp.common.beans.Term;
import darks.nlp.utils.StringUtils;

public class CoNLLTerm extends Term
{
	private static final long serialVersionUID = 3489056720200601822L;

	int id;
	
	String lemma;
	
	NatureTag coarseNatureTag;
	
	String feature;
	
	int centerId;
	
	boolean isRoot;
	
	String label;
	
	public CoNLLTerm()
	{
		
	}
	
	public CoNLLTerm(String name, NatureTag natureTag)
	{
		super(name, natureTag);
	}

	public boolean parse(String line)
	{
		String[] params = line.split("\t");
		if (params.length < 8)
			return false;
		id = Integer.parseInt(params[0].trim());
		name = params[1].trim();
		lemma = params[2].trim();
		coarseNatureTag = NatureTag.valueOf(params[3].replace("-", "_").trim());
		natureTag = NatureTag.valueOf(params[4].replace("-", "_").trim());
		feature = params[5].trim();
		centerId = Integer.parseInt(params[6].trim());
		label = params[7].trim();
		return true;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getLemma()
	{
		return lemma;
	}

	public NatureTag getCoarseNatureTag()
	{
		return coarseNatureTag;
	}

	public String getFeature()
	{
		return feature;
	}

	public int getCenterId()
	{
		return centerId;
	}

	public boolean isRoot()
	{
		return isRoot;
	}

	public String getLabel()
	{
		return label;
	}

	public void setCenterId(int centerId)
	{
		this.centerId = centerId;
	}

	public void setRoot(boolean isRoot)
	{
		this.isRoot = isRoot;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	
	
	public void setId(int id)
	{
		this.id = id;
	}

	public void setLemma(String lemma)
	{
		this.lemma = lemma;
	}

	public void setCoarseNatureTag(NatureTag coarseNatureTag)
	{
		this.coarseNatureTag = coarseNatureTag;
	}

	public void setFeature(String feature)
	{
		this.feature = feature;
	}

	@Override
	public String toString()
	{
		return StringUtils.stringBuffer(id, '\t', name, '\t', lemma, '\t', coarseNatureTag, '\t', natureTag, 
				'\t', centerId, '\t', label);
	}
	
	
	
}
