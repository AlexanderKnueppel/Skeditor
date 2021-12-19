package de.tubs.skeditor.HMA.testbench;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EMOFResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import SkillGraph.Category;
import SkillGraph.Graph;
import SkillGraph.Node;
import de.tubs.skeditor.HMA.HybridModeAutomaton;
import de.tubs.skeditor.HMA.composition.Resolver;
import de.tubs.skeditor.HMA.utils.HMAUtil;
import de.tubs.skeditor.keymaera.generator.HMATranslator;
import de.tubs.skeditor.keymaera.generator.ProblemGenerator;

public class MainTest {

	private static Graph parseSked(String skedFile) {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("sked", new EMOFResourceFactoryImpl());

		//Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createFileURI(skedFile);
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(rs);
		if (editingDomain == null) {
			editingDomain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(rs);
		}

		Resource r = rs.getResource(uri, true);
		try {
			r.load(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Object o : r.getContents()) {
			if (o instanceof Graph) {
				return (Graph) o;
			}
		}
		return null;

	}

	public static void test(Graph sg) {
		System.out.println("Start Test");
		for (Node skill : sg.getNodes()) {
			if (skill.getCategory().equals(Category.ACTION)
					|| skill.getCategory().equals(Category.OBSERVABLE_EXTERNAL_BEHAVIOR)) {
				System.out.println(ProblemGenerator.generateMonolithicProblemForSkill(skill));
			}
		}
		
	}
	
	public static void test2(Graph sg) {
		//Graph sg = parseSked("resources/SimpleThermostat.sked");
		System.out.println("Start Test");
		Set<HybridModeAutomaton> hma = new HashSet<HybridModeAutomaton>();
		HybridModeAutomaton root = null;
		for (Node skill : sg.getNodes()) {
			if (skill.getCategory().equals(Category.ACTION)
					|| skill.getCategory().equals(Category.OBSERVABLE_EXTERNAL_BEHAVIOR)) {
				HybridModeAutomaton tmp = HMAUtil.createHMAFromSKill(skill);
				hma.add(tmp);

				if (skill.getCategory().equals(Category.OBSERVABLE_EXTERNAL_BEHAVIOR)) {
					root = tmp;
				}
			}
		}

		HybridModeAutomaton finalHMA = Resolver.resolveAllSubmodes(root, hma.toArray(new HybridModeAutomaton[0]));
		System.out.println(HMATranslator.translateHMA(finalHMA));
	}
}
