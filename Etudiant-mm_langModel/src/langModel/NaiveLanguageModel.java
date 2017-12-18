package langModel;

import java.awt.List;
import java.util.ArrayList;

/**
 * Class NaiveLanguageModel: class implementing the interface LanguageModelInterface by creating a naive language model,
 * i.e. a n-gram language model with no smoothing.
 * 
 * @author FAHMI Ilyass (2017)
 *
 */
public class NaiveLanguageModel implements LanguageModelInterface {
	/**
	 * The NgramCountsInterface corresponding to the language model.
	 */
	protected NgramCountsInterface ngramCounts;
	
	/**
	 * The vocabulary of the language model.
	 */
	protected VocabularyInterface vocabulary;
	
	
	/**
	 * Constructor.
	 */
	public NaiveLanguageModel(){
		this.ngramCounts = new NgramCounts();
		this.vocabulary = new Vocabulary();
	}
	

	@Override
	public int getLMOrder() {
		return ngramCounts.getMaximalOrder();
	}

	@Override
	public void setNgramCounts(NgramCounts ngramCounts){
		this.ngramCounts = ngramCounts;
		this.vocabulary.scanNgramSet(ngramCounts.getNgrams());
	}

	public int getVocabularySize(){
		return this.vocabulary.getSize();
	}
	
	@Override
	public Double getNgramProb(String ngram){		
		int tailleNGram = ngram.split(" ").length;
		double nBNGram = this.ngramCounts.getCounts(ngram);
		if (tailleNGram == 1){
			return nBNGram / this.ngramCounts.getTotalWordNumber();
		}
		else{
			double somme = 0.0;
			String historique = "";
			if (this.getLMOrder() > tailleNGram){
				historique = NgramUtils.getHistory(ngram,tailleNGram);
			}
			else{
				historique = NgramUtils.getHistory(ngram, this.getLMOrder());
			}
			for(String word : this.vocabulary.getWords()){
				somme += this.ngramCounts.getCounts(historique);
			}
			if(somme == 0){
				return somme;
			}
			else{
				return nBNGram/somme;
			}
		}
	}

	@Override
	public Double getSentenceProb(String sentence) {
		double somme = 1;
		for(String ngram : NgramUtils.decomposeIntoNgrams(sentence, this.getLMOrder())) {
			somme = this.getNgramProb(ngram) * somme;
		}
		return somme;
	}

}
