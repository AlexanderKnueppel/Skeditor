package de.tubs.skeditor.examples.wizards;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.tubs.skeditor.examples.ExamplePlugin;
import de.tubs.skeditor.examples.utils.ProjectRecord;
import de.tubs.skeditor.examples.utils.ProjectRecordCollection;
import de.tubs.skeditor.examples.utils.ProjectRecordParser;

public class ProjectProvider {
	private static final Collection<ProjectRecord> projects = getProjects();
	private static final Set<String> viewerNames = getViewersNamesForProjects();

	private ProjectProvider() {}

	public static Collection<ProjectRecord> getProjects() {
		if (projects != null) {
			return projects;
		}
		InputStream inputStream = null;
		try {
			final URL url = new URL("platform:/plugin/de.tubs.skeditor.examples/" + ExamplePlugin.EXAMPLE_INDEX);
			inputStream = url.openConnection().getInputStream();
		} catch (final IOException e) {
			ExamplePlugin.getDefault().logError(e);
		}


		return getProjects(inputStream);
	}

	private static Collection<ProjectRecord> getProjects(InputStream inputStream) {
		final ProjectRecordCollection projects = ProjectRecordParser.getProjects(inputStream);

		for (final Iterator<ProjectRecord> iterator = projects.iterator(); iterator.hasNext();) {
			if (!iterator.next().init()) {
				iterator.remove();
			}
		}

		return projects;
	}

	public static Set<String> getViewersNamesForProjects() {
		if (viewerNames != null) {
			return viewerNames;
		}
		final Set<String> viewerNames = new HashSet<>();
		for (final ProjectRecord projectRecord : ProjectProvider.getProjects()) {
			final Document doc = projectRecord.getInformationDocument();

			if (doc != null) {
				final NodeList nlInterfaces = doc.getElementsByTagName("contentProvider");
				for (int i = 0; i < nlInterfaces.getLength(); i++) {
					final Node item = nlInterfaces.item(i);
					if (item.getNodeType() == Node.ELEMENT_NODE) {
						viewerNames.add(((Element) item).getAttribute("name"));
					}
				}
			}
		}
		return viewerNames;
	}

	public static void resetProjectItems() {
		for (final ProjectRecord projectRecord : getProjects()) {
			projectRecord.resetItems();
		}
	}
}
