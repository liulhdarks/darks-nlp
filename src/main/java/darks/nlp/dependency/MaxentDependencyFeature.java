package darks.nlp.dependency;

import java.util.List;

public class MaxentDependencyFeature
{
	List<String> feature;
	
	String label;
	
	public MaxentDependencyFeature()
	{
		
	}

	public MaxentDependencyFeature(List<String> feature, String label)
	{
		super();
		this.feature = feature;
		this.label = label;
	}

	public List<String> getFeature()
	{
		return feature;
	}

	public void setFeature(List<String> feature)
	{
		this.feature = feature;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	@Override
	public String toString()
	{
		return "MaxentDependencyFeature [feature=" + feature + ", label=" + label + "]";
	}
	
}
