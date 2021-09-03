package de.tubs.skeditor.simulation.plugin.handle;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.widgets.Group;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.tubs.skeditor.simulation.plugin.core.ASimulatorFactory;

public class SimulatorLoader {

	public static List<SimulatorDescription<ASimulatorFactory>> simulatorList = Collections.synchronizedList(new ArrayList<>());
	/**
	 * Loads classes implementing the given interface
	 * 
	 * @param cl Class implementing the interface <b>T</b>
	 * @return List
	 */
	/*public static <T> List<SimulatorDescription<T>> load(Class<T> cl) {
		final List<SimulatorDescription<T>> list = new ArrayList<>();
		for (SimulatorDescription<Object> desc : simulatorList) {
			ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
			try {
				//Class<?> loadClass = systemClassLoader.loadClass(desc.getSimulator());
				list.add(new SimulatorDescription<>(cl.cast(loadClass.newInstance()), desc.getName(), desc.getHandle(),
						desc.getDescription()));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
//			list.add(new SimulatorDescription<T>(cl.cast(desc.getSimulator()), desc.getName(), desc.getHandle(),
//					desc.getDescription()));
		}
		return list;
	}*/
	
//	private static List<SimulatorDescription<String>> registerClasses() {
//		List<SimulatorDescription<String>> simulators = new ArrayList<>();
//		try {
//			Enumeration<URL> urls = ClassLoader.getSystemResources("info_simulator.xml");
//			
//			if(!urls.hasMoreElements()) {
//				System.out.println("No simulator found!");
//			}
//			
//			while (urls.hasMoreElements()) {
//				URL url = urls.nextElement();
//				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//				Document doc = dBuilder.parse(url.openStream());
//
//				String name = "";
//				String desc = "";
//				String handle = "";
//
//				// Get name
//				NodeList nameNode = doc.getElementsByTagName("name");
//				name = nameNode.item(0).getTextContent();
//
//				// Get Desc.
//				NodeList descNode = doc.getElementsByTagName("description");
//				desc = descNode.item(0).getTextContent();
//
//				// Get Handle.
//				NodeList handleNode = doc.getElementsByTagName("handle");
//				handle = handleNode.item(0).getTextContent();
//
//				// Get interface(s)
//
//				/** Get all interfaces inside JAR **/
//				NodeList nlInterfaces = doc.getElementsByTagName("interface");
//
//				if (nlInterfaces.getLength() < 1) {
//					throw new RuntimeException("No interfaces specified...");
//				}
//
//				if (nlInterfaces.getLength() > 1) {
//					throw new RuntimeException("Too many interfaces specified...");
//				}
//
//				Node interfaceNode = nlInterfaces.item(0);
//				//if (interfaceNode.getNodeType() == Node.ELEMENT_NODE) {
//					//String interfaceName = ((Element) interfaceNode).getAttribute("id");
//					/** Get implementing class **/
//
//					NodeList nlClasses = interfaceNode.getChildNodes();
//					String clazz = "";
//					for (int j = 0; j < nlClasses.getLength(); j++) {
//						Node classNode = nlClasses.item(j);
//						//if (classNode.getNodeType() == Node.ELEMENT_NODE) {
//							Element e = (Element) classNode;
//							clazz = classNode.getTextContent();
//						//}
//					}
//
//					/** Update map at key **/
//					System.out.println("add simulator \"" + name + "\" with clazz: " + clazz );
//					simulators.add(new SimulatorDescription<String>(clazz, name, handle, desc));
//				//}
//
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		}
//		return simulators;
//	}
//
//	static {
//		/** Get information about all classes **/
//		List<SimulatorDescription<String>> classes = registerClasses();
//		for (SimulatorDescription<String> clazz : classes) {
//			// List<Object> classInstanceList = new ArrayList<>();
//			ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
//
////			URL[] urls = ((URLClassLoader)systemClassLoader).getURLs();
////
////	        for(URL url: urls){
////	        	System.out.println(url.getFile());
////	        }
//
//			try {
//				Class<?> loadClass = systemClassLoader.loadClass(clazz.getSimulator());
//				simulatorList.add(new SimulatorDescription<>(loadClass.newInstance(), clazz.getName(), clazz.getHandle(),
//						clazz.getDescription()));
//			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * Loads classes implementing the given interface
//	 * 
//	 * @param cl Class implementing the interface <b>T</b>
//	 * @return List
//	 */
//	public static <T> List<SimulatorDescription<T>> load(Class<T> cl) {
//		final List<SimulatorDescription<T>> list = new ArrayList<>();
//		for (SimulatorDescription<Object> desc : simulatorList) {
//			list.add(new SimulatorDescription<T>(cl.cast(desc.getSimulator()), desc.getName(), desc.getHandle(),
//					desc.getDescription()));
//		}
//		return list;
//	}
//		
//	/*
//	public static void main(String[] args) {
//		List<SimulatorDescription<SimulatorFactory>> systems = SimulatorLoader
//				.load(SimulatorFactory.class);
//
//		for (SimulatorDescription<SimulatorFactory> system : systems) {
//			Map<String, String> x = system.getVerifier().createSettingsObject().getSettingsMap();
//
//			System.out.println("Name: " + system.getName());
//			System.out.println("Handle: " + system.getHandle());
//			System.out.println("Desc: " + system.getDescription());
//			System.out.println("Ref: " + system.getVerifier());
//
//		}
//	}
//	*/
//	
//	public static void main(String[] args) {
//		List<SimulatorDescription<ASimulatorFactory>> systems = SimulatorLoader
//				.load(ASimulatorFactory.class);
//
//		if (systems.isEmpty()) {
//			System.err.println("No simulator found! Add plug-ins to the classpath!");
//			return;
//		}
//
//		/*CommandArguments parser = new CommandArguments(args);
//
//		SimulatorDescription<ASimulatorFactory> description;
//		if (parser.hasOption("--simulator")) {
//			String simulator = parser.valueOf("--simulator");
//			Optional<SimulatorDescription<ASimulatorFactory>> system = systems.stream()
//					.filter(desc -> desc.getHandle().equals(simulator)).findFirst();
//
//			if (system.isPresent()) {
//				description = system.get();
//			} else {
//				System.err.println("Simulator '" + parser.valueOf("--simulator") + "' not found! Wrong spelling?");
//				System.err.println("The following simulators are available: ");
//				for (SimulatorDescription<ASimulatorFactory> s : systems) {
//					System.err.println("  -- " + s.getName() + " (command: " + s.getHandle() + ")");
//				}
//				return;
//			}
//		} else {
//			System.out.println("No simulator specified!");
//			description = systems.get(0);
//			System.out.println("Using " + description.getName() + "...");
//		}*/
//	}
}