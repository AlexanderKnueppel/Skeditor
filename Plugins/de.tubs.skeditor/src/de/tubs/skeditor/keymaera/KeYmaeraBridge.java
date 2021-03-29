package de.tubs.skeditor.keymaera;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import de.tubs.skeditor.preferences.SkeditorSettings;
import edu.cmu.cs.ls.keymaerax.bellerophon.BelleExpr;
import edu.cmu.cs.ls.keymaerax.codegen.CControllerGenerator;
import edu.cmu.cs.ls.keymaerax.codegen.CGenerator;
import edu.cmu.cs.ls.keymaerax.core.BaseVariable;
import edu.cmu.cs.ls.keymaerax.core.Box;
import edu.cmu.cs.ls.keymaerax.core.Formula;
import edu.cmu.cs.ls.keymaerax.core.Imply;
import edu.cmu.cs.ls.keymaerax.core.Program;
import edu.cmu.cs.ls.keymaerax.core.Sequent;
import edu.cmu.cs.ls.keymaerax.core.StaticSemantics;
import edu.cmu.cs.ls.keymaerax.hydra.DBAbstractionObj;
import edu.cmu.cs.ls.keymaerax.hydra.UploadArchiveRequest;
import edu.cmu.cs.ls.keymaerax.parser.ArchiveParser;
import edu.cmu.cs.ls.keymaerax.parser.ParsedArchiveEntry;
import edu.cmu.cs.ls.keymaerax.pt.ProvableSig;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.JavaConverters;
import scala.collection.JavaConverters$;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.mutable.Buffer;

public class KeYmaeraBridge {

	private static final KeYmaeraBridge instance = new KeYmaeraBridge();

	public static KeYmaeraBridge getInstance() {
		return instance;
	}

	public static Formula parseProgramFromFileAsFormula(String filename) throws IOException {
		String contents = new String(Files.readAllBytes(Paths.get(filename)));
		return edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXArchiveParser$.MODULE$.parseAsFormula(contents);
	}

	public List<ParsedArchiveEntry> parseProgramsFromFileAsEntries(String filename) throws IOException {
		return edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXArchiveParser$.MODULE$.parseFromFile(filename);
	}

	public static Formula parseProgramAsFormula(String contents) {
		return edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXArchiveParser$.MODULE$.parseAsFormula(contents);
	}

	public static Sequent parseSeqFromString(String content) {
		return edu.cmu.cs.ls.keymaerax.parser.StringConverter$.MODULE$.StringToStringConverter(content).asSequent();
	}

	public ParsedArchiveEntry parseProblem(String content) {
		return edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXArchiveParser$.MODULE$.parseProblem(content, true);
	}

	public static java.util.HashMap<String, String> getMathematicaConfig() {
		java.util.HashMap<String, String> c = new HashMap<String, String>();

		c.put("linkName", SkeditorSettings.getInstance().getMathematicaExe());
		c.put("libDir", SkeditorSettings.getInstance().getMathematicaLibs());

//		c.put("linkName", "C:\\Program Files\\Wolfram Research\\Mathematica\\11.2\\MathKernel.exe"); // path
//		c.put("libDir",
//				"C:\\Program Files\\Wolfram Research\\Mathematica\\11.2\\SystemFiles\\Links\\JLink\\SystemFiles\\Libraries\\Windows-x86-64\\"); // path
		return c;
	}

	public static <A, B> Map<A, B> toScalaMap(HashMap<A, B> m) {
		return JavaConverters$.MODULE$.mapAsScalaMapConverter(m).asScala()
				.toMap(scala.Predef$.MODULE$.<scala.Tuple2<A, B>>$conforms());
	}

	public static java.util.HashMap<String, String> getZ3Config() {
		java.util.HashMap<String, String> c = new HashMap<String, String>();
		c.put("z3Path", SkeditorSettings.getInstance().getZ3Exe());
		return c;
	}

	public static <A> java.util.List<A> toJavaList(Buffer<A> b) {
		return JavaConverters$.MODULE$.bufferAsJavaList(b);
	}

	private void init() {
		setProver();

		edu.cmu.cs.ls.keymaerax.Configuration$.MODULE$
				.setConfiguration(edu.cmu.cs.ls.keymaerax.FileConfiguration$.MODULE$);

		HashMap<String, String> javaConfig = new HashMap<String, String>();
		javaConfig.put(edu.cmu.cs.ls.keymaerax.tools.KeYmaeraXTool.INIT_DERIVATION_INFO_REGISTRY(), "false");
		javaConfig.put(edu.cmu.cs.ls.keymaerax.tools.KeYmaeraXTool.INTERPRETER(),
				edu.cmu.cs.ls.keymaerax.bellerophon.ExhaustiveSequentialInterpreter$.class.getSimpleName());
		Map<String, String> config = toScalaMap(javaConfig);

		edu.cmu.cs.ls.keymaerax.tools.KeYmaeraXTool$.MODULE$.init(config);

		System.out.println("KeYmaeraX initiliazied = " + edu.cmu.cs.ls.keymaerax.tools.KeYmaeraXTool.isInitialized());
		if (edu.cmu.cs.ls.keymaerax.tools.KeYmaeraXTool.isInitialized()) {
			System.out.println(edu.cmu.cs.ls.keymaerax.tools.KeYmaeraXTool.name());
		}
	}
	
	public void setProver() {
		switch(SkeditorSettings.getInstance().getSelectedProver()) {
		case "z3":
			edu.cmu.cs.ls.keymaerax.btactics.ToolProvider$.MODULE$
			.setProvider(new edu.cmu.cs.ls.keymaerax.btactics.Z3ToolProvider(toScalaMap(getZ3Config())));
			break;
		case "mathematica":
			edu.cmu.cs.ls.keymaerax.btactics.ToolProvider$.MODULE$
			.setProvider(new edu.cmu.cs.ls.keymaerax.btactics.MathematicaToolProvider(toScalaMap(getMathematicaConfig())));	
			break;
		}
	}

	private KeYmaeraBridge() {
		init();
	}

	public void launchKeYmaera() {
		edu.cmu.cs.ls.keymaerax.launcher.Main.main(new String[0]);
		// DBAbstractionObj.defaultDatabase().createModel(userId, name, fileContents,
		// date, description, publink, title, tactic)
	}

	public void uploadArchiveToKeYmaera() {

		// UploadArchiveRequest upload = new UploadArchiveRequest(DBAbstractionObj.,
		// "guest", "tst", "");
	}

	/*
	 * Proof is the belle epression! (it seems like...) Returns list of tuples
	 * (T1,T2,T3) with T1: Name of the proof T2: String content of the whole Proof
	 * T3: BelleExpr is the Proof as an expression with all tactics
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
	 * \brief Uses a sequence/belle expr of tactics to prove the formula
	 * interactively
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
	
	public java.util.List<String> generateCCode(String hybridProgram) {
 		scala.collection.immutable.List<ParsedArchiveEntry> archives = ArchiveParser.parser().parse(hybridProgram,
				true);

 		java.util.List<String> results=new java.util.ArrayList<>(); 
		for (int i = 0; i < archives.length(); i++) {
			ParsedArchiveEntry archive = archives.apply(i);
			Formula model = (Formula) archive.model();
			Program prg = ((Box) ((Imply) model).right()).program();

			scala.collection.immutable.List<Object> symbols = StaticSemantics.boundVars(model).symbols().toList();
			ArrayList<BaseVariable> vars = new ArrayList<>();
			for (int j = 0; j < symbols.size(); j++) {
				Object s = symbols.apply(j);
				if (s instanceof BaseVariable) {
					vars.add((BaseVariable) s);
				}
			}

			Tuple2<String, String> result = new CGenerator(new CControllerGenerator()).apply(prg,
					JavaConverters.asScalaSet(vars.stream().collect(Collectors.toSet())).toSet(),
					CGenerator.getInputs(prg));
			results.add(result._1);
		}
		return results;
	}

}


