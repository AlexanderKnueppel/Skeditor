package de.tubs.skeditor.views.safetygoalsview;

import java.util.List;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import SkillGraph.Assumption;
import SkillGraph.Node;
import SkillGraph.Requirement;
import de.tubs.skeditor.contracting.grammar.GrammarUtil;
import de.tubs.skeditor.contracting.grammar.SyntaxError;

public class LabelProvider implements ITableLabelProvider, ITableFontProvider, ITableColorProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Node) {
			Node node = (Node) element;
			switch (columnIndex) {
			case 0:
				return node.getName();
			default:
				break;
			}
		} else if (element instanceof Requirement) {
			Requirement req = (Requirement) element;
			switch (columnIndex) {
			case 0:
				String name = "RS";
				for (int i = 0; i < req.getNode().getRequirements().size(); i++) {
					if (req.equals(req.getNode().getRequirements().get(i))) {
						name += (i + 1);
					}
				}
				return name;
			case 1:
				return req.getTerm();
			case 2:
				return req.getComment();
			case 3:
				return req.getType().getLiteral();
			default:
				break;
			}
		} else if (element instanceof Assumption) {
			Assumption as = (Assumption) element;
			switch (columnIndex) {
			case 0:
				String name = "AS";
				for (int i = 0; i < as.getNode().getAssumptions().size(); i++) {
					if (as.equals(as.getNode().getAssumptions().get(i))) {
						name += (i + 1);
					}
				}
				return name;
			case 1:
				return as.getTerm();
			case 2:
				return as.getComment();
			case 3:
				return "Assumption";
			default:
				break;
			}
		}

		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		String Term = "";
		if(element instanceof Requirement) 
			Term = ((Requirement)element).getTerm();
		else if(element instanceof Assumption) 
			Term = ((Assumption)element).getTerm();
		
		if(element instanceof Requirement || element instanceof Assumption) {
			switch (columnIndex) {
			case 1:
				List<SyntaxError> errors = GrammarUtil.tryToParse(Term);
				if(errors.isEmpty())
					return null;
				else {
					//TODO add markers fr syntax errors
					return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
				}
			default:break;
			}
		}
		return null;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getFont(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
