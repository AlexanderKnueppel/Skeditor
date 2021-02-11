package de.tubs.skeditor.ui.handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.util.JavaProjectFactory;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

import de.tubs.skeditor.SdlStandaloneSetup;
import de.tubs.skeditor.sdl.SDLModel;
import de.tubs.skeditor.sdl.ui.internal.SdlActivator;

public class SkillDescriptionLanguageHandler {


//	@Inject
//	private Provider<XbaseInterpreter> interpreterProvider;

//	@Inject
//	JavaProjectFactory javaProjectFactory;
//
//	@Inject
//	IQualifiedNameConverter qualifiedNameConverter;

//	@Inject
//	ISerializer serializer;

//	@Inject
	Injector injector;
//
//	@Inject
//	IWorkbench workbench;
	
	public static SDLModel textToModel(String text) throws IOException {
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		IResource res = (IResource) activeEditor.getEditorInput().getAdapter(IResource.class);
		IProject project = res == null ? null : res.getProject();
		
		Injector injector = new SdlStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);

		InputStream in = new ByteArrayInputStream(text.getBytes());
		Resource resource = resourceSet.createResource(URI.createURI("dummy:/inmemory.sd"));
		//System.out.println(text);
        resource.load(in, resourceSet.getLoadOptions());
		return (SDLModel) resource.getContents().get(0);
	}

	public String openDialog(String content) throws IOException {
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		IResource res = (IResource) activeEditor.getEditorInput().getAdapter(IResource.class);
		IProject project = res == null ? null : res.getProject();

		if (project != null) {
			SkillDescriptionLanguageDialog dialog = new SkillDescriptionLanguageDialog(
					Display.getCurrent().getActiveShell(), content, project);
			
			SdlActivator activator = SdlActivator.getInstance();
			injector =  activator != null ? activator.getInjector(SdlActivator.DE_TUBS_SKEDITOR_SDL) : null;
			injector.injectMembers(dialog);
			
			int value = dialog.open();

			if (value == Window.OK) {
				return dialog.get();
			}
		}

		return null;
	}

}
