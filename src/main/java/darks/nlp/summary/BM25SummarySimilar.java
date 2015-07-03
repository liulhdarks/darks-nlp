package darks.nlp.summary;

import java.util.Collection;

import org.jblas.DoubleMatrix;

import darks.nlp.common.beans.Sentence;
import darks.nlp.common.similar.BM25;

public class BM25SummarySimilar extends SummarySimilar
{
	
	BM25 bm25 = new BM25();

	public BM25SummarySimilar()
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(Collection<Sentence> sentences)
	{
		bm25.initialize(sentences);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DoubleMatrix similar(Sentence sentence)
	{
		return bm25.computeAll(sentence);
	}

}
