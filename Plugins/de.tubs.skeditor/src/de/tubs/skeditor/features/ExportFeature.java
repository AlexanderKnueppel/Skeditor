package de.tubs.skeditor.features;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import SkillGraph.Graph;
import SkillGraph.Node;
import de.tubs.skeditor.utils.ColorUtil;
import de.tubs.skeditor.utils.GraphUtil;

public class ExportFeature extends AbstractCustomFeature {
	ArrayList<Node> visited = new ArrayList<Node>();

	StringBuilder stringBuilder = new StringBuilder();

	public ExportFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Export Graph to DOT";
	}

	@Override
	public String getDescription() {
		return "Exports the Graph to a DOT file";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		File file = new File(org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot().getLocation() + "\\" + getDiagram().getName() + ".dot");
		Node node = ((Graph) getBusinessObjectForPictogramElement(getDiagram())).getRootNode();
		System.out.println( ((Graph) getBusinessObjectForPictogramElement(getDiagram())).getNodes());
		if (node == null) {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Root Node not found!");
			return;
		}
		stringBuilder = new StringBuilder();
		append("digraph ");
		append(getDiagram().getName());
		append(" {");
		newLine();
		printNodeRelations(node);
		stringBuilder.append("}");
		FileWriter writer;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new FileWriter(file);
			writer.write(stringBuilder.toString());
			writer.close();
		} catch (IOException e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Something went wort" + e);
		}
		if (visited.containsAll(((Graph) getBusinessObjectForPictogramElement(getDiagram())).getNodes())) {
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Information", ".dot File exportet to: " + file.getAbsolutePath());
		} else {
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Warning",
					"Not all Nodes included!\n.dot File exportet to: " + file.getAbsolutePath());
		}

	}

	private void append(String text) {
		stringBuilder.append(text);
	}

	private void newLine() {
		stringBuilder.append("\n");
	}

	private void printNodeRelations(Node node) {
		visited.add(node);
		append("   \"");
		append(node.getName());
		append("\" [shape=box, style=\"filled, rounded\", fillcolor=\"#");
		append(ColorUtil.getCategoryColorRGB(node.getCategory().getLiteral()));
		append("\"];");
		newLine();
		for (Node child : GraphUtil.getChildNodes(node)) {
			append("   \"");
			append(node.getName());
			append("\" -> \"");
			append(child.getName());
			append("\";");
			newLine();
			if (!visited.contains(child)) {
				printNodeRelations(child);
			}

		}
	}
}
