package de.tubs.skeditor.ui;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

import de.tubs.skeditor.ide.SDLSemanticHighlighterCalculator.SDLHighlightingStyles;

public class SDLHighlightingConfiguration extends DefaultHighlightingConfiguration {
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		acceptor.acceptDefaultHighlighting(SDLHighlightingStyles.KEYWORD_BLUE_ID, "KeywordBlue", keywordRedTextStyle());
		super.configure(acceptor);
	}
	
	public TextStyle keywordRedTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(5, 51, 142));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}
}
