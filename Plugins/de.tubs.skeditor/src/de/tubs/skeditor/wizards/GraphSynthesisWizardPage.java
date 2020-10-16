package de.tubs.skeditor.wizards;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import de.tubs.skeditor.synthesis.Requirement;
import de.tubs.skeditor.synthesis.prover.TermProver;
import de.tubs.skeditor.synthesis.search.FilterFormatException;
import de.tubs.skeditor.utils.SynthesisUtil;

/**
 * wizard page for GraphSynthesisWizard
 * 
 * @author Christopher Göbel
 *
 */
public class GraphSynthesisWizardPage extends WizardPage {

	//error messages for this page
	private final String ERR_EMPTY = "New requirement cannot be empty!";
	private final String ERR_REQUIREMENTS = "You must at least specify one requirement!";
	private final String ERR_ALREADY_DEFINED = "Requirement already defined!";
	private final String ERR_EMPTY_REPO = "Repository cannot be empty!";
	private final String ERR_BAD_REQ = "Bad format for requirement!";
	private final String ERR_UNSATISFIABLE = "Requirement conflicts with other requirement!";
	
	//attributes
	private Text requirementText;
	private Text repoText;
	private TableViewer viewer;
	private String description;
	
	private List<Requirement> requirements;
	private TermProver prover; //to prove satisfiability of requirements

	/**
	 * Constructor for GraphSynthesisWizardPage.
	 * 
	 */
	public GraphSynthesisWizardPage() {
		super("wizardPage");
		setTitle("Skill Graph Synthesis");
		description = "This wizard synthesizes a new skill graph.";
		setDescription(description);
		requirements = new ArrayList<>();
		prover = new TermProver();
	}
	
	@Override
	public void createControl(Composite parent) {
		//define Container and layout data
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		container.setLayout(layout);
		
		//label for repo textfield
		Label repoLabel = new Label(container, SWT.NONE);
		repoLabel.setText("&Repository:");

		//textfield for repository
		repoText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData repoTextData = new GridData(GridData.FILL_HORIZONTAL);
		repoText.setLayoutData(repoTextData);
		repoText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if(checkPage()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}
			
		});						
		//browse button 
		Button browseButton = new Button(container, SWT.PUSH);
		browseButton.setText("Browse...");
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
				
		//label for textfield
		Label label = new Label(container, SWT.NONE);
		label.setText("&New Requirement:");

		//textfield for new requirement
		requirementText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		requirementText.setLayoutData(gd);
				
		//add button 
		Button addButton = new Button(container, SWT.PUSH);
		addButton.setText("Add");
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleAdd();
			}
		});
		
		//composite for tableviewer
		Composite tableComp = new Composite(container, SWT.NONE);
		GridData gdc = new GridData();
		gdc.verticalAlignment = GridData.FILL;
		gdc.horizontalSpan = 3;
		gdc.grabExcessHorizontalSpace = true;
		gdc.grabExcessVerticalSpace = true;
		gdc.horizontalAlignment = GridData.FILL;
		
		//layout for tablecomp
		TableColumnLayout colLayout = new TableColumnLayout();
		tableComp.setLayoutData(gdc);
		tableComp.setLayout(colLayout);
		
		//define tableviewer for requirements
		viewer = new TableViewer(tableComp, SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
        viewer.setContentProvider(new ArrayContentProvider());
        viewer.getTable().setHeaderVisible(true);
        viewer.getTable().setLinesVisible(true);
        
        //define column for tableviewer
        TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
        viewerColumn.getColumn().setText("Requirements:");
        viewerColumn.getColumn().setResizable(true);
        viewerColumn.getColumn().setMoveable(true);
        viewerColumn.setEditingSupport(new GraphSynthesisEditingSupport(viewer));
        colLayout.setColumnData(viewerColumn.getColumn(), new ColumnWeightData(100));
        //viewer.getTable().setLayout(tableColumnLayout);
        viewerColumn.setLabelProvider(new ColumnLabelProvider() {
        	@Override
            public String getText(Object element) {
                Requirement req = (Requirement) element;
                return req.getFormula();
            }
        });
        
        //delete button
		Button deleteButton = new Button(container, SWT.PUSH);
		deleteButton.setText("Delete");
		deleteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleDelete();
			}
		});

		//requirements.add(new Requirement("Test"));
		//requirements.add(new Requirement("Test2"));
		updateStatus(ERR_REQUIREMENTS);
		setPageComplete(false);
		setControl(container);
	}
	
	/*
	 * handles clicks on add button
	 */
	private void handleAdd() {
		String requirement = requirementText.getText();
		Requirement req = new Requirement(requirement);
		if(requirements.contains(req)) {
			updateStatus(ERR_ALREADY_DEFINED);
			return;
		} else {
			if(SynthesisUtil.isValidRequirement(requirement)) {
				//check satisfiability of requirements
				String[] requirementStrings = new String[requirements.size()+1];
				int i;
				for(i = 0; i < requirements.size(); i++) {
					requirementStrings[i] = requirements.get(i).getFormula();
				}
				requirementStrings[i] = requirement;
				System.out.println(requirement);
				if(prover.check(requirementStrings)) {
					//everythings fine, add requirement
					requirements.add(req);
					/*for(String var : req.getVariables()) {
						System.out.println("var: "+var);
					}*/
				} else {
					updateStatus(ERR_UNSATISFIABLE);
					return;
				}
				
				
			} else {
				updateStatus(ERR_BAD_REQ);
				return;
			}
		}
		
		if(checkPage()) {
			setPageComplete(true);
		}
		updateStatus(null);
	}
	
	private void printReqs() {
		for(Requirement req : requirements) {
			System.out.println(req.getFormula());
		}
	}
	
	/*
	 * handles clicks on delete button
	 */
	private void handleDelete() {
		IStructuredSelection iselection = viewer.getStructuredSelection();
		if(!iselection.isEmpty()) {
			printReqs();
			for(Object obj : iselection.toList()) {
				Requirement req = (Requirement) obj;
				System.out.println("Formel:"+req.getFormula()+" ,hash: "+req.hashCode());
				requirements.remove(req);
				System.out.println("Sind enthalten :"+requirements.contains(req));	
			}
			printReqs();
			System.out.println("reqs nach löschen");
			if(!checkPage()) {
				setPageComplete(false);
			} 
			System.out.println("after deletion");
		} else {
			System.out.println("selection ist leer");
		}
	}
	
	/*
	 * handles clicks on browse button
	 */
	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(),
				ResourcesPlugin.getWorkspace().getRoot(), false, "Select repository");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				repoText.setText(((Path) result[0]).toString());
			}
		}
	}
	
	/*
	 * update status of this page
	 */
	private void updateStatus(String message) {
		viewer.setInput(requirements.toArray());
		setErrorMessage(message);
		viewer.refresh();
	}
	
	private boolean checkPage() {
		if(!requirements.isEmpty()) {
			System.out.println("requirements nicht leer");
			if(repoText.getText() != null && repoText.getText().length() > 0) {
				updateStatus(null);
				return true;
			} else {
				updateStatus(ERR_EMPTY_REPO);
			}
		} else {
			System.out.println("requirements leer");
			updateStatus(ERR_REQUIREMENTS);
		}
		
		return false;
	}
	
	/*
	 * checks if the given string is a valid requirement
	 */
	private boolean isValidRequirement(String requirement) {
		
		String str = requirement;
		str.replace(" ", ""); //remove white spaces from string
		
		//check if requirement is "include SKILL" requirement
		if(str.startsWith("include")) {
			str = str.replace("include", "");
			if(str.startsWith("not")) {
				str = str.replace("not", "");
			}
			if(!str.matches("([A-Za-z_])([A-Za-z0-9_]*)")) { //no valid skill name given
				return false;
			} else {
				return true;
			}
		}
		
		//split requirement at comparism operator, only one comparism allowed per requirement
		int numOperators = 0;
		char c;
		String operator = "";
		for(int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c == '<' || c == '>') {
				if(str.charAt(i+1) == '=') { // <= or >=
					operator = String.format("%c=", c);
					i++;
				} else {
					operator = String.format("%c", c);
				}
				numOperators++;
			} else if (c == '=') {
				numOperators++;
				operator = "=";
			} 
		}
		if(numOperators != 1) { //only 1 operator allowed
			return false;
		}
		String[] operands = str.split(operator); 
		if(operands.length != 2) {
			return false;
		}
		/*for(String o : operands) {
			System.out.println(o);
		}*/
		Requirement req = new Requirement(requirement);
		return checkOperand(operands[0]) & checkOperand(operands[1]) & !(req.getVariables().isEmpty());
	}
	
	/*
	 * checks if the given operand of a requirement is valid.
	 * this method is a helper function for isValidRequirement()
	 */
	private boolean checkOperand(String operand) {
		int i; 
		boolean result = true;
		String rest = "";
		Deque<Character> stack = new ArrayDeque<>(); //for finding matching parenthesis in filter
		if(operand.startsWith("(")) { //if filter starts with parenthesis, find matching closing parenthesis
			i = 0;
			do {
				if(operand.charAt(i) == '(') {
					stack.push('(');
				} else if(operand.charAt(i) == ')') {
					stack.pop();
				}
				i++;
			} while(!stack.isEmpty() && i < operand.length());
			if(!stack.isEmpty()) { // unbalanced parantheses
				return false;
			}
			rest = operand.substring(i);
			result &= checkOperand(operand.substring(1, i-1));
		} else {
			String toCheck = operand;
			String part1 = "", part2 = "";
			i = 0;
			System.out.println(operand);
			while(i < operand.length()) {
				if(operand.charAt(i) == '+' || operand.charAt(i) == '-' || operand.charAt(i) == '*' || operand.charAt(i) == '/') {
					part1 = operand.substring(0, i);
					part2 = operand.substring(i);
					System.out.println(part1+ " und "+part2);
					break;
				}
				i++;
			}
			if (part1.length() == 0 && part2.length() == 0) {
				toCheck = operand;
			} else {
				if(part1.length() == 0 || part2.length() == 0) { //operand is a operation itself, so operands of operand cannot be empty
					return false;
				} else {
					toCheck = part1;
					rest = part2;
				}
			} 
			if (!toCheck.matches("([A-Za-z_])([A-Za-z0-9_]*)")) { //check if String is valid variable
				System.out.println(toCheck+ " ist keine variable");
				if(!isNumeric(toCheck)) { //check if string is number
					System.out.println(toCheck+ " ist keine zahl");
					return false;
				}
			}
		}
		if(rest.length() > 0) {
			if(!(rest.charAt(0) == '+' || rest.charAt(0) == '-' || rest.charAt(0) == '*' || rest.charAt(0) == '/')) {
				return false;
			}
			result &= checkOperand(rest.substring(1));
		}
		return result;
	}
	
	/*
	 * checks if the given string is numeric
	 */
	private boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
	/**
	 * Returns requirements specified on this page
	 * @return specified requirements
	 */
	public List<Requirement> getRequirements() {
		return requirements;
	}
	
	/**
	 * Returns name of repository
	 * @return repository name
	 */
	public String getRepositoryName() {
		return repoText.getText();
	}

}
