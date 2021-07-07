package de.tubs.skeditor.preferences;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.prefs.Preferences;
import de.tubs.skeditor.simulation.utils.FileBrowserGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Group of the widgets to select a prover and specify their paths
 */
public class ProverSettingsGroup extends Group {

	private Map<PreferenceSettings, String> settingsMap;

	private List<Button> radioButtons;
	private Map<Button, Text> textAreasMap;
	private Map<Button, Button> browseButtonsMap;

	public ProverSettingsGroup(Composite parent, int style) {
		super(parent, style);
		setText("Prover Settings");
		GridLayout gridLayout = new GridLayout(1, false);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setLayout(gridLayout);
		createDropDownGroup();
		createFileBrowserGroup();
	}

	private void createDropDownGroup() {
		IEclipsePreferences preferences = ConfigurationScope.INSTANCE.getNode("de.tubs.skeditor.preferences.page");
		Preferences sub = preferences.node("node");
		List<String> items = new ArrayList<>();
		items.add("z3");
		items.add("Mathematica");

		// Create the group
		Group selectSimulatorGroup = new Group(this, SWT.SHADOW_ETCHED_IN);
		selectSimulatorGroup.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
		selectSimulatorGroup.setLayout(new GridLayout(1, false));
		selectSimulatorGroup.setText("Select prover to use");

		Combo dropDownMenu = new Combo(selectSimulatorGroup, SWT.BORDER);
		dropDownMenu.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Default
		dropDownMenu.setItems(items.toArray(new String[0]));
		dropDownMenu.select(0);

		dropDownMenu.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String selectedProver = "";
				switch (dropDownMenu.getSelectionIndex()) {
				case 0:
					selectedProver = "z3";
					break;
				case 1:
					selectedProver = "Mathematica";
					break;
				}
				sub.put(PreferenceSettings.SELECTED_PROVER.toString(), selectedProver);
				System.out.println(dropDownMenu.getText());

			}
		});
		dropDownMenu.pack();
		selectSimulatorGroup.pack();
	}

	private void createFileBrowserGroup() {
		// Create the group
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 3;
		fillLayout.marginHeight = 3;
		fillLayout.marginWidth = 3;
		Group fileBrowserGroup = new Group(this, SWT.SHADOW_ETCHED_IN);
		fileBrowserGroup.setText("Select prover to use");
		fileBrowserGroup.setLayout(fillLayout);
		fileBrowserGroup.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));

		FileBrowserGroup z3Browser = new FileBrowserGroup(fileBrowserGroup, SWT.NONE, "z3 (.exe) - REQUIRED",
				new String[] { "*.exe" }, null);
		z3Browser.pack();

		FileBrowserGroup mathematicaBrowser = new FileBrowserGroup(fileBrowserGroup, SWT.NONE, "Mathematica (.exe)",
				new String[] { "*.exe" }, null);
		z3Browser.pack();
		mathematicaBrowser.pack();
		this.pack();

	}

//	private void createWidgets() {
//		this.radioButtons = new ArrayList<>();
//		this.textAreasMap = new HashMap<>();
//		this.browseButtonsMap = new HashMap<>();
//
//		RadioButtonAndFileBrowserComposite z3Composite = new RadioButtonAndFileBrowserComposite(this, 0, "z3");
//		z3Composite.pack();
//
//		RadioButtonAndFileBrowserComposite mathematicaComposite = new RadioButtonAndFileBrowserComposite(this, 0,
//				"mathematica");
//		mathematicaComposite.pack();
//
////		RadioButtonAndFileBrowserComposite secondMathematicaComposite = new RadioButtonAndFileBrowserComposite(this, 0,
////				"");
////		secondMathematicaComposite.pack();
//
//		this.pack();
//	}

//	private class RadioButtonAndFileBrowserComposite extends Composite {
//		public RadioButtonAndFileBrowserComposite(ProverSettingsGroup parent, int style, String radioButtonName) {
//			super(parent, style);
//			Preferences preferences = ConfigurationScope.INSTANCE.getNode("de.tubs.skeditor.preferences.page");
//			Preferences sub = preferences.node("node");
//
//			setLayout(new RowLayout(SWT.HORIZONTAL));
//
//			RowData buttonLayoutData = new RowData();
//			buttonLayoutData.width = 100;
//			Button radioButton = null;
//			if (radioButtonName.equals("")) {
//				Label emptyLabel = new Label(this, SWT.BEGINNING);
//				emptyLabel.setLayoutData(buttonLayoutData);
//			} else {
//				radioButton = new Button(this, SWT.RADIO);
//				radioButton.setText(radioButtonName);
//				radioButton.setLayoutData(buttonLayoutData);
//				radioButton.addSelectionListener(new SelectionAdapter() {
//					@Override
//					public void widgetSelected(SelectionEvent e) {
//						switch (radioButtonName) {
//						case "z3":
//							sub.put("selected_radio_button", "z3");
//							break;
//						case "mathematica":
//							sub.put("selected_radio_button", "mathematica");
//						}
//
//						if (e.getSource() instanceof Button) {
//							Button sourceRadioButoon = (Button) e.getSource();
//
//							parent.getRadioButtons().forEach(button -> {
//								if (!button.equals(sourceRadioButoon)) {
//									button.setSelection(false);
//								}
//							});
//
//							parent.getTextAreasMap().forEach((button, text) -> {
//								if (button.equals((Button) e.getSource())) {
//									text.setEnabled(true);
//								} else {
//									text.setEnabled(false);
//								}
//							});
//
//							parent.getBrowseButtonsMap().forEach((radiobutton, browseButton) -> {
//								if (radiobutton.equals((Button) e.getSource())) {
//									browseButton.setEnabled(true);
//								} else {
//									browseButton.setEnabled(false);
//								}
//							});
//
//						}
//					}
//				});
//				radioButton.pack();
//
//				parent.getRadioButtons().add(radioButton);
//			}
//
//			Text directoryText = new Text(this, SWT.BORDER);
//			RowData textLayoutData = new RowData();
//			textLayoutData.width = 200;
//			directoryText.setLayoutData(textLayoutData);
//			directoryText.addModifyListener(new ModifyListener() {
//				@Override
//				public void modifyText(ModifyEvent e) {
//					// System.out.println("TEST " + ((Text) e.widget).getText());
//					String input = ((Text) e.widget).getText();
//					switch (radioButtonName) {
//					case "z3":
//						sub.put("z3_exe", input);
//						break;
//					case "mathematica":
//						sub.put("mathematica_exe", input);
//						break;
//					}
//				}
//
//			});
//
//			switch (radioButtonName) {
//			case "z3":
//				directoryText.setText(sub.get("z3_exe", ""));
//				break;
//			case "mathematica":
//				directoryText.setText(sub.get("mathematica_exe", ""));
//
//			}
//			directoryText.pack();
//
//			parent.getTextAreasMap().put(radioButton, directoryText);
//
//			Button browseButton = new Button(this, SWT.PUSH);
//			browseButton.setText("Browse...");
//			browseButton.addSelectionListener(new FileDialogSelectionListener(this.getShell(), directoryText));
//			browseButton.pack();
//
//			parent.getBrowseButtonsMap().put(radioButton, browseButton);
//
//			if (radioButton != null) {
//				String selectedRadioButton = sub.get("selected_radio_button", "");
//				System.out.println("Button " + selectedRadioButton);
//				if (selectedRadioButton.equals("")) {
//					sub.put("selected_radio_button", "z3");
//				}
//				selectedRadioButton = sub.get("selected_radio_button", "");
//				if (selectedRadioButton.equals(radioButtonName)) {
//					radioButton.setSelection(true);
//					parent.getTextAreasMap().get(radioButton).setEnabled(true);
//					parent.getBrowseButtonsMap().get(radioButton).setEnabled(true);
//				} else {
//					parent.getTextAreasMap().get(radioButton).setEnabled(false);
//					parent.getTextAreasMap().get(radioButton).setEnabled(false);
//				}
//
//			}
//
//		}
//
//	}

	public List<Button> getRadioButtons() {
		return this.radioButtons;
	}

	public Map<Button, Text> getTextAreasMap() {
		return this.textAreasMap;
	}

	public Map<Button, Button> getBrowseButtonsMap() {
		return this.browseButtonsMap;
	}

	@Override
	protected void checkSubclass() {
		// allow subclass
	}

}
