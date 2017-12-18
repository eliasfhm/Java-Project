package langModel;

import static org.junit.Assert.*;
import langModel.NgramCounts;


import org.junit.Before;
import org.junit.Test;

public class NgramCountsTest {	
	NgramCounts nGC;	
	@Before
	public void initialize() {
		nGC = new NgramCounts();
	}
	@Test
	public void testScanTextFile() {		
		nGC.scanTextFile("data/small_corpus/corpus_fr_test.txt", null, 3);
		assertEquals(3, nGC.getMaximalOrder());
		assertEquals(12, nGC.getTotalWordNumber());
		assertEquals(2, nGC.getCounts("chanson"));
		assertEquals(2, nGC.getCounts("une chanson"));
	}
	@Test
	public void testWriteNgramCountFile() {
		nGC.scanTextFile("data/small_corpus/corpus_fr_test.txt", null, 3);
		nGC.writeNgramCountFile("lm/small_corpus/ngramCounts_bigram_vocabulary2.txt");
	}
	@Test
	public void testReadNgramCountsFile() {
		nGC.readNgramCountsFile("lm/small_corpus/ngramCounts_bigram_vocabulary2.txt");
		assertEquals(3, nGC.getMaximalOrder());
		assertEquals(20, nGC.getTotalWordNumber());
		assertEquals(3, nGC.getCounts("chanson"));
		assertEquals(3, nGC.getCounts("une chanson"));
	}
}
