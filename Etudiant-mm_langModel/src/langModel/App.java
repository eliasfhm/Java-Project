package langModel;

public class App {

	public static void main(String[] args) {
		VocabularyInterface v;
		NgramCountsInterface n;
		
		NgramUtils u = new NgramUtils();
		v = new Vocabulary();
		n = new NgramCounts();
		u = new NgramUtils();
		
		String s = "a b c d e f g";
		v.addWord("denis");
		v.addWord("elle");
		v.addWord("xo");
		v.addWord("no");
		n.scanTextFile("./lm/small_corpus/ngramCounts_bigram_vocabulary1.txt", v, 3);
		
		System.out.println(u.getHistory("<s> lionel veut boire une </s>", 3));
		System.out.println("--------------------------");
		System.out.println(v.contains("xo"));
		System.out.println(v.contains("elle"));
		
		System.out.println("--------------------------");
		
		v.removeWord("xo");
		System.out.println(v.contains("xo"));
		System.out.println(v.getSize());
		System.out.println(v.getWords());
		
		System.out.println("--------------------------");

		
		System.out.println();
		System.out.println(u.getSequenceSize(s));
		System.out.println(u.getHistory(s, 7));
		System.out.println(u.generateNgrams(s, 3, 3));
		System.out.println(u.decomposeIntoNgrams(s, 3));

	}

}
