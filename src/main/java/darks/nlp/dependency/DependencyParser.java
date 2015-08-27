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
package darks.nlp.dependency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import darks.nlp.corpus.conll.CoNLLCorpus;
import darks.nlp.corpus.conll.CoNLLSentence;

/**
 * Sentence dependency parser
 * 
 * @author lihua.llh
 *
 */
public abstract class DependencyParser
{
	
	protected int threadCount = 1;
	
	protected long logDelay = 10000;
	
	public DependencyParser()
	{
		
	}
	
	public DependencyParser(int threadCount)
	{
		this.threadCount = threadCount;
	}

	/**
	 * Start to train dependency
	 * 
	 * @param corpus Conll corpus
	 * @param maxIterationNumber Maximum iteration number
	 * @return 
	 */
	public abstract boolean train(CoNLLCorpus corpus, int maxIterationNumber);
	
	/**
	 * Parse dependency
	 * 
	 * @param sentence
	 */
	public abstract void parse(CoNLLSentence sentence);
	
	public boolean saveModel(File file) throws IOException
	{
		return saveModel(new FileOutputStream(file));
	}
	
	public abstract boolean saveModel(OutputStream out) throws IOException;
	
	public boolean readModel(File file) throws Exception
	{
		return readModel(new FileInputStream(file));
	}
	
	public abstract boolean readModel(InputStream ins) throws Exception;

	public int getThreadCount()
	{
		return threadCount;
	}

	public void setThreadCount(int threadCount)
	{
		this.threadCount = threadCount;
	}

	public long getLogDelay()
	{
		return logDelay;
	}

	public void setLogDelay(long logDelay)
	{
		this.logDelay = logDelay;
	}
	
	
	
}
