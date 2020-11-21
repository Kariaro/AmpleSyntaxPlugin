package plugin.hardcoded.ample.console;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.*;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.ui.console.IConsoleDocumentPartitioner;
import org.eclipse.ui.progress.UIJob;

import hardcoded.vm.AmpleBufferStream;

public class AmpleConsolePartitioner extends AmpleBufferStream implements IConsoleDocumentPartitioner {
	@SuppressWarnings("unused")
	private AmpleConsole console;
	private IDocument document;
	private AmpleUIJob queueJob;
	
	// TODO: Add a remove this console button
	
	public AmpleConsolePartitioner(AmpleConsole console) {
		this.console = console;
		this.queueJob = new AmpleUIJob();
		this.queueJob.setRule(console.getSchedulingRule());
	}
	
	public void connect(IDocument document) {
		this.document = document;
		document.setDocumentPartitioner(this);
	}
	
	public void disconnect() {
		this.document = null;
	}
	
	public void documentAboutToBeChanged(DocumentEvent event) {
	}
	
	public boolean documentChanged(DocumentEvent event) {
		return false;
	}
	
	public String[] getLegalContentTypes() {
		return new String[0];
	}
	
	public String getContentType(int offset) {
		return null;
	}
	
	public ITypedRegion[] computePartitioning(int offset, int length) {
		return new ITypedRegion[0];
	}
	
	public ITypedRegion getPartition(int offset) {
		return null;
	}
	
	public boolean isReadOnly(int offset) {
		return false;
	}
	
	public StyleRange[] getStyleRanges(int offset, int length) {
		return null;
	}
	
	public synchronized void write(int index, char c) {
		super.write(index, c);
		queueJob.schedule(50);
	}
	
	private class AmpleUIJob extends UIJob {
		public AmpleUIJob() {
			super("AmpleConsole Updater");
			setSystem(true);
			setPriority(Job.INTERACTIVE);
		}
		
		public boolean shouldRun() {
			return hasChanges();
		}
		
		public IStatus runInUIThread(IProgressMonitor monitor) {
			processQueue();
			return Status.OK_STATUS;
		}
		
		private void processQueue() {
			try {
				String buffer = getBuffer();
				document.replace(0, document.getLength(), buffer);
			} catch(BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
}
