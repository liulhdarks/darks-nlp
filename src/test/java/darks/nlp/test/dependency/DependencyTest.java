package darks.nlp.test.dependency;

import java.io.File;

import org.junit.Test;

import darks.nlp.common.NatureTagger.NatureTag;
import darks.nlp.common.beans.Term;
import darks.nlp.corpus.conll.CoNLLCorpus;
import darks.nlp.corpus.conll.CoNLLSentence;
import darks.nlp.dependency.DependencyParser;
import darks.nlp.dependency.impl.MaxentDependencyParser;

public class DependencyTest
{

	@Test
	public void testMaxentDependency()
	{
		try
		{
			DependencyParser parser = new MaxentDependencyParser(32);
//			CoNLLCorpus corpus = CoNLLCorpus.loadFromFile(new File("corpus/dependency/thu_train.conll"));
//			parser.setLogDelay(5000);
//			parser.train(corpus, 100);
//			parser.saveModel(new File("model/dependency/maxent_dep_thu.model"));
			parser.readModel(new File("model/dependency/maxent_dep_thu.model"));
			
			CoNLLSentence sent = new CoNLLSentence();
			sent.addTerm(new Term("坚决", NatureTag.ad));
			sent.addTerm(new Term("惩治", NatureTag.v));
			sent.addTerm(new Term("贪污", NatureTag.v));
			sent.addTerm(new Term("贿赂", NatureTag.n));
			sent.addTerm(new Term("等", NatureTag.udeng));
			sent.addTerm(new Term("经济", NatureTag.n));
			sent.addTerm(new Term("犯罪", NatureTag.vn));
			parser.parse(sent);

			sent = new CoNLLSentence();
			sent.addTerm(new Term("hankcs", NatureTag.nrf));
			sent.addTerm(new Term("每天", NatureTag.r));
			sent.addTerm(new Term("都", NatureTag.d));
			sent.addTerm(new Term("在", NatureTag.p));
			sent.addTerm(new Term("写", NatureTag.v));
			sent.addTerm(new Term("程序", NatureTag.n));
			parser.parse(sent);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
}
