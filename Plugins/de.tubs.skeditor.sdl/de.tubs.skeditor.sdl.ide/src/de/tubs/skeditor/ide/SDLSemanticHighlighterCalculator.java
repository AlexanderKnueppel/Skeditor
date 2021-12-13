package de.tubs.skeditor.ide;

import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ide.editor.syntaxcoloring.HighlightingStyles;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;

import de.tubs.skeditor.services.SdlGrammarAccess;
import de.tubs.skeditor.sdl.SDLModel;

public class SDLSemanticHighlighterCalculator extends DefaultSemanticHighlightingCalculator {
	public static class SDLHighlightingStyles implements HighlightingStyles {
		public static String KEYWORD_BLUE_ID = KEYWORD_ID + "Blue";
	}

	@Inject
	SdlGrammarAccess ga;

	@Override
	protected boolean highlightElement(EObject object, IHighlightedPositionAcceptor acceptor,
			CancelIndicator cancelIndicator) {
		if (object instanceof SDLModel) {
			for (ILeafNode n : NodeModelUtils.findActualNodeFor(object).getLeafNodes()) {
				if (ga.getStatementAccess().findKeywords(n.getGrammarElement().toString()).size() > 0) {
					acceptor.addPosition(n.getOffset(), n.getLength(), SDLHighlightingStyles.KEYWORD_BLUE_ID);
				}
			}
		}
		return super.highlightElement(object, acceptor, cancelIndicator);
	}
}
