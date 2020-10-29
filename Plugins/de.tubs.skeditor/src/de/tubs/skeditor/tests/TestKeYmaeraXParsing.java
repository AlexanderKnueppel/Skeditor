package de.tubs.skeditor.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import de.tubs.skeditor.keymaera.Expression;
import de.tubs.skeditor.keymaera.KeYmaeraBridge;
import edu.cmu.cs.ls.keymaerax.core.Formula;

/**
 * Test class to ensure that the KeymaeraX parsers work properly.
 * 
 * @author Dibo Gonda
 */
public class TestKeYmaeraXParsing {
	
	private final String KEYMAERAX_FILE = "resources/keymaerax_parsing_test.kyx";

	@Test
	public void testFileParsing() throws IOException {
		Formula expectedFormula = null; // TODO
		assertEquals(expectedFormula, KeYmaeraBridge.parseProgramFromFile(KEYMAERAX_FILE));
	}
	
	@Test
	public void testContentParsing() throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(KEYMAERAX_FILE)));
		Formula expectedFormula = null; // TODO
		assertEquals(expectedFormula, KeYmaeraBridge.parseProgram(content));
	}
}
