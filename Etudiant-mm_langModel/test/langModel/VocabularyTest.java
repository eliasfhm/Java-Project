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
		nGC.readNgramCountsFile("lm/trigram-sample-train-fr.lm");
		vocabulaire = new Vocabulary();
	}	
	@Test
	public void testScanNgramSet() {
		vocabulaire.scanNgramSet(nGC.getNgrams());
		assertEquals(12, vocabulaire.getSize());
		assertTrue(vocabulaire.contains("pousse"));
		assertTrue(vocabulaire.contains("PoUsSe"));
		assertFalse(vocabulaire.contains("carotte"));
	}
	@Test
	public void testReadVocabularyFile() {
		vocabulaire.readVocabularyFile("vocabulaireTest/sample-train-fr.vocab");
		assertEquals(12, vocabulaire.getSize());
		assertTrue(vocabulaire.contains("pousse"));
		assertTrue(vocabulaire.contains("PoUsSe"));
		assertFalse(vocabulaire.contains("carotte"));
	}
	@Test
	public void testWriteVocabularyFile() {
		vocabulaire.scanNgramSet(nGC.getNgrams());
		vocabulaire.writeVocabularyFile("vocabulaireTest/sample-train-fr.vocab");
	}
}
