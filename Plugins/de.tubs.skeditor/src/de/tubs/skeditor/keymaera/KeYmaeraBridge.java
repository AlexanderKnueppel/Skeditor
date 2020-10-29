package de.tubs.skeditor.keymaera;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import edu.cmu.cs.ls.keymaerax.bellerophon.BelleExpr;
import edu.cmu.cs.ls.keymaerax.core.Formula;
import edu.cmu.cs.ls.keymaerax.core.Sequent;
import edu.cmu.cs.ls.keymaerax.pt.ProvableSig;
import scala.Predef;
import scala.Tuple2;
import scala.collection.JavaConverters;
import scala.collection.immutable.Map;

public class KeYmaeraBridge {

	static final edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXParser$ parser = edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXParser$.MODULE$;
	static final edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXArchiveParser$ archiveParser = edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXArchiveParser$.MODULE$;
	static final edu.cmu.cs.ls.keymaerax.parser.StringConverter$ stringConverter = edu.cmu.cs.ls.keymaerax.parser.StringConverter$.MODULE$;
	static final edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXPrettyPrinter$ prettyPrinter = edu.cmu.cs.ls.keymaerax.parser.KeYmaeraXPrettyPrinter$.MODULE$;
	static final edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$ God = edu.cmu.cs.ls.keymaerax.btactics.TactixLibrary$.MODULE$;
	static final edu.cmu.cs.ls.keymaerax.btactics.AxiomaticODESolver$ axiomSolver = edu.cmu.cs.ls.keymaerax.btactics.AxiomaticODESolver$.MODULE$;

	static final edu.cmu.cs.ls.keymaerax.pt.ProvableSig$ provSig = edu.cmu.cs.ls.keymaerax.pt.ProvableSig$.MODULE$;

	static final edu.cmu.cs.ls.keymaerax.btactics.DifferentialTactics$ diffTactics = edu.cmu.cs.ls.keymaerax.btactics.DifferentialTactics$.MODULE$;

	static final BelleExpr master = God.master(God.master$default$1(), God.master$default$2());

	static final edu.cmu.cs.ls.keymaerax.bellerophon.BelleInterpreter$ belleInterpreter = edu.cmu.cs.ls.keymaerax.bellerophon.BelleInterpreter$.MODULE$;

	public static Formula parseProgramFromFile(String filename) throws IOException {
		String contents = new String(Files.readAllBytes(Paths.get(filename)));
		return archiveParser.parseAsFormula(contents);
	}

	public static Sequent parseSeqFromString(String content) {
		return stringConverter.StringToStringConverter(content).asSequent();
	}

	public static Formula parseProgram(String contents) {
		return archiveParser.parseAsFormula(contents);
	}

	public static java.util.HashMap<String, String> getConfig() {
		java.util.HashMap<String, String> c = new HashMap<String, String>();
		c.put("linkName", "C:\\Program Files\\Wolfram Research\\Mathematica\\11.2\\MathKernel.exe"); // path
		c.put("libDir", "C:\\Program Files\\Wolfram Research\\Mathematica\\11.2\\SystemFiles\\Links\\JLink\\SystemFiles\\Libraries\\Windows-x86-64\\"); // path
		return c;
	}

	public static <A, B> Map<A, B> toScalaMap(HashMap<A, B> m) {
		return JavaConverters.mapAsScalaMapConverter(m).asScala().toMap(Predef.<Tuple2<A, B>>conforms());
	}

	private void init() {
		//belleInterpreter.setInterpreter(new edu.cmu.cs.ls.keymaerax.bellerophon.SequentialInterpreter(edu.cmu.cs.ls.keymaerax.bellerophon.SequentialInterpreter.apply$default$1()));

		edu.cmu.cs.ls.keymaerax.btactics.ToolProvider$.MODULE$.setProvider(new edu.cmu.cs.ls.keymaerax.btactics.Z3ToolProvider(null));
		// edu.cmu.cs.ls.keymaerax.btactics.ToolProvider$.MODULE$.setProvider(new
		// edu.cmu.cs.ls.keymaerax.btactics.MathematicaToolProvider(toScalaMap(getConfig())));

	}

	public KeYmaeraBridge() {
		init();
	}

	public ProvableSig prove(Formula f) {
		ProvableSig sig = God.proveBy(f, master);
		return sig;
	}

	public ProvableSig prove(Sequent s) {
		ProvableSig sig = God.proveBy(s, God.master(God.master$default$1(), God.master$default$2()).$times(15));
		return sig;
	}

	public ProvableSig prove(DynamicModel dm, String precondition, String postcondition) throws IOException {
		return prove(parseProgram(dm.createKeYmaeraProgram(precondition, postcondition)));
	}
}
