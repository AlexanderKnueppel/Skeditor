package de.tubs.skeditor.diagram;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;
import org.eclipse.graphiti.mm.pictograms.PictogramsFactory;
import org.eclipse.graphiti.platform.IDiagramBehavior;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

import SkillGraph.Graph;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.utils.ViewUtil;

public class DiagramTypeProvider extends AbstractDiagramTypeProvider {

	private IToolBehaviorProvider[] toolBehaviorProviders;

	public DiagramTypeProvider() {
		super();
		setFeatureProvider(new FeatureProvider(this));
	}
	
	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			toolBehaviorProviders = new IToolBehaviorProvider[] { new ToolBehaviorProvider(this) };
		}
		return toolBehaviorProviders;
	}

	@Override
	public void init(Diagram diagram, IDiagramBehavior diagramBehavior) {
		super.init(diagram, diagramBehavior);
		// linking up the Diagram with the Graph is necessary so that the skill graph
		// can be serialized, else saving wont be possible
		if (getFeatureProvider().getBusinessObjectForPictogramElement(diagram) == null) {
			Graph graph = SkillGraphFactory.eINSTANCE.createGraph();
			PictogramLink link = PictogramsFactory.eINSTANCE.createPictogramLink();
			link.getBusinessObjects().add(graph);
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(diagram);
			domain.getCommandStack().execute(new RecordingCommand(domain) {
				@Override
				protected void doExecute() {
					diagram.setLink(link);
					diagram.eResource().getContents().add(graph);
				}
			});
		}
		ViewUtil.updateViews((Graph) getFeatureProvider().getBusinessObjectForPictogramElement(diagram));
	}
}
