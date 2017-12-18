package langModel;

import static org.junit.Assert.*;
import langModel.LaplaceLanguageModel;
import langModel.NaiveLanguageModel;
import langModel.NgramCounts;

import org.junit.Before;
import org.junit.Test;

public class LaplaceLanguageModelTest {
	NaiveLanguageModel nLM;
	NgramCounts nGC;	
	@Before
	public void init(){
		nLM = new LaplaceLanguageModel();
		nGC = new NgramCounts();
		nGC.readNgramCountsFile("lm/small_corpus/ngramCounts_bigram_vocabulary2.txt");
		nLM.setNgramCounts(nGC);
	}	
	@Test
	public void testGetSentenceProb() {
		System.out.println("\ntestGetSentenceProb() Laplace");
		System.out.println(nLM.getSentenceProb("<s> elle écoute une chanson de thom </s>"));
		System.out.println(nLM.getSentenceProb("<s> elle écoute une chanson de thom </s>"));
	}
}
