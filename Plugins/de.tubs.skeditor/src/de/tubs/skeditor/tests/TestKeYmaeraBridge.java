package de.tubs.skeditor.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import de.tubs.skeditor.keymaera.KeYmaeraBridge;
import edu.cmu.cs.ls.keymaerax.pt.ProvableSig;

/**
 * Test class to ensure that the KeymaeraX parsers work properly.
 * 
 * @author Dibo Gonda
 */
public class TestKeYmaeraBridge {

	private final String KEYMAERAX_FILE = "resources/keymaerax_parsing_test.kyx";

	@Test
	public void testFileAndContentParsing() throws IOException, InterruptedException {
		String expectedFormula = "x>=0&x=H()&v=0&g()>0&1>=c()&c()>=0->[{x'=v,v'=-g()&true}{?x=0;v:=-c()*v;++?x>=0;}](x>=0&x<=H())";
		assertEquals(expectedFormula, KeYmaeraBridge.parseProgramFromFileAsFormula(KEYMAERAX_FILE).prettyString());
	
		String content = new String(Files.readAllBytes(Paths.get(KEYMAERAX_FILE)));
		assertEquals(expectedFormula, KeYmaeraBridge.parseProgramAsFormula(content).prettyString());
	}

	@Test
	public void testAutomaticProving() throws IOException {
		String contents = "ArchiveEntry \"Static semantics correctness: Assignment 1\"\r\n" + "\r\n"
				+ "Description \"Basic assignment\".\r\n" + "\r\n" + "ProgramVariables\r\n" + "  Real x;\r\n"
				+ "End.\r\n" + "\r\n" + "Problem\r\n" + "  x>=0 -> [x:=x+1;]x>=1\r\n" + "End.\r\nEnd.";

		ProvableSig provableSig = KeYmaeraBridge.getInstance()
				.proveAutomatically(KeYmaeraBridge.parseProgramAsFormula(contents));
		
		assertTrue(provableSig.isProved());
		assertEquals(24, provableSig.steps());
		assertEquals("ElidingProvable(Provable{==> 1:  x>=0->[x:=x+1;]x>=1	Imply proved})", provableSig.prettyString());
	}
}
