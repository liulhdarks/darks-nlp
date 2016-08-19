package darks.nlp.summary;

import darks.nlp.common.beans.Sentence;

public interface SummaryFilter {

    public boolean filter(Sentence sentence);
    
}
