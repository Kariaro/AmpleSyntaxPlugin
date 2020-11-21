package plugin.hardcoded.ample.lir;

import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.texteditor.AbstractDocumentProvider;

import hardcoded.compiler.instruction.*;
import plugin.hardcoded.ample.AmpleLogger;

public class LIRDocumentProvider extends AbstractDocumentProvider {
	public LIRDocumentProvider() {
		
	}
	
	protected IDocument createDocument(Object element) throws CoreException {
		Document document = new Document();
		
		if(element instanceof IStorageEditorInput) {
			IStorage storage = ((IStorageEditorInput)element).getStorage();
			try(InputStream stream = storage.getContents()) {
				String string = processInput(IRSerializer.read(stream));
				document.set(string);
			} catch(Throwable t) {
				AmpleLogger.log("Failed to load lir file because of errors. " + t.getMessage());
			}
		}
		
		return document;
	}
	
	private String processInput(IRProgram program) {
		StringBuilder sb = new StringBuilder();
		
		for(IRFunction func : program.getFunctions()) {
			IRInstruction[] array = func.getInstructions();
			sb.append(func.toString()).append("\n");
			for(IRInstruction inst : array) {
				sb.append("    ").append(inst).append("\n");
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}

	protected IAnnotationModel createAnnotationModel(Object element) throws CoreException {
		return null;
	}
	
	protected void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite)
			throws CoreException {
	}
	
	protected IRunnableContext getOperationRunner(IProgressMonitor monitor) {
		return null;
	}
	
	public boolean isModifiable(Object element) { return false; }
	public boolean isReadOnly(Object element) { return true; }
}
