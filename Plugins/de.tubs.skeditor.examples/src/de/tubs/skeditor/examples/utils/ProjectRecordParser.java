package de.tubs.skeditor.examples.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ProjectRecordParser {
	private static final String ATTRIBUTE_PATH = "path";
	private static final String ATTRIBUTE_NAME = "name";
	private static final String PROJECT = "project";
	private static final String PROJECT_RECORDS = "projectRecords";

	ProjectRecordCollection collection = new ProjectRecordCollection();

	public static ProjectRecordCollection getProjects(InputStream input) {
		ProjectRecordParser parser = new ProjectRecordParser();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(input);
			doc.getDocumentElement().normalize();
			parser.readDocument(doc);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parser.collection;
	}

	protected void readDocument(Document doc) {
		final NodeList projectRecords = doc.getElementsByTagName(PROJECT_RECORDS);
		for (int i = 0; i < projectRecords.getLength(); ++i) {
			Node node = projectRecords.item(i);

			parseProjects(node.getChildNodes());
		}
	}

	private void parseProjects(NodeList nodeList) {

		for (int i = 0; i < nodeList.getLength(); ++i) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nodeList.item(i);
				if (PROJECT.equals(element.getTagName())) {
					final String name = element.getAttribute(ATTRIBUTE_NAME);
					final String path = element.getAttribute(ATTRIBUTE_PATH);
					final ProjectRecord projectRecord = new ProjectRecord(path, name);
					collection.add(projectRecord);
				}
			}
		}
	}

	public String getName() {
		return "";
	}
}
