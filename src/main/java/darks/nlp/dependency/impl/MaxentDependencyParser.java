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
package darks.nlp.dependency.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jblas.DoubleMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import darks.learning.classifier.maxent.GISMaxent;
import darks.learning.classifier.maxent.GISModel;
import darks.learning.classifier.maxent.Maxent;
import darks.learning.classifier.maxent.MaxentModel;
import darks.learning.common.blas.DenseMatrix;
import darks.learning.common.blas.Matrix;
import darks.learning.common.minispantree.DirectPrimMiniSpanTree;
import darks.learning.common.minispantree.GraphBuilder;
import darks.learning.common.minispantree.GraphEdge;
import darks.learning.common.minispantree.GraphNode;
import darks.learning.common.minispantree.MiniSpanTree;
import darks.learning.corpus.Documents;
import darks.nlp.common.beans.Term;
import darks.nlp.corpus.conll.CoNLLCorpus;
import darks.nlp.corpus.conll.CoNLLSentence;
import darks.nlp.corpus.conll.CoNLLTerm;
import darks.nlp.dependency.DependencyParser;
import darks.nlp.dependency.impl.MaxentDependencyExtractor.MaxentDependencyFeature;
import darks.nlp.utils.StringUtils;

/**
 * Parse sentence dependency relation by maxent
 * 
 * @author lihua.llh
 *
 */
public class MaxentDependencyParser extends DependencyParser
{
	private static final Logger log = LoggerFactory.getLogger(MaxentDependencyParser.class);
	
	private static final double LAPLACE_SMOOTH = 0.0000001;
	
	Maxent maxent = null;
	
	MaxentModel model = null;
	
	public MaxentDependencyParser()
	{
		
	}
	
	public MaxentDependencyParser(int threadCount)
	{
		super(threadCount);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean train(CoNLLCorpus corpus, int maxIterationNumber)
	{
		MaxentDependencyExtractor extractor = new MaxentDependencyExtractor();
		extractor.setLogDelay(logDelay);
		List<MaxentDependencyFeature> features = extractor.extractTrainFeature(corpus, threadCount);
		if (features == null || features.isEmpty())
			return false;
		log.info("Convert maxent dependency features " + features.size() + " to documents.");
		Documents docs = new Documents();
		for (MaxentDependencyFeature feature : features)
		{
			String strFeature = StringUtils.toCollectionString(feature.feature, ' ');
			docs.addData(strFeature, feature.label);
		}
		maxent = new GISMaxent();
		model = maxent.train(docs, maxIterationNumber);
		return model != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void parse(CoNLLSentence sentence)
	{
		List<CoNLLTerm> terms = sentence.getTerms();
		List<GraphNode<Term>> nodes = new ArrayList<GraphNode<Term>>();
		MaxentDependencyExtractor extractor = new MaxentDependencyExtractor();
		Matrix<GraphEdge<String>> edges = new DenseMatrix<GraphEdge<String>>(terms.size());
		for (int i = 0; i < terms.size(); i++)
		{
			nodes.add(new GraphNode<Term>(terms.get(i)));
			for (int j = 0; j < terms.size(); j++)
			{
				if (i == j)
					continue;
				List<String> feature = extractor.extractWordsFeature(terms, i, j);
//				System.out.println(StringUtils.toCollectionString(feature, ' '));
				DoubleMatrix probMatrix = maxent.predictMatrix(feature.toArray(new String[0]));
				List<String> labels = maxent.getLabels();
				String maxLabel = null;
				double maxProb = -1;
				for (int p = 0; p < labels.size(); p++)
				{
					String label = maxent.getLabel(p);
					double prob = probMatrix.get(p);
					if (Double.compare(prob, maxProb) > 0 
							&& !MaxentDependencyExtractor.DEFAULT_LABEL.equals(label))
					{
						maxLabel = label;
						maxProb = prob;
					}
				}
				double prob = Double.compare(maxProb, 1.) == 0 ? maxProb - LAPLACE_SMOOTH : maxProb;
				double weight = -Math.log(prob);
				String label = maxLabel;
				if (label != null 
						&& !"".equals(label) 
						&& !MaxentDependencyExtractor.DEFAULT_LABEL.equals(label))
				{
//					System.out.println(i + " " + j + " " + prob + " " + weight + " " + label);
					GraphEdge<String> edge = new GraphEdge<String>(i, j, weight, label);
					edges.put(j, i, edge);
				}
			}
		}
		
		if (!edges.checkNull())
		{
			MiniSpanTree<Term, String> tree = new DirectPrimMiniSpanTree<Term, String>();
			tree.initialize(new GraphBuilder<Term, String>(nodes, edges));
			tree.buildTree(0);
			for (GraphEdge<String> edge : tree.getResultEdges())
			{
				CoNLLTerm term = terms.get(edge.getFrom());
				term.setId(edge.getFrom());
				term.setCenterId(edge.getTo());
				term.setLabel(edge.getValue());
			}
		}
		for (CoNLLTerm term : terms)
		{
			if (term.isRoot())
				continue;
			System.out.println(term);
		}
		System.out.println();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean saveModel(OutputStream out) throws IOException
	{
		if (model == null)
			return false;
		return model.saveModel(out);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean readModel(InputStream ins) throws Exception
	{
		model = GISModel.readModel(ins);
		if (model != null)
		{
			maxent = new GISMaxent();
			maxent.loadModel(model);
			return true;
		}
		return false;
	}
	
	
}
