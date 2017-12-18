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

	@Override
	public void scanNgramSet(Set<String> ngramSet) {
		Iterator<String> it = ngramSet.iterator();
		while(it.hasNext()){
			this.addWord(it.next());
		}
	}

	@Override
	public void readVocabularyFile(String filePath) {
		try {
			Scanner sc = new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeVocabularyFile(String filePath) {
		File file = new File(filePath);
		Iterator<String> it = this.vocabulary.iterator();
		try{
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			while(it.hasNext()){
				fw.write(it.next());
				fw.write("\n");
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
