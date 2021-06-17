package de.tubs.skeditor.preferences;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.prefs.Preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Group of the widgets to select a prover and specify their paths
 * 
 * @author Dibo Gonda
 *
 */
public class SelectProverGroup extends Group {

	private List<Button> radioButtons;
	private Map<Button, Text> textAreasMap;
	private Map<Button, Button> browseButtonsMap;

	public SelectProverGroup(Composite parent, int style) {
		super(parent, style);
		setText("Select KeymaeraX prover (.exe)");
		setLayout(new RowLayout(SWT.VERTICAL));
		createWidgets();
	}

	private void createWidgets() {
		this.radioButtons = new ArrayList<>();
		this.textAreasMap = new HashMap<>();
		this.browseButtonsMap = new HashMap<>();

		RadioButtonAndFileBrowserComposite z3Composite = new RadioButtonAndFileBrowserComposite(this, 0, "z3");
		z3Composite.pack();

		RadioButtonAndFileBrowserComposite mathematicaComposite = new RadioButtonAndFileBrowserComposite(this, 0,
				"mathematica");
		mathematicaComposite.pack();

//		RadioButtonAndFileBrowserComposite secondMathematicaComposite = new RadioButtonAndFileBrowserComposite(this, 0,
//				"");
//		secondMathematicaComposite.pack();

		this.pack();
	}

	private class RadioButtonAndFileBrowserComposite extends Composite {
		public RadioButtonAndFileBrowserComposite(SelectProverGroup parent, int style, String radioButtonName) {
			super(parent, style);
			Preferences preferences = ConfigurationScope.INSTANCE.getNode("de.tubs.skeditor.preferences.page");
			Preferences sub = preferences.node("node");

			setLayout(new RowLayout(SWT.HORIZONTAL));

			RowData buttonLayoutData = new RowData();
			buttonLayoutData.width = 100;
			Button radioButton = null;
			if (radioButtonName.equals("")) {
				Label emptyLabel = new Label(this, SWT.BEGINNING);
				emptyLabel.setLayoutData(buttonLayoutData);
			} else {
				radioButton = new Button(this, SWT.RADIO);
				radioButton.setText(radioButtonName);
				radioButton.setLayoutData(buttonLayoutData);
				radioButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						switch (radioButtonName) {
						case "z3":
							sub.put("selected_radio_button", "z3");
							break;
						case "mathematica":
							sub.put("selected_radio_button", "mathematica");
						}

						if (e.getSource() instanceof Button) {
							Button sourceRadioButoon = (Button) e.getSource();

							parent.getRadioButtons().forEach(button -> {
								if (!button.equals(sourceRadioButoon)) {
									button.setSelection(false);
								}
							});

							parent.getTextAreasMap().forEach((button, text) -> {
								if (button.equals((Button) e.getSource())) {
									text.setEnabled(true);
								} else {
									text.setEnabled(false);
								}
							});

							parent.getBrowseButtonsMap().forEach((radiobutton, browseButton) -> {
								if (radiobutton.equals((Button) e.getSource())) {
									browseButton.setEnabled(true);
								} else {
									browseButton.setEnabled(false);
								}
							});

						}
					}
				});
				radioButton.pack();

				parent.getRadioButtons().add(radioButton);
			}

			Text directoryText = new Text(this, SWT.BORDER);
			RowData textLayoutData = new RowData();
			textLayoutData.width = 200;
			directoryText.setLayoutData(textLayoutData);
			directoryText.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					//System.out.println("TEST " + ((Text) e.widget).getText());
					String input = ((Text) e.widget).getText();
					switch (radioButtonName) {
					case "z3":
						sub.put("z3_exe", input);
						break;
					case "mathematica":
						sub.put("mathematica_exe", input);
						break;
					}
				}

			});

			switch (radioButtonName) {
			case "z3":
				directoryText.setText(sub.get("z3_exe", ""));
				break;
			case "mathematica":
				directoryText.setText(sub.get("mathematica_exe", ""));

			}
			directoryText.pack();

			parent.getTextAreasMap().put(radioButton, directoryText);

			Button browseButton = new Button(this, SWT.PUSH);
			browseButton.setText("Browse...");
			browseButton.addSelectionListener(new FileDialogSelectionListener(this.getShell(), directoryText));
			browseButton.pack();

			parent.getBrowseButtonsMap().put(radioButton, browseButton);

			if (radioButton != null) {
				String selectedRadioButton = sub.get("selected_radio_button", "");
				System.out.println("Button " + selectedRadioButton);
				if (selectedRadioButton.equals("")) {
					sub.put("selected_radio_button", "z3");
				}
				selectedRadioButton = sub.get("selected_radio_button", "");
				if (selectedRadioButton.equals(radioButtonName)) {
					radioButton.setSelection(true);
					parent.getTextAreasMap().get(radioButton).setEnabled(true);
					parent.getBrowseButtonsMap().get(radioButton).setEnabled(true);
				} else {
					parent.getTextAreasMap().get(radioButton).setEnabled(false);
					parent.getTextAreasMap().get(radioButton).setEnabled(false);
				}

			}

		}

	}

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
