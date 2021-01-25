package de.tubs.skeditor.ui.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class SkillDescriptionLanguageEditedResourceProvider implements IEditedResourceProvider {
	
	@Inject IResourceSetProvider rsp;
	private IProject project;

	@Override
	public XtextResource createResource() {
		return (XtextResource) rsp.get(project).createResource(URI.createURI("platform:/resource/"+project.getName()+"/src/___test.sd"));
	}

	public void configureProject(IProject project) {
		this.project = project;
	}

}