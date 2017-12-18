package langModel;

import static org.junit.Assert.*;
import langModel.NaiveLanguageModel;
import langModel.NgramCounts;

import org.junit.Before;
import org.junit.Test;

public class NaiveLanguageModelTest {	
	NaiveLanguageModel nML;
	NgramCounts nGC;	
	@Before
	public void init(){
		nML = new NaiveLanguageModel();
		nGC = new NgramCounts();
		nGC.readNgramCountsFile("lm/small_corpus/ngramCounts_bigram_vocabulary2.txt");
		nML.setNgramCounts(nGC);
	}	
	@Test
	public void testGetNgramProb() {
		System.out.println("\nTest GetNgramProb()");
		System.out.println(nML.getNgramProb("antoine"));
		System.out.println(nML.getNgramProb("autre"));
		System.out.println(nML.getNgramProb("écoute"));
		System.out.println(nML.getNgramProb("elle écoute"));
	}
	@Test
	public void testGetSentenceProb() {
		System.out.println("\nTest GetSentenceProb()");
		System.out.println(nML.getSentenceProb("<s> elle écoute une chanson de thom </s>"));
		System.out.println(nML.getSentenceProb("<s> elle écoute une chanson de thom </s>"));
	}
}
