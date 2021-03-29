package de.tubs.skeditor.job;

import org.eclipse.core.runtime.IProgressMonitor;

public interface ILongRunningMethod<T> {
	T execute(IProgressMonitor monitor) throws Exception;
}
