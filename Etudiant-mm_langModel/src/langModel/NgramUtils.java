package langModel;


import java.util.ArrayList;
import java.util.List;


/**
 * Class NgramUtils: class containing useful functions to deal with n-grams.
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class NgramUtils {

	/**
	 * Method counting the number of words in a given sequence 
	 * (the sequence can be a n-gram or a sentence).
	 * 
	 * @param sequence the sequence to consider.
	 * @return the number of words of the given sequence.
	 */
	public static int getSequenceSize (String sequence) {
		String [] tableauMotsSequence = sequence.split(" ");
		return tableauMotsSequence.length;
	}

	
	/**
	 * Method parsing a n-gram and returning its history, i.e. the n-1 preceding words.
	 * 
	 * Example: 
	 *   let the ngram be "l'historique de cette phrase";
	 *   the history will be given for the last word of the ngram, here "phrase":
	 *   if the order is 2 then the history will be "cette"; 
	 *   if the order is 3 then it will be "de cette".
	 * 
	 * @param ngram the n-gram to consider.
	 * @param order the order to consider for the n-gram.
	 * @return history of the given n-gram (the length of the history is order-1).  
	 */
	public static String getHistory (String ngram, int order) 
	{
		String [] tableauGetHistory = ngram.split("\\s");
		String history = "";
		
		if(order <= tableauGetHistory.length)
		{
			while(order != 1)
			{
				if(order == 2)
				{
					history += tableauGetHistory[tableauGetHistory.length - order];
				}
				else
				{
					history += tableauGetHistory[tableauGetHistory.length - order] + " ";
				}
				order--;
			}
		}
		else
		{
			for (int i = 0; i < tableauGetHistory.length - 1; i++) 
			{
				history += tableauGetHistory[i];
			}
		}
		return history;
}


	/**
	 * Method decomposing the given sentence into n-grams of the given order.
	 * 
	 * This method will be used in the LanguageModelInterface class for computing 
	 * the probability of a sentence as the product of the probabilities of its n-grams. 
	 * 
	 * Example
	 * given the sentence "a b c d e f g", with order=3,
	 * it will result in the following list:
	 * [a, a b, a b c, b c d, c d e, d e f, e f g] 
	 * 
	 * @param sentence the sentence to consider.
	 * @param order the maximal order for the n-grams to create from the sentence.
	 * @return the list of n-grams constructed from the sentence.
	 */

/*	
	public static List<String> decomposeIntoNgrams (String sentence, int order){
		String histo = "";
		List<String> liste = new ArrayList<String>();
		int newOrder = order ;
		for (String word : sentence.split(" ")){
			if (newOrder > 0){
				newOrder--; 
			}
			histo += word + " ";
			String historique = getHistory(histo, order - newOrder);
			historique += (newOrder == order -1) ? word : " " + word; 
			liste.add(historique);
		} return liste;
	}
*/
public static List<String> decomposeIntoNgrams (String sentence, int order) {
         String [] seq = sentence.split(" ");
         String res = "";
         List<String> lres = new ArrayList<String>();
         int count = 0;

	        for(int i = 1; i <= order; i++)
	        {
	                for(int j = 0; j < i; j++)
	                {
	                        res += seq[j] + " ";
	                }
	                res = res.trim();
	                lres.add(res);
	                res = "";
	        }
	
	        for(int i = 1; i < seq.length - (order - 1); i++)
	        {
	                for(int j = i; j < i + order; j++)
	                {
	                        res += seq[j] + " ";
	                }
	                res = res.trim();
	                lres.add(res);
	                res = "";
	        }
	
	        return lres;
        }

	
	
	/**
	 * Method parsing the given sentence and generate all the combinations of ngrams,
	 * by varying the order n between the given minOrder and maxOrder.
	 * 
	 * This method will be used in the NgramCount class for counting the ngrams 
	 * occurring in a corpus.
	 * 
	 * Algorithm (one possible algo...)
	 * initialize list of ngrams
	 * for n = minOrder to maxOrder (for each order)
	 * 	 for i = 0 to sentence.length-n (parse the whole sentence)
	 *     initialize ngram string parsedSentence
	 *     for j = i to i+n-1 (create a ngram made of the following sequence of words starting from i to i + the order size)
	 *       ngram = ngram + " " + sentence[j]
	 *     add ngramm to list ngrams 
	 * return list ngrams
	 * 
	 * Example
	 * given the sentence "a b c d e f g", with minOrder=1 and maxOrder=3, it will result in the following list:
	 * [a, b, c, d, e, f, g, a b, b c, c d, d e, e f, f g, a b c, b c d, c d e, d e f, e f g]
	 * 
	 * @param sentence the sentence from which to generate n-grams.
	 * @param minOrder the minimal order of the n-grams to create.
	 * @param maxOrder the maximal order of the n-grams to create.
	 * @return a list of generated n-grams from the sentence.
	 */
	public static List<String> generateNgrams (String sentence, int minOrder, int maxOrder) {

        List<String> liste = new ArrayList<String>();
        String[] phrase = sentence.split("\\s");
        for (int n = minOrder; n <= maxOrder; n++)
        {
        	for (int i = 0; i <= phrase.length-n; i++)
        	{
                String nGram = "";
                for (int j = i; j <= i + n - 1; j++)
                {
                	if(nGram != "")
                	{
                        nGram += " ";
                	}
         nGram += phrase[j];
                }
        //nGram = nGram.trim();
        liste.add(nGram);
        	}
        }
        return liste;
	}
	
	/**
     * Utile pour estimer un modèle de langage : NgramCounts::scanTextFile()
     * Utile pour calculer la proba d'une phrase : NaiveLanguageModel
     */
	
	public static String getOOV (String s, VocabularyInterface vocab)
    {
            String[] phrase = s.split("\\s");
            String res = "";
            for(int i = 0; i < phrase.length; i++)
            {
            	if(res != "")
            	{
            		res += " ";
            	}
            	res += phrase[i];
            	phrase[i].toString();
                if(!vocab.contains(phrase[i]))
                {
                    phrase[i] = vocab.OOV_TAG;
                }
            }
            return res;
    }

}
