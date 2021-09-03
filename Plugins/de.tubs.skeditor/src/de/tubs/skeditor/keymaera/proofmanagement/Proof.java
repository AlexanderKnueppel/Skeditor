package de.tubs.skeditor.keymaera.proofmanagement;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.tubs.skeditor.keymaera.KeYmaeraBridge;
import edu.cmu.cs.ls.keymaerax.bellerophon.BelleExpr;
import edu.cmu.cs.ls.keymaerax.parser.ParsedArchiveEntry;

public class Proof {
	final long creationTime = System.currentTimeMillis();

	private final String name;
	private final String content;

	private BelleExpr proofExpression;

	/**
	 * The {@link File} under which this {@link Proof} was saved the last time if
	 * available or {@code null} otherwise.
	 */
	private File proofFile;

	public Proof(String name, String content, BelleExpr expr) {
		this.name = name;
		this.content = content;
		this.proofExpression = expr;
	}

	public Proof(File file) throws IOException {
		proofFile = file;
		ParsedArchiveEntry entry = KeYmaeraBridge.getInstance().parseProblem(
				String.join("\n", Files.readAllLines(Paths.get(file.getPath()), Charset.defaultCharset())));
		
		this.name = KeYmaeraBridge.getInstance().getProof(entry).head()._1();
		this.content = KeYmaeraBridge.getInstance().getProof(entry).head()._2();
		this.proofExpression = KeYmaeraBridge.getInstance().getProof(entry).head()._3();
	}

	public String toString() {
		return name + ":\n" + content;
	}

}
