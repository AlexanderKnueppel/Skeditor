package de.tubs.skeditor.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Preconditions;

/**
 * 
 * @author Dibo Gonda
 *
 */
public class FileDialogSelectionListener extends SelectionAdapter {

	private final Shell shell;
	private final Text target;
	private static final String[] FILTER_NAMES = { "Executable (*.exe)" };

	private static final String[] FILTER_EXTS = { "*.exe" };

	public FileDialogSelectionListener(Shell shell, Text target, String entity) {
		this.shell = Preconditions.checkNotNull(shell);
		this.target = target;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// User has selected to open a single file
		FileDialog dlg = new FileDialog(shell, SWT.OPEN);
		String currentOs = System.getProperty("os.name").toLowerCase();
		if (currentOs.contains("windows")) {
			dlg.setFilterNames(FILTER_NAMES);
			dlg.setFilterExtensions(FILTER_EXTS);
		}
		String fn = dlg.open();
		if (fn != null) {
			target.setText(fn);
		}
	}

}
