package langModel;

import java.io.*;
import java.util.*;



/**
 * Class Vocabulary: class implementing the interface VocabularyInterface.
 * 
 * @author Ilyass FAHMI (2017)
 *
 */
public class Vocabulary implements VocabularyInterface {
	/**
	 * The set of words corresponding to the vocabulary.
	 */
	protected Set<String> vocabulary;

	
	/**
	 * Constructor.
	 */
	public Vocabulary(){
		vocabulary = new TreeSet<String>();
	}
	
	
	@Override
	public int getSize() {
		
		return this.vocabulary.size();
	}

	@Override
	public Set<String> getWords() {

		return vocabulary;
	}

	@Override
	public boolean contains(String word) {
		
		return vocabulary.contains(word);
	}

	@Override
	public void addWord(String word) {
		vocabulary.add(word);
		
	}

	@Override
	public void removeWord(String word) {
		vocabulary.remove(word);
		
	}


	public void scanNgramSet(Set<String> ngramSet) {
		for(String ngram : ngramSet){
			for(String mot : ngram.split(" ")){
				if(!this.contains(mot)){
					this.addWord(mot);
				}
			}
		}
	}


	public void readVocabularyFile(String filePath) {
		File file  = new File(filePath);
		Scanner sc;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String mot = sc.nextLine();
				this.addWord(mot);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void writeVocabularyFile(String filePath) {		
		PrintWriter pW;
		try {
			pW = new PrintWriter(filePath, "UTF-8");
			for(String  mot : getWords()){
				pW.println(mot);
			}
			pW.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
