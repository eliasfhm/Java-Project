package langModel;


/**
 * Class LaplaceLanguageModel: class inheriting the class NaiveLanguageModel by creating 
 * a n-gram language model using a Laplace smoothing.
 * 
 * @author ... (2017)
 *
 */
public class LaplaceLanguageModel extends NaiveLanguageModel {

	public Double getNgramProb(String ngram) {
		int tailleNGram = ngram.split(" ").length;
		double nBNGram = this.ngramCounts.getCounts(ngram) + 1;
		if (tailleNGram == 1) {
			return nBNGram / (this.ngramCounts.getTotalWordNumber() + this.vocabulary.getSize());
		}
		else{
			double somme = 0.0;
			String historique = "";
			if(this.getLMOrder()> tailleNGram){
				historique = NgramUtils.getHistory(ngram, tailleNGram);
			}
			else{
				historique = NgramUtils.getHistory(ngram, this.getLMOrder());
			}
			for(String mot : this.vocabulary.getWords()){
				somme += this.ngramCounts.getCounts(historique);
			}			
			return nBNGram/(somme+this.vocabulary.getSize());
		}
	}
}
