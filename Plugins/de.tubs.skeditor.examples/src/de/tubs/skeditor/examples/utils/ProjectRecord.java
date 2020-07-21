package de.tubs.skeditor.examples.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IContentProvider;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.tubs.skeditor.examples.ExamplePlugin;

public class ProjectRecord implements Comparable<ProjectRecord> {

	public static final String PROJECT_INFORMATION_XML = "projectInformation.xml";
	public static final String INDEX_FILENAME = "index.fileList";

	private final String projectDescriptionRelativePath;
	private final String projectName;

	private IProjectDescription projectDescription;
	private String warning;
	private String error;
	private String description;
	private List<TreeItem> contentProviderItems;
	private boolean hasWarnings = false;
	private boolean hasErrors = false;
	private boolean updated = false;

	/**
	 * Create a record for a project based on the info given in the file.
	 *
	 * @param file
	 */
	public ProjectRecord(String projectDescriptionRelativePath, String projectName) {
		this.projectName = projectName;
		this.projectDescriptionRelativePath = projectDescriptionRelativePath;
		initFields();
	}

	/**
	 * Deserialization
	 *
	 * @param in
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		initFields();
	}

	private void initFields() {
		warning = "";
		hasWarnings = false;
		error = "";
		hasErrors = false;
		contentProviderItems = new ArrayList<>();
	}

	public List<TreeItem> getTreeItems() {
		return contentProviderItems;
	}

	public class TreeItem {

		private final IContentProvider contProv;

		public TreeItem(IContentProvider contProv) {
			this.contProv = contProv;
		}

		public ProjectRecord getRecord() {
			init();
			return ProjectRecord.this;
		}

		@Override
		public String toString() {
			return getProjectName();
		}

		public IContentProvider getProvider() {
			return contProv;
		}

	}

	@Override
	public String toString() {
		return getProjectName();
	}

	public TreeItem createNewTreeItem(IContentProvider prov) {
		final TreeItem ti = new TreeItem(prov);
		contentProviderItems.add(ti);
		return ti;
	}

	public boolean init() {
		try (InputStream inputStream = new URL(
				"platform:/plugin/de.tubs.skeditor.examples/" + projectDescriptionRelativePath).openConnection()
						.getInputStream()) {
			projectDescription = ResourcesPlugin.getWorkspace().loadProjectDescription(inputStream);
		} catch (IOException | CoreException e) {
			ExamplePlugin.getDefault().logError(e);
			return false;
		}

		if (projectDescription != null) {
			if (getInformationDocument().getElementsByTagName("description").getLength() > 0) {
				description = getInformationDocument().getElementsByTagName("description").item(0).getTextContent();
			}

			performAlreadyExistsCheck();
			performRequirementCheck();
		} else {
			return false;
		}
		return true;
	}

	private void performAlreadyExistsCheck() {
		hasErrors = false;
		error = "";
		if (isProjectInWorkspace(getProjectName())) {
			error += "THIS EXAMPLE ALREADY EXISTS IN THE WORKSPACE DIRECTORY";
			hasErrors = true;
		}
	}

	private void performRequirementCheck() {
		hasWarnings = false;
		warning = "";
		final String[] natures = projectDescription.getNatureIds();
		IStatus status = ResourcesPlugin.getWorkspace().validateNatureSet(natures);

		if (!status.isOK()) {
			warning = status.getMessage();
			if (status instanceof MultiStatus) {
				final MultiStatus multi = (MultiStatus) status;
				if (multi.getChildren().length > 0) {
					warning += " (";
					for (int j = 0; j < (multi.getChildren().length - 1); j++) {
						warning += multi.getChildren()[j].getMessage() + " ;";
					}
					warning += multi.getChildren()[multi.getChildren().length - 1].getMessage() + ")";
				}
			}
			hasWarnings = true;
		}
	}

	/**
	 * Get the name of the project
	 *
	 * @return String
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Get the description of the project
	 *
	 * @return String
	 */
	public String getDescription() {
		return description == null ? "" : description;
	}

	public boolean hasWarnings() {
		return hasWarnings;
	}

	public String getWarningText() {
		return warning;
	}

	public boolean hasErrors() {
		return hasErrors;
	}

	public String getErrorText() {
		return error;
	}

	/**
	 * Gets the label to be used when rendering this project record in the UI.
	 *
	 * @return String the label
	 * @since 3.4
	 */
	public String getProjectLabel() {
		return projectName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + projectDescriptionRelativePath.hashCode();
		result = (prime * result) + projectName.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ProjectRecord other = (ProjectRecord) obj;
		return projectDescriptionRelativePath.equals(other.projectDescriptionRelativePath)
				&& projectName.equals(other.projectName);
	}

	@Override
	public int compareTo(ProjectRecord o) {
		return projectDescriptionRelativePath.compareTo(o.projectDescriptionRelativePath);
	}

	/**
	 * Determine if the project with the given name is in the current workspace.
	 *
	 * @param projectName
	 *            String the project name to check
	 * @return boolean true if the project with the given name is in this workspace
	 */
	protected static boolean isProjectInWorkspace(String projectName) {
		if (projectName == null) {
			return false;
		}
		final IProject[] workspaceProjects = getProjectsInWorkspace();
		for (int i = 0; i < workspaceProjects.length; i++) {
			if (projectName.equals(workspaceProjects[i].getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retrieve all the projects in the current workspace.
	 *
	 * @return IProject[] array of IProject in the current workspace
	 */
	private static IProject[] getProjectsInWorkspace() {
		return ResourcesPlugin.getWorkspace().getRoot().getProjects();
	}

	public String getRelativePath() {
		// TODO Use general root path
		return new Path(projectDescriptionRelativePath).removeFirstSegments(1).removeLastSegments(1).toString();
	}

	public String getIndexDocumentPath() {
		return projectDescriptionRelativePath.replace(".project", INDEX_FILENAME);
	}

	public String getInformationDocumentPath() {
		return projectDescriptionRelativePath.replace(".project", PROJECT_INFORMATION_XML);
	}

	public IProjectDescription getProjectDescription() {
		return projectDescription;
	}

	public Document getInformationDocument() {
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			final InputStream inputStream = new URL(
					"platform:/plugin/de.tubs.skeditor.examples/" + getInformationDocumentPath())
							.openConnection().getInputStream();
			final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			final Document doc = dBuilder.parse(inputStream);
			return doc;
		} catch (IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void resetItems() {
		contentProviderItems.clear();
	}

	public boolean updated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public String getProjectDescriptionRelativePath() {
		return projectDescriptionRelativePath;
	}

}
