package de.tubs.skeditor.examples.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;

import de.tubs.skeditor.examples.ExamplePlugin;

public class SimpleStructureProvider implements IImportStructureProvider  {
	private final String project;

	public SimpleStructureProvider(String project) {
		super();
		this.project = project;
	}

	@Override
	public List<?> getChildren(Object element) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public InputStream getContents(Object element) {
		final IPath p = new Path((String) element);
		try {
			return new URL("platform:/plugin/de.tubs.skeditor.examples/skeditor_examples/" + project + "/" + p.toString()).openConnection()
					.getInputStream();
		} catch (final IOException e) {
			ExamplePlugin.getDefault().logError(e);
		}
		return null;
	}

	@Override
	public String getFullPath(Object element) {
		return (String) element;
	}

	@Override
	public String getLabel(Object element) {
		final IPath path = new Path((String) element);
		return path.lastSegment();
	}

	@Override
	public boolean isFolder(Object element) {
		return false;
	}

}
