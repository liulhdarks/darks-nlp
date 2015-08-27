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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import darks.learning.common.utils.IOUtils;

/**
 * CoNLL format corpus
 * 
 * @author lihua.llh
 *
 */
public class CoNLLCorpus
{
	
	private List<CoNLLSentence> sentences = null;

	public CoNLLCorpus()
	{
		sentences = new LinkedList<CoNLLSentence>();
	}
	
	public static CoNLLCorpus loadFromFile(File file) throws IOException
	{
		return loadFromStream(new FileInputStream(file));
	}
	
	public static CoNLLCorpus loadFromStream(InputStream ins) throws IOException
	{
		BufferedReader reader = null;
		try
		{
			CoNLLCorpus corpus = new CoNLLCorpus();
			CoNLLSentence curSentence = null;
			reader = new BufferedReader(new InputStreamReader(ins));
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				line = line.trim();
				if ("".equals(line))
				{
					curSentence = null;
					continue;
				}
				if (curSentence == null)
				{
					curSentence = new CoNLLSentence();
					corpus.sentences.add(curSentence);
				}
				curSentence.addTermLine(line);
			}
			return corpus;
		}
		finally
		{
			IOUtils.closeStream(reader);
		}
	}
	
	
	
	public List<CoNLLSentence> getSentences()
	{
		return sentences;
	}

}
