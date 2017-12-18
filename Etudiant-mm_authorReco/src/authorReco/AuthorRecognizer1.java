package authorReco;


import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

import authorEval.*;
import langModel.*;


/**
 * Class AuthorRecognizer1: a first author recognition system that recognizes 
 * the author of a sentence by using the language models read from a configuration system.
 * (no unknown author can be detected)
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class AuthorRecognizer1 extends AuthorRecognizerAbstractClass {
	/**
	 * Map of LanguageModels associated with each author (each author can be 
	 * associated with several language models). 
	 * The keys to the first map are the names of the authors (e.g., "zola"), the keys 
	 * to the second map are the names associated with each file containing a language model 
	 * (e.g., "zola-bigrams"), and the values of the second map are the LanguageModel objects 
	 * built from the file path given in the AuthorConfigurationFile attribute.
	 */
	protected Map<String, Map<String, LanguageModelInterface>> authorLangModelsMap;
	
	/**
	 * Constructor.
	 * 
	 * @param configFile the file path of the configuration file containing the information 
	 * on the language models (author, name and file path).
	 * @param vocabFile the file path of the file containing the common vocabulary
	 * for all the language models used in the recognition system. 
	 * @param authorFile the file path of the file containing 
	 * the names of the authors recognized by the system.
	 */
	public AuthorRecognizer1(String configFile, String vocabFile, String authorFile) 
	{
		this.loadAuthorConfigurationFile(configFile);
		this.loadVocabularyFile(vocabFile);
		this.loadAuthorFile(authorFile);
		
		this.authorLangModelsMap = new HashMap<>();
		
		NgramCountsInterface ngramCounts = new NgramCounts();
		LanguageModelInterface langM = new NaiveLanguageModel();
		
		try
		{
			Scanner sc = new Scanner(new File(configFile));
			
			while(sc.hasNextLine())
			{
				//Splitting each line of the configuration file
				String[] params = sc.nextLine().split("\\s+");
				
				HashMap<String, LanguageModelInterface> almm = new HashMap<>();
								
				ngramCounts.readNgramCountsFile(params[2]);
				langM.setNgramCounts(ngramCounts, this.getVocabularyLM());
				
				almm.put(params[1], langM);
				System.out.println(almm.get(params[1]));
				this.authorLangModelsMap.put(params[0], almm);
			}
			sc.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Method recognizing and returning the author of the given sentence 
	 * (the unknown author can also be picked up).
	 * 
	 * @param sentence the sentence whose author is to recognize.
	 * @return the author of the sentence as recognized by the recognition system.
	 */
	public String recognizeAuthorSentence(String sentence) 
	{
		LanguageModelInterface langM;
		Collection<LanguageModelInterface> authbis;
		Iterator<LanguageModelInterface> it;
		Double prob = 0.0;
		String author_recognized = UNKNOWN_AUTHOR;

		for(int i = 0; i < this.authorLangModelsMap.size(); i++)
		{
			authbis = this.authorLangModelsMap.get(authors.get(i)).values();
			it = authbis.iterator();
			//System.out.println(authors.get(i));
			while(it.hasNext())
			{
				langM = (NaiveLanguageModel) it.next();
				//System.out.println(langM.getSentenceProb(sentence));
				//System.out.println(authors.get(i));
				
				if(prob < langM.getSentenceProb(sentence))
				{
					prob = langM.getSentenceProb(sentence);
					author_recognized = authors.get(i);
				}
			}
		}
		return author_recognized;
	}
	
	/**
	 * Main method.
	 * 
	 * @param args arguments of the main method.
	 */
	public static void main(String[] args) 
	{
		//initialization of the recognition system
		AuthorRecognizer1 AR = new AuthorRecognizer1("./lm/small_author_corpus/fichConfig_bigram_1000sentences.txt","./lm/small_author_corpus/corpus_20000.vocab","./data/small_author_corpus/validation/authors.txt");
		//computation of the hypothesis author file
		AR.recognizeFileLanguage("./data/small_author_corpus/validation/sentences_100sentences.txt", "./data/small_author_corpus/validation/authors_100sentences_hyp1.txt");
		//computation of the performance of the recognition system
		System.out.print(AR.recognizeAuthorSentence("je vois en 1719"));
	}
}
