package de.tubs.skeditor.examples.wizards;

import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

import de.tubs.skeditor.examples.utils.ProjectRecord;

public class ExampleNewWizardPage extends WizardPage {
	protected static final Color gray = new Color(null, 140, 140, 140);
	protected static final Color black = new Color(null, 0, 0, 0);

	private final ICheckStateListener checkStateList = new MyCheckStateListener();
	private final SelectionChangedListener selChangeList = new SelectionChangedListener();

	private ContainerTreeViewWrapper wrapper;
	private Text descBox;

	private class MyCheckStateListener implements ICheckStateListener {

		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			if (event instanceof ContainerTreeViewWrapper.ParentCheckStateChangedEvent) {
				CheckboxTreeViewer viewer = null;
				if (event.getSource() instanceof CheckboxTreeViewer) {
					viewer = (CheckboxTreeViewer) event.getSource();
				}

				if (event.getElement() instanceof ProjectRecord.TreeItem) {
					final ProjectRecord.TreeItem item = (ProjectRecord.TreeItem) event.getElement();
					if (viewer != null) {
						if (!viewer.getChecked(event.getElement()) || item.getRecord().hasWarnings()) {
							wrapper.setGrayed(item, true);
							wrapper.setChecked(item, false);
						}
					}
				}
			}
			if (event.getElement() instanceof ProjectRecord.TreeItem) {
				final ProjectRecord.TreeItem item = (ProjectRecord.TreeItem) event.getElement();
				if (item.getRecord().hasErrors()) {
					wrapper.setChecked(item, false);
				} else {
					if (event instanceof ContainerTreeViewWrapper.ParentCheckStateChangedEvent) {
						final ContainerTreeViewWrapper.ParentCheckStateChangedEvent newName = (ContainerTreeViewWrapper.ParentCheckStateChangedEvent) event;
						if (newName.isOnlyRefresh()) {
							wrapper.setChecked(item, event.getChecked());
						}
					} else {
						wrapper.setChecked(item, event.getChecked());
					}
				}
			}
			determineAndSetPageComplete();
		}
	}

	private class SelectionChangedListener implements ISelectionChangedListener {

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			CheckboxTreeViewer viewer = null;
			ITreeContentProvider contProv = null;
			if (event.getSource() instanceof CheckboxTreeViewer) {
				viewer = (CheckboxTreeViewer) event.getSource();
			}
			if ((viewer != null) && (viewer.getContentProvider() instanceof ITreeContentProvider)) {
				contProv = (ITreeContentProvider) viewer.getContentProvider();
			}

			if (event.getSelection() instanceof IStructuredSelection) {
				final IStructuredSelection iss = (IStructuredSelection) event.getSelection();

				if (iss != null) {
					final Object selectedElement = iss.getFirstElement();
					if (selectedElement instanceof ProjectRecord.TreeItem) {
						final ProjectRecord.TreeItem treeItem = (ProjectRecord.TreeItem) selectedElement;
						final ProjectRecord tmpRecord = treeItem.getRecord();
						if (tmpRecord != null) {
							descBox.setText(tmpRecord.getDescription());
							if (tmpRecord.hasErrors()) {
								setMessage(tmpRecord.getErrorText(), ERROR);
							} else if (tmpRecord.hasWarnings()) {
								setMessage(tmpRecord.getWarningText(), WARNING);
							} else {
								setMessage("");
							}
						}
					} else if (selectedElement instanceof IPath) {
						final Object[] checkedProjectItems = wrapper.getCheckedProjectItems(wrapper.getSelectedViewer());
						setMessage("");
						for (final Object object : checkedProjectItems) {
							final ProjectRecord.TreeItem item = (ProjectRecord.TreeItem) object;
							if (item.getRecord().hasWarnings()) {
								Object parent = contProv.getParent(item);
								while (parent != null) {
									if (parent.equals(selectedElement)) {
										setMessage("Projects with warnings are selected!", WARNING);
										break;
									}
									parent = contProv.getParent(parent);
								}
							}
							if (!getMessage().isEmpty()) {
								break;
							}
						}
					}
				}
			}
		}
	}

	private class DynamicComposite extends Composite {

		public DynamicComposite(Composite parent, int style, String contentProviderName) {
			super(parent, style);
			final GridLayout layout = new GridLayout();
			setLayout(layout);
			setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));

			final ContainerCheckedTreeViewer contCheckTreeV = wrapper.getNewContainerViewer(this, SWT.BORDER);
			final GridData listData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
			contCheckTreeV.getControl().setLayoutData(listData);

			final IContentProvider contProv = new DynamicContentProvider(contentProviderName);
			contCheckTreeV.setContentProvider(contProv);
			contCheckTreeV.setLabelProvider(new ExampleLabelProvider());
			contCheckTreeV.addCheckStateListener(checkStateList);

			contCheckTreeV.addSelectionChangedListener(selChangeList);

			contCheckTreeV.setInput(ExampleNewWizardPage.this);
			setPageComplete(false);
		}
	}

	/**
	 * Constructor for SampleNewWizardPage.
	 *
	 * @param pageName
	 */
	public ExampleNewWizardPage() {
		super("");
		setTitle("Select example(s) which you would like to explore");
	}

	@Override
	public void createControl(Composite parent) {
		ProjectProvider.resetProjectItems();
		initializeDialogUnits(parent);
		wrapper = new ContainerTreeViewWrapper();

		final Composite workArea = new Composite(parent, SWT.NONE);
		setControl(workArea);

		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.verticalSpacing = 4;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		gridData.verticalAlignment = SWT.TOP;
		gridLayout = new GridLayout(3, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginLeft = 0;
		final Composite compositeTop = new Composite(workArea, SWT.NONE);
		compositeTop.setLayout(gridLayout);
		compositeTop.setLayoutData(gridData);


		gridData = new GridData();
		gridData.horizontalAlignment = SWT.RIGHT;
		gridData.verticalAlignment = SWT.CENTER;
		gridData.grabExcessHorizontalSpace = false;

		createProjectSelectionArea(workArea);
		createDescriptionArea(workArea);

		workArea.setLayout(new GridLayout());
		workArea.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

		Dialog.applyDialogFont(workArea);
	}

	/**
	 * Create the checkbox list for the found projects.
	 *
	 * @param workArea
	 */
	private void createProjectSelectionArea(final Composite workArea) {
		final CTabFolder tabFolder = new CTabFolder(workArea, SWT.BORDER);
		tabFolder.addListener(SWT.MouseExit, new Listener() {

			@Override
			public void handleEvent(Event event) {
				final Object[] checkedProjects = wrapper.getCheckedProjects();
				boolean warningsExists = false;
				for (final Object object : checkedProjects) {
					final ProjectRecord rec = (ProjectRecord) object;
					if (rec.hasErrors() || rec.hasWarnings()) {
						setMessage("", IMessageProvider.WARNING);
						warningsExists = true;
						break;
					}
				}
				if (!warningsExists) {
					setMessage("");
				}
			}
		});
		tabFolder.setSimple(false);
		final GridLayout gridLayout = new GridLayout();

		tabFolder.setLayout(gridLayout);
		tabFolder.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));

		tabFolder.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.getSource() instanceof CTabFolder) {
					final CTabFolder tabFolder = (CTabFolder) e.getSource();
					final CTabItem selection = tabFolder.getSelection();
					final Control contr = selection.getControl();
					wrapper.setSelectedViewer(contr);
					final Object[] checkedProjects = wrapper.getCheckedProjectItems(wrapper.getSelectedViewer());
					wrapper.refreshCheckOfSelectedViewer(checkedProjects);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		final Set<String> tabItems = ProjectProvider.getViewersNamesForProjects();
		CTabItem item = null;
		for (final String name : tabItems) {
			item = new CTabItem(tabFolder, workArea.getStyle());
			item.setText(name);
			item.setControl(new DynamicComposite(tabFolder, SWT.MULTI, name));
			if (name.equals("General")) {
				tabFolder.setSelection(item);
				wrapper.setSelectedViewer(item.getControl());
			}
		}

		//wrapper.addFilter(errorFilter);
	}

	private void createDescriptionArea(Composite workArea) {
		final Label title = new Label(workArea, SWT.NONE);
		title.setText("Description:");

		descBox = new Text(workArea, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.READ_ONLY | SWT.V_SCROLL);
		descBox.setText("");

		final GridData dbDG = new GridData(GridData.FILL_BOTH);
		dbDG.minimumHeight = 75;
		descBox.setLayoutData(dbDG);
	}

	private void determineAndSetPageComplete() {
		setPageComplete(wrapper.isProjectSelected());
	}

	private void selectAllElementsWithoutWarningsAndErrors() {
		final Object[] allProjectItems = wrapper.getAllProjectItems(wrapper.getSelectedViewer());
		for (final Object object : allProjectItems) {
			if (object instanceof ProjectRecord.TreeItem) {
				final ProjectRecord.TreeItem treeItem = (ProjectRecord.TreeItem) object;
				if (!(treeItem.getRecord().hasErrors() || treeItem.getRecord().hasWarnings())) {
					wrapper.setChecked(treeItem, true);
				}
			}
		}
	}

	private void deselectAllProjects() {
		final Object[] checkedProjectItems = wrapper.getCheckedProjectItems(wrapper.getSelectedViewer());
		for (final Object object : checkedProjectItems) {
			wrapper.setChecked(object, false);
		}
	}

	public Object[] getCheckedProjects() {
		return wrapper.getCheckedProjects();
	}

}
