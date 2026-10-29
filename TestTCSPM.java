import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
// import java.util.BitSet;

public class TestTCSPM {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// load a sequence database
		String input = fileToPath("sample_sequences.txt");
		String output = ".//output_sample.txt";

		// Item IDs use A=1, B=2, ..., Z=26. Query: V then R.
		Sequence Query = new Sequence();
		Query.addItemset(new Itemset(22)); // V
		Query.addItemset(new Itemset(18)); // R
		System.out.println("Query sequence: " + Query);

		// create an instance of the algorithm

		//TCSPM
        AlgoTCSPM11 algo1 = new AlgoTCSPM11();
		algo1.runAlgorithm(input, output, 3, Query);
        algo1.printStatistics();
                 
        //TCSPM+

//		AlgoTCSPM22 algo2 = new AlgoTCSPM22();
//		algo2.runAlgorithm(input, output,155, Query);
//		algo2.printStatistics();

	}

	public static String fileToPath(String filename) throws UnsupportedEncodingException {
		URL url = TestTCSPM.class.getResource(filename);
		return java.net.URLDecoder.decode(url.getPath(), "UTF-8");
	}
}
