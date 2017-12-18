package langModel;

import static org.junit.Assert.*;
import langModel.NgramCounts;
import langModel.Vocabulary;

import org.junit.Before;
import org.junit.Test;

public class VocabularyTest {	
	Vocabulary vocabulaire;
	NgramCounts nGC;	
	@Before
	public void init(){
		nGC = new NgramCounts();
		nGC.readNgramCountsFile("lm/small_corpus/ngramCounts_bigram_vocabulary1.txt");
		vocabulaire = new Vocabulary();
	}	
	@Test
	public void testScanNgramSet() {
		vocabulaire.scanNgramSet(nGC.getNgrams());
		assertEquals(12, vocabulaire.getSize());
		assertTrue(vocabulaire.contains("chanson"));
		assertFalse(vocabulaire.contains("patate"));
	}
	@Test
	public void testReadVocabularyFile() {
		vocabulaire.readVocabularyFile("lm/small_corpus/vocabulary1_in.txt");
		assertEquals(12, vocabulaire.getSize());
		assertTrue(vocabulaire.contains("chanson"));
		assertFalse(vocabulaire.contains("patate"));
	}
	@Test
	public void testWriteVocabularyFile() {
		vocabulaire.scanNgramSet(nGC.getNgrams());
		vocabulaire.writeVocabularyFile("lm/small_corpus/vocabulary1_in.txt");
	}
}
