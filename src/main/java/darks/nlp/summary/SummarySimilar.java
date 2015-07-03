package darks.nlp.summary;

import java.util.Collection;
import java.util.List;

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
