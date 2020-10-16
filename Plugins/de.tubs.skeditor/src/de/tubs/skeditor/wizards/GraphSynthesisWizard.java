package de.tubs.skeditor.wizards;

import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import de.tubs.skeditor.synthesis.Requirement;
import de.tubs.skeditor.synthesis.SynthesisOperation;
import de.tubs.skeditor.views.SafetyGoalsView;

public class GraphSynthesisWizard extends Wizard implements INewWizard {

	private GraphSynthesisWizardPage requirementPage;
	private NewGraphWizardPage newGraphPage;
	private ISelection selection;
	private List<Requirement> unsatisfiables;
	
	public GraphSynthesisWizard() {
		super();
		setNeedsProgressMonitor(true);
		unsatisfiables = new ArrayList<>();
	}
	
	/**
	 * Adding the page to the wizard.
	 */
	@Override
	public void addPages() {
		requirementPage = new GraphSynthesisWizardPage();
		newGraphPage = new NewGraphWizardPage(selection);
		addPage(requirementPage);
		addPage(newGraphPage);
	}

	@Override
	public boolean performFinish() {
		final String containerName = newGraphPage.getContainerName();
		final List<Requirement> requirements = requirementPage.getRequirements();
		final String filename = newGraphPage.getFileName();
		final String repoName = requirementPage.getRepositoryName();
		//final String GraphBName = page.getGrapBName();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, filename, requirements, repoName, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		if(unsatisfiables.size() > 0) {
			System.out.println("unsatisfiables gefunden");
			String warnMsg = "The following requirements cannot be satisfied:";
			for(Requirement unsatisfiable : unsatisfiables) {
				warnMsg += "\n"+unsatisfiable.getFormula();
			}
			MessageDialog.openWarning(getShell(), "Warning", warnMsg);
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the file if missing or
	 * just replace its contents, and open the editor on the newly created file.
	 */

	private void doFinish(String containerName, String filename, List<Requirement> requirements, String repoName, IProgressMonitor monitor)
			throws CoreException {
		// create a sample file
		monitor.beginTask("Creating " + filename, 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(filename));

		ResourceSet rSet = new ResourceSetImpl();

		URI uri = URI.createFileURI(file.getFullPath().toString());

		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(rSet);
		if (editingDomain == null) {
			// Not yet existing, create one
			editingDomain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(rSet);
		}

		SynthesisOperation operation = new SynthesisOperation(editingDomain, containerName, filename, repoName, requirements);
		editingDomain.getCommandStack().execute(operation);
		unsatisfiables = operation.getUnsatisfiableRequirements(); //save unsatisfiable requirements
		
		
		// Dispose the editing domain to eliminate memory leak
		editingDomain.dispose();

		monitor.worked(1);
		monitor.setTaskName("Opening file for editing...");

	}

	/**
	 * We will initialize file contents with a sample text.
	 */

	private InputStream openContentStream() {
		String contents = "This is the initial file contents for *.sked files that should be word-sorted in the Preview page of the multi-page editor";
		return new ByteArrayInputStream(contents.getBytes());
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "ContractModelling", IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

}
