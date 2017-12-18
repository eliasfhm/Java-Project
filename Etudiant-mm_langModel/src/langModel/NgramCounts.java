package langModel;


import java.io.*;
import java.util.*;

import jdk.jfr.events.FileWriteEvent;

/**
 * Class NgramCounts: class implementing the interface NgramCountsInterface. 
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class NgramCounts implements NgramCountsInterface {
	/**
	 * The maximal order of the n-gram counts.
	 */
	protected int order;

	/**
	 * The map containing the counts of each n-gram.
	 */
	protected Map<String,Integer> ngramCounts;

	/**
	 * The total number of words in the corpus.
	 * In practice, the total number of words will be increased when parsing a corpus 
	 * or when parsing a NgramCountsInterface file (only if the ngram encountered is a unigram one).
	 */
	protected int nbWordsTotal;
	
	
	/**
	 * Constructor.
	 */
	public NgramCounts(){
		ngramCounts = new HashMap<String, Integer>();
	}


	/**
	 * Setter of the maximal order of the ngrams considered.
	 * 
	 * In practice, the method will be called when parsing the training corpus, 
	 * or when parsing the NgramCountsInterface file (using the maximal n-gram length encountered).
	 * 
	 * @param order the maximal order of n-grams considered.
	 */

	private void setMaximalOrder (int order) {
		this.order = order; 
	}

	
	@Override
	public int getMaximalOrder() {
		return order;
	}

	
	@Override
	public int getNgramCounterSize() {
		return ngramCounts.size();
	}

	
	@Override
	public int getTotalWordNumber(){
		return this.nbWordsTotal;
	}
	
	
	@Override
	public Set<String> getNgrams() {
		return this.ngramCounts.keySet();
	}

	
	@Override
	public int getCounts(String ngram) {
		if(ngramCounts.get(ngram) != null){
			return ngramCounts.get(ngram);
		}else{
			return 0;
		}
	}
	

	@Override
	public void incCounts(String ngram) {
		if(ngramCounts.containsKey(ngram)){
			ngramCounts.put(ngram, ngramCounts.get(ngram)+1);
		}else{
			ngramCounts.put(ngram, 1);
		}
	}

	
	@Override
	public void setCounts(String ngram, int counts) {
		ngramCounts.put(ngram, counts);
	}

	

	@Override
	/*public void scanTextFile(String filePath, VocabularyInterface vocab, int maximalOrder) {
		 Scanner scf = null;
         try
         {
         scf = new Scanner(new File(filePath));
         NgramUtils u = new NgramUtils();
         List<String> liste;
         Iterator<String> its;

         while (scf.hasNextLine()){
             liste = NgramUtils.generateNgrams(scf.nextLine(), 1, maximalOrder);
             its = liste.iterator();
             String scan = "";
             while(its.hasNext()){
                 scan = its.next();
                 String ngram = NgramUtils.getOOV(scan, vocab);
                 this.incCounts(ngram);
             }
         }
         scf.close();
         this.setMaximalOrder(maximalOrder);
         }
         catch (FileNotFoundException e)
         {
                 e.printStackTrace();
         }
	}*/

	public void scanTextString(String text, int maximalOrder) {
		for(String ngram : NgramUtils.generateNgrams(text, 1, maximalOrder)){
			incCounts(ngram.toLowerCase());
		}
	}
	public void scanTextFile(String filePath, int maximalOrder) {
		String txt="";
		Scanner sc;
		try{
			sc = new Scanner(new File(filePath));
			while(sc.hasNextLine()){
				this.scanTextString(sc.nextLine(), maximalOrder);
			}
			 this.setMaximalOrder(maximalOrder);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}		
	}
	
	@Override
	public void writeNgramCountFile(String filePath) {
		File file = new File(filePath);
		Iterator<String> it = ((Map<String, Integer>) ngramCounts).keySet().iterator();
		try{
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			while(it.hasNext()){
				String str = it.next();
				bw.write(str+"\t"+ngramCounts.get(str));
				bw.newLine();
			}
			bw.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void readNgramCountsFile(String filePath) {
		int maxOrd = 0;
		int nbMot = 0;
		File file = new File(filePath);
		Scanner sc;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String txt = sc.nextLine();
				String[] tab = txt.split("\t");
				this.setCounts(tab[0], Integer.parseInt(tab[1]));				
				int orderNGram = tab[0].split(" ").length;
				if(orderNGram > maxOrd){
					maxOrd = orderNGram;
				}
				if(orderNGram == 1){
					nbMot += Integer.parseInt(tab[1]);
				}
			}
			this.setMaximalOrder(maxOrd);
			this.nbWordsTotal = nbMot;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
}


	@Override
	public void scanTextFile(String filePath, VocabularyInterface vocab, int maximalOrder) {
		// TODO Auto-generated method stub
		
	}
}
