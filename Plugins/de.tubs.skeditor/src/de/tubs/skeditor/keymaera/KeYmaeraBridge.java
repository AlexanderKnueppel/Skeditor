package de.tubs.skeditor.keymaera;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import edu.cmu.cs.ls.keymaerax.bellerophon.BelleExpr;
import edu.cmu.cs.ls.keymaerax.core.Formula;
import edu.cmu.cs.ls.keymaerax.core.Sequent;
import edu.cmu.cs.ls.keymaerax.parser.ParsedArchiveEntry;
import edu.cmu.cs.ls.keymaerax.pt.ProvableSig;
import scala.Tuple3;
import scala.collection.JavaConverters$;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.mutable.Buffer;

public class KeYmaeraBridge {

	private static final KeYmaeraBridge instance = new KeYmaeraBridge();

	public static KeYmaeraBridge getInstance() {
		return instance;
	}

//	//static final edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXParser$ parser = edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXParser$.MODULE$;
//	static final edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXArchiveParser$ archiveParser = edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXArchiveParser$.MODULE$;
//	static final edu.cmu.cs.ls.keymaerax.parser.StringConverter$ stringConverter = edu.cmu.cs.ls.keymaerax.parser.StringConverter$.MODULE$;
//	//static final edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXPrettyPrinter$ prettyPrinter = edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXPrettyPrinter$.MODULE$;
//	static final edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$ God = edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$.MODULE$;
//	static final edu.cmu.cs.ls.keymaerax.btactics.AxiomaticODESolver$ axiomSolver = edu.cmu.cs.ls.keymaerax.btactics.AxiomaticODESolver$.MODULE$;
//
//	static final edu.cmu.cs.ls.keymaerax.pt.ProvableSig$ provSig = edu.cmu.cs.ls.keymaerax.pt.ProvableSig$.MODULE$;
//
//	static final edu.cmu.cs.ls.keymaerax.btactics.DifferentialTactics$ diffTactics = edu.cmu.cs.ls.keymaerax.btactics.DifferentialTactics$.MODULE$;
//
//	static final BelleExpr master = God.master(God.master$default$1(), God.master$default$2());
//
//	static final edu.cmu.cs.ls.keymaerax.bellerophon.BelleInterpreter$ belleInterpreter = edu.cmu.cs.ls.keymaerax.bellerophon.BelleInterpreter$.MODULE$;

	public Formula parseProgramFromFileAsFormula(String filename) throws IOException {
		String contents = new String(Files.readAllBytes(Paths.get(filename)));
		return edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXArchiveParser$.MODULE$.parseAsFormula(contents);
	}

	public List<ParsedArchiveEntry> parseProgramsFromFileAsEntries(String filename) throws IOException {
		return edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXArchiveParser$.MODULE$.parseFromFile(filename);
	}

	public Formula parseProgramAsFormula(String contents) {
		return edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXArchiveParser$.MODULE$.parseAsFormula(contents);
	}

	public Sequent parseSeqFromString(String content) {
		return edu.cmu.cs.ls.keymaerax.parser.StringConverter$.MODULE$.StringToStringConverter(content).asSequent();
	}

	public static java.util.HashMap<String, String> getMathematicaConfig() {
		java.util.HashMap<String, String> c = new HashMap<String, String>();
		c.put("linkName", "C:\\Program Files\\Wolfram Research\\Mathematica\\11.2\\MathKernel.exe"); // path
		c.put("libDir",
				"C:\\Program Files\\Wolfram Research\\Mathematica\\11.2\\SystemFiles\\Links\\JLink\\SystemFiles\\Libraries\\Windows-x86-64\\"); // path
		return c;
	}

	public static java.util.HashMap<String, String> getZ3Config() {
		java.util.HashMap<String, String> c = new HashMap<String, String>();
		c.put("rath", "C:\\z3\\bin\\z3.exe"); // path
		return c;
	}

	public static <A, B> Map<A, B> toScalaMap(HashMap<A, B> m) {
		return JavaConverters$.MODULE$.mapAsScalaMapConverter(m).asScala()
				.toMap(scala.Predef$.MODULE$.<scala.Tuple2<A, B>>$conforms());
	}

	public static <A> java.util.List<A> toJavaList(Buffer<A> b) {
		return JavaConverters$.MODULE$.bufferAsJavaList(b);
	}

	private void init() {
		edu.cmu.cs.ls.keymaerax.btactics.ToolProvider$.MODULE$
				.setProvider(new edu.cmu.cs.ls.keymaerax.btactics.Z3ToolProvider(toScalaMap(getZ3Config())));

		edu.cmu.cs.ls.keymaerax.Configuration$.MODULE$
				.setConfiguration(edu.cmu.cs.ls.keymaerax.FileConfiguration$.MODULE$);

		HashMap<String, String> javaConfig = new HashMap<String, String>();
		javaConfig.put(edu.cmu.cs.ls.keymaerax.tools.KeYmaeraXTool.INIT_DERIVATION_INFO_REGISTRY(), "false");
		javaConfig.put(edu.cmu.cs.ls.keymaerax.tools.KeYmaeraXTool.INTERPRETER(),
				// edu.cmu.cs.ls.keymaerax.bellerophon.LazySequentialInterpreter$.class.getSimpleName());
				edu.cmu.cs.ls.keymaerax.bellerophon.ExhaustiveSequentialInterpreter$.class.getSimpleName());
		Map<String, String> config = toScalaMap(javaConfig);

		edu.cmu.cs.ls.keymaerax.tools.KeYmaeraXTool$.MODULE$.init(config);

		System.out.println("KeYmaeraX initiliazied = " + edu.cmu.cs.ls.keymaerax.tools.KeYmaeraXTool.isInitialized());
		if (edu.cmu.cs.ls.keymaerax.tools.KeYmaeraXTool.isInitialized()) {
			System.out.println(edu.cmu.cs.ls.keymaerax.tools.KeYmaeraXTool.name());
		}
	}

	private KeYmaeraBridge() {
		init();
	}

	/*
	 * Proof is the belle epression! (it seems like...)
	 * Returns list of tuples (T1,T2,T3) with 
	 * 				T1: Name of the proof
	 * 				T2: String content of the whole Proof
	 * 				T3: BelleExpr is the Proof as an expression with all tactics
	 */
	public List<Tuple3<String, String, BelleExpr>> getProof(ParsedArchiveEntry entry) {
		List<Tuple3<String, String, BelleExpr>> tactics = entry.tactics();
		return tactics;
	}

	/*
	 * \brief Uses the master tactic to try to close all proof goals automatically
	 */
	public ProvableSig proveAutomatically(Formula f) {
		ProvableSig sig = edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$.MODULE$.proveBy(f,
				edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$.MODULE$.master(
						edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$.MODULE$.master$default$1(),
						edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$.MODULE$.master$default$2()));
		return sig;
	}
	
	/*
	 * \brief Uses a sequence/belle expr of tactics to prove the formula interactively
	 */
	public ProvableSig prove(Formula f, BelleExpr expr) {
		ProvableSig sig = edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$.MODULE$.proveBy(f, expr);
		return sig;
	}

	public ProvableSig proveAutomatically(Sequent s) {
		ProvableSig sig = edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$.MODULE$
				.proveBy(s,
						edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$.MODULE$
								.master(edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$.MODULE$.master$default$1(),
										edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$.MODULE$.master$default$2())
								.$times(15));
		return sig;
	}

	public ProvableSig prove(DynamicModel dm, String precondition, String postcondition) throws IOException {
		return proveAutomatically(parseProgramAsFormula(dm.createKeYmaeraProgram(precondition, postcondition)));
	}

	public static void main(String[] Args) {

		String contents = "ArchiveEntry \"Static semantics correctness: Assignment 1\"\r\n" + "\r\n"
				+ "Description \"Basic assignment\".\r\n" + "\r\n" + "ProgramVariables\r\n" + "  Real x;\r\n"
				+ "End.\r\n" + "\r\n" + "Problem\r\n" + "  x>=0 -> [x:=x+1;]x>=1\r\n" + "End.\r\nEnd.";

		ProvableSig proof = KeYmaeraBridge.getInstance()
				.proveAutomatically(KeYmaeraBridge.getInstance().parseProgramAsFormula(contents));

		if (proof.isProved()) {
			System.out.println("Proved!!!");
			System.out.println("Steps = " + proof.steps());
			System.out.println("Proof: ");
			System.out.println(proof.prettyString());
		}

		try {
			for (Object entry : KeYmaeraBridge.toJavaList(
					KeYmaeraBridge.getInstance().parseProgramsFromFileAsEntries("resources/simple_proof.kyx").toBuffer())) {
				ParsedArchiveEntry archiveEntry = (ParsedArchiveEntry)entry;
				
				KeYmaeraBridge.getInstance().getProof(archiveEntry);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// KeYmaeraBridge.getInstance().getProof(entry)
	}
}
