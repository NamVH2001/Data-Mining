import java.io.File;

/**
 * Runs one TCSPM / TCSPM+ experiment and prints a single CSV line:
 * algo,dataset,query,minsup,candidates,patterns,timeSec,maxMemMB
 *
 * Usage: java -cp <TCSPM classes>;. RunPaperExperiment <1|2> <input> <minsup> <query>
 *   1 = TCSPM (AlgoTCSPM11), 2 = TCSPM+ (AlgoTCSPM22)
 *   query: itemsets separated by ';', items by ',' e.g. "28,212;280"
 */
public class RunPaperExperiment {

	public static void main(String[] args) throws Exception {
		int algo = Integer.parseInt(args[0]);
		String input = args[1];
		int minsup = Integer.parseInt(args[2]);
		Sequence query = new Sequence();
		for (String is : args[3].split(";")) {
			Itemset itemset = new Itemset();
			for (String item : is.split(","))
				itemset.addItem(Integer.parseInt(item.trim()));
			query.addItemset(itemset);
		}
		File out = File.createTempFile("tcspm_", ".txt");
		out.deleteOnExit();

		long start = System.currentTimeMillis();
		int candidates, patterns;
		if (algo == 1) {
			AlgoTCSPM11 a = new AlgoTCSPM11();
			a.runAlgorithm(input, out.getPath(), minsup, query);
			candidates = a.count;
			patterns = a.patternCount;
		} else {
			AlgoTCSPM22 a = new AlgoTCSPM22();
			a.runAlgorithm(input, out.getPath(), minsup, query);
			candidates = a.count;
			patterns = a.patternCount;
		}
		double sec = (System.currentTimeMillis() - start) / 1000.0;
		System.out.printf("%s,%s,%s,%d,%d,%d,%.3f,%.1f%n", algo == 1 ? "TCSPM" : "TCSPM+",
				new File(input).getName(), args[3].replace(',', ' '), minsup, candidates, patterns, sec,
				MemoryLogger.getInstance().getMaxMemory());
	}
}
