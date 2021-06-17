package de.tubs.skeditor.simulation.utils;

import com.google.common.base.Preconditions;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
* A simple file browser dialog
*/
public class FileDialogSelectionListener extends SelectionAdapter {

	private final Shell shell;
	private final Text target;
//	private static final String[] FILTER_NAMES = { "Executable (*.exe)" };
	private final String[] filterExtensions;

	public FileDialogSelectionListener(Shell shell, Text target, String[] filterExtensions) {
		this.shell = Preconditions.checkNotNull(shell);
		this.target = target;
		this.filterExtensions = filterExtensions;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// User has selected to open a single file
		FileDialog dlg = new FileDialog(shell, SWT.OPEN);
		String currentOs = System.getProperty("os.name").toLowerCase();
		if (currentOs.contains("windows")) {
//			dlg.setFilterNames(FILTER_NAMES);
			dlg.setFilterExtensions(filterExtensions);
		}
		String fn = dlg.open();
		if (fn != null) {
			target.setText(fn);
		}
	}

}
