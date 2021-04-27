package de.tubs.skeditor.ui.handler;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorFactory;

import com.google.inject.Inject;

import de.tubs.skeditor.ui.views.SkillDescriptionLanguageEditedResourceProvider;
import de.tubs.skeditor.ui.views.ViewerConfiguration;

@SuppressWarnings("restriction")
public class SkillDescriptionLanguageDialog extends TitleAreaDialog {
	private String content;

	@Inject
	SkillDescriptionLanguageEditedResourceProvider editedResourceProvider;

	@Inject
	EmbeddedEditorFactory embeddedEditorFactory;
	
	@Inject
	ViewerConfiguration configuration;

	private EmbeddedEditor embeddedEditor;

	private IProject project;
	
	public SkillDescriptionLanguageDialog(Shell parentShell, String content, IProject project) {
		super(parentShell);
		this.content = content;
		this.project = project;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		editedResourceProvider.configureProject(project);
		Composite composite = (Composite) super.createDialogArea(parent);
		embeddedEditor = embeddedEditorFactory.newEditor(editedResourceProvider).showErrorAndWarningAnnotations().withParent(composite);
		embeddedEditor.createPartialEditor();
		configuration.getHighlightingHelper().install(embeddedEditor.getConfiguration(), embeddedEditor.getViewer());
		embeddedEditor.getViewer().getTextWidget().setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));
		
		LineNumberRulerColumn lineNumberRulerColumn = new LineNumberRulerColumn();
		embeddedEditor.getViewer().addVerticalRulerColumn(lineNumberRulerColumn); 
		embeddedEditor.getDocument().set(content);
		composite.pack();
		return composite;
	}
	
	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		setTitle("Skill Description");
		setMessage("Detailed description of this skill", IMessageProvider.INFORMATION);
		return control;
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}
	
    private void saveInput() {
        content = embeddedEditor.getDocument().get();
    }
    
    @Override
    protected void okPressed() {
        saveInput();
        super.okPressed();
    }
    
    public String get() {
        return content;
    }
}
