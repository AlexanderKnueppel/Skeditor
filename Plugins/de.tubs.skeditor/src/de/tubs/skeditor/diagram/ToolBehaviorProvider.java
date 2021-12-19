package de.tubs.skeditor.diagram;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.context.impl.DirectEditingContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Font;
import org.eclipse.graphiti.mm.algorithms.styles.TextStyle;
import org.eclipse.graphiti.mm.algorithms.styles.TextStyleRegion;
import org.eclipse.graphiti.mm.algorithms.styles.UnderlineStyle;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.platform.IPlatformImageConstants;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.tb.ContextButtonEntry;
import org.eclipse.graphiti.tb.ContextMenuEntry;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IContextButtonPadData;
import org.eclipse.graphiti.tb.IContextMenuEntry;
import org.eclipse.graphiti.tb.IDecorator;
import org.eclipse.graphiti.tb.IImageDecorator;
import org.eclipse.graphiti.tb.ImageDecorator;

import SkillGraph.Category;
import SkillGraph.Graph;
import SkillGraph.Node;
import de.tubs.skeditor.ImageProvider;
import de.tubs.skeditor.contracting.Contract;
import de.tubs.skeditor.contracting.ContractPropagator;
import de.tubs.skeditor.features.AddAssumptionFeature;
import de.tubs.skeditor.features.AddSafetyRequirementsFeature;
import de.tubs.skeditor.features.ChangeCategoryFeature;
import de.tubs.skeditor.features.CreateKeymaeraFileFeature;
import de.tubs.skeditor.features.EditControllerFeature;
import de.tubs.skeditor.features.EditProgramPathFeature;
import de.tubs.skeditor.features.EditSkillDescriptionFeature;
import de.tubs.skeditor.features.EditVariableFeature;
import de.tubs.skeditor.features.ExportFeature;
import de.tubs.skeditor.features.LaunchKeymaeraFeature;
import de.tubs.skeditor.features.RunKeymaeraCheckFeature;
import de.tubs.skeditor.features.SetRootNodeFeature;
import de.tubs.skeditor.features.TestFeature;
import de.tubs.skeditor.utils.ConstraintUtil;
import de.tubs.skeditor.utils.ViewUtil;

public class ToolBehaviorProvider extends DefaultToolBehaviorProvider {

	public ToolBehaviorProvider(IDiagramTypeProvider diagramTypeProvider) {
		super(diagramTypeProvider);
	}

	@Override
	public IContextMenuEntry[] getContextMenu(ICustomContext context) {
		ArrayList<ContextMenuEntry> entries = new ArrayList<ContextMenuEntry>();
		ContextMenuEntry checkbox = new ContextMenuEntry(null, context);
		//checkbox.
		ContextMenuEntry subMenu = new ContextMenuEntry(null, context);
		subMenu.setText("KeYmaera"); //$NON-NLS-1$
		subMenu.setDescription("KeYmaera entries"); //$NON-NLS-1$
		subMenu.setSubmenu(true);
		
		for (ICustomFeature customFeature : getFeatureProvider().getCustomFeatures(context)) {
			if (customFeature instanceof ExportFeature) {
				entries.add(new ContextMenuEntry(customFeature, context));
			} if (customFeature instanceof TestFeature) {
				entries.add(new ContextMenuEntry(customFeature, context));
			} else if (customFeature instanceof RunKeymaeraCheckFeature) {
				subMenu.add(new ContextMenuEntry(customFeature, context));
			} else if (customFeature instanceof LaunchKeymaeraFeature) {
				subMenu.add(new ContextMenuEntry(customFeature, context));
			} else if (customFeature instanceof EditControllerFeature) {
				entries.add(new ContextMenuEntry(customFeature, context));
			} else if (customFeature instanceof EditSkillDescriptionFeature) {
				entries.add(new ContextMenuEntry(customFeature, context));
			} else if (customFeature instanceof CreateKeymaeraFileFeature) {
				subMenu.add(new ContextMenuEntry(customFeature, context));
			} else if (customFeature instanceof EditProgramPathFeature) {
				entries.add(new ContextMenuEntry(customFeature, context));
			} else if (customFeature instanceof SetRootNodeFeature) {
				entries.add(new ContextMenuEntry(customFeature, context));
			} else if (customFeature instanceof EditVariableFeature) {
				entries.add(new ContextMenuEntry(customFeature, context));
			} 
		}
		entries.add(subMenu);
		return entries.toArray(new IContextMenuEntry[entries.size()]);
	}
	
	//Maybe you have based your tool on the tutorial, there in the
//	//TutorialToolBehaviorProvider we have:
//	@Override
//	public IContextMenuEntry[] getContextMenu(ICustomContext context) {
//		// create a sub-menu for all custom features
//		ContextMenuEntry subMenu = new ContextMenuEntry(null, context);
//		subMenu.setText("Cu&stom"); //$NON-NLS-1$
//		subMenu.setDescription("Custom features submenu"); //$NON-NLS-1$
//		// display sub-menu hierarchical or flat
//		subMenu.setSubmenu(true);
//	
//		// create a menu-entry in the sub-menu for each custom feature
//		ICustomFeature[] customFeatures =
//		getFeatureProvider().getCustomFeatures(context);
//		for (int i = 0; i < customFeatures.length; i++) {
//			ICustomFeature customFeature = customFeatures[i];
//			if (customFeature.isAvailable(context)) {
//				ContextMenuEntry menuEntry = new ContextMenuEntry(customFeature, context);
//				subMenu.add(menuEntry);
//			}
//		}
//
//		IContextMenuEntry ret[] = new IContextMenuEntry[] { subMenu };
//		return ret;
//	}

	@Override
	public IContextButtonPadData getContextButtonPad(IPictogramElementContext context) {
		IContextButtonPadData data = super.getContextButtonPad(context);
		PictogramElement pictogramElement = context.getPictogramElement();
		setGenericContextButtons(data, pictogramElement, CONTEXT_BUTTON_DELETE);
		Node node = (Node) context.getPictogramElement().getLink().getBusinessObjects().get(0);
		CustomContext customCatButtonContext = new CustomContext(new PictogramElement[] { pictogramElement });
		ICustomFeature[] customFeatures = getFeatureProvider().getCustomFeatures(customCatButtonContext);

		ContextButtonEntry changeCatButton = new ContextButtonEntry(null, customCatButtonContext);
		changeCatButton.setText("Change Category");
		changeCatButton.setIconId(ImageProvider.IMG_CHANGE_CAT);
		data.getGenericContextButtons().add(changeCatButton);

		Graph graph = (Graph) getFeatureProvider()
				.getBusinessObjectForPictogramElement(getDiagramTypeProvider().getDiagram());
		boolean mainFound = graph.getRootNode() != null;
		for (ICustomFeature iCustomFeature : customFeatures) {
			if (iCustomFeature instanceof ChangeCategoryFeature) {
				ChangeCategoryFeature changeCatFeature = (ChangeCategoryFeature) iCustomFeature;
				if ((changeCatFeature.getCATEGORY() == Category.MAIN && mainFound)
						|| !ConstraintUtil.areConstraintsValid(node, changeCatFeature.getCATEGORY())) {
					continue;
				}
				ContextButtonEntry changeButton = new ContextButtonEntry(changeCatFeature, customCatButtonContext);
				String name = changeCatFeature.getCATEGORY().getName().replace('_', ' ');
				name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
				changeButton.setText("Change to " + name);
				changeCatButton.addContextButtonMenuEntry(changeButton);

			}

//			if (iCustomFeature instanceof AddSafetyRequirementsFeature && node.getCategory() != Category.MAIN) {
			if (iCustomFeature instanceof AddSafetyRequirementsFeature) {
				AddSafetyRequirementsFeature reqFeature = (AddSafetyRequirementsFeature) iCustomFeature;
				ContextButtonEntry addReqButton = new ContextButtonEntry(reqFeature, customCatButtonContext);
				addReqButton.setText("New Requirement");
				data.getGenericContextButtons().add(addReqButton);

			}

			if (iCustomFeature instanceof AddAssumptionFeature) {
				AddAssumptionFeature asFeature = (AddAssumptionFeature) iCustomFeature;
				ContextButtonEntry addAsButton = new ContextButtonEntry(asFeature, customCatButtonContext);
				addAsButton.setText("New Assumption");
				data.getGenericContextButtons().add(addAsButton);

			}
		}

		if (pictogramElement instanceof ContainerShape) {
			ContainerShape containerShape = (ContainerShape) pictogramElement;
			EList<Shape> containerChildren = containerShape.getChildren();
			if (containerChildren.size() > 0 && containerChildren.get(0).getGraphicsAlgorithm() instanceof MultiText) {
				DirectEditingContext editContext = new DirectEditingContext(containerChildren.get(0),
						containerChildren.get(0).getGraphicsAlgorithm());
				IDirectEditingFeature editFeature = getFeatureProvider().getDirectEditingFeature(editContext);

				ContextButtonEntry editButton = new ContextButtonEntry(editFeature, context);
				editButton.setText("Edit label");
				editButton.setIconId(ImageProvider.IMG_EDIT_PEN);
				data.getGenericContextButtons().add(editButton);
			}
		}
		CreateConnectionContext ccc = new CreateConnectionContext();
		ccc.setSourcePictogramElement(pictogramElement);
		Anchor anchor = null;
		if (pictogramElement instanceof Anchor) {
			anchor = (Anchor) pictogramElement;
		} else if (pictogramElement instanceof AnchorContainer) {
			anchor = Graphiti.getPeService().getChopboxAnchor((AnchorContainer) pictogramElement);
		}
		ccc.setSourceAnchor(anchor);
		ContextButtonEntry button = new ContextButtonEntry(null, context);
		button.setText("Create connection");
		button.setIconId(ImageProvider.IMG_ARROW_HEAD);
		ICreateConnectionFeature[] features = getFeatureProvider().getCreateConnectionFeatures();
		for (ICreateConnectionFeature feature : features) {
			if (feature.isAvailable(ccc) && feature.canStartConnection(ccc))
				button.addDragAndDropFeature(feature);
		}

		if (button.getDragAndDropFeatures().size() > 0) {
			data.getDomainSpecificContextButtons().add(button);
		}

		return data;
	}

	@Override
	public ICustomFeature getDoubleClickFeature(IDoubleClickContext context) {
//		if (getFeatureProvider()
//				.getBusinessObjectForPictogramElement(context.getInnerPictogramElement()) instanceof Node) {
//			Node node = (Node) getFeatureProvider()
//					.getBusinessObjectForPictogramElement(context.getInnerPictogramElement());
//			ViewUtil.updateDiffView(node);
//		}
		return super.getDoubleClickFeature(context);
	}

	@Override
	public Object getToolTip(GraphicsAlgorithm graphicsAlgorithm) {
		PictogramElement pe = graphicsAlgorithm.getPictogramElement();
		Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if (bo instanceof Node) {

			Contract c = ContractPropagator.computeContract((Node) bo);

			String assume = "Assume:";
			String safe = "Safe:";
			String toolTip = assume + "\n" + c.getAssumption() + "\n" + safe + "\n" + c.getGuarantee();

			IGaService gaService = Graphiti.getGaService();
			Text text = gaService.createPlainText(null, toolTip);
//
//			TextStyleRegion textStyleRegion = gaService.createTextStyleRegion(text, 0, assume.length());
//			gaService.createTextStyle(textStyleRegion, true, false, UnderlineStyle.UNDERLINE_SINGLE);
//
//			TextStyleRegion textStyleRegion2 = gaService.createTextStyleRegion(text,
//					assume.length() + c.getAssumption().length() + 2,
//					assume.length() + c.getAssumption().length() + 2 + safe.length());
//			gaService.createTextStyle(textStyleRegion2, true, false, UnderlineStyle.UNDERLINE_SINGLE);
//
//			TextStyle textStyle1 = gaService.createTextStyle(textStyleRegion);
//			TextStyle textStyle2 = gaService.createTextStyle(textStyleRegion2);
//
//			Font font = gaService.manageFont(getDiagramTypeProvider().getDiagram(), "Verdana", 9, true, false);
//
//			textStyle1.setFont(font);
//			textStyle2.setFont(font);

			return text;
		}
		return super.getToolTip(graphicsAlgorithm);
	}
	
	@Override
	public IDecorator[] getDecorators(PictogramElement pe) {
		Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if (bo instanceof Node) {
			Node node = (Node)bo;
			
//			//Validator validator = new Validator(diagramTypeProvider);
//			
//			IImageDecorator imageRenderingDecorator = null;
//
//			if(...){ // There is a proof to import
//
//				imageRenderingDecorator = new ImageDecorator(IPlatformImageConstants.IMG_ECLIPSE_INFORMATION_TSK);
//			}
			
		}
		return super.getDecorators(pe);
	}

}
