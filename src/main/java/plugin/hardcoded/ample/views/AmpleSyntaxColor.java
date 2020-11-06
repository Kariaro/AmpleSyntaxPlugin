package plugin.hardcoded.ample.views;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

import plugin.hardcoded.ample.rules.AmpleScanner;
import plugin.hardcoded.ample.syntax.AmpleContentAssist;

public class AmpleSyntaxColor extends TextSourceViewerConfiguration {
	@SuppressWarnings("unused")
	private final AmpleSyntaxEditor editor;
	
	public AmpleSyntaxColor(AmpleSyntaxEditor editor) {
		this.editor = editor;
	}
	
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		AmpleScanner scanner = new AmpleScanner();
		
		PresentationReconciler pr = new PresentationReconciler();
		DefaultDamagerRepairer ddr = new DefaultDamagerRepairer(scanner);
		pr.setRepairer(ddr, IDocument.DEFAULT_CONTENT_TYPE);
		pr.setDamager(ddr, IDocument.DEFAULT_CONTENT_TYPE);
		return pr;
	}
	
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
//		HCReconcilerStrategy strategy = new HCReconcilerStrategy();
//		strategy.setEditor(editor);
//		
//		MonoReconciler reconciler = new MonoReconciler(strategy, false);
//		return reconciler;
		return super.getReconciler(sourceViewer);
	}
	
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		assistant.setContentAssistProcessor(new AmpleContentAssist(), IDocument.DEFAULT_CONTENT_TYPE);
		return assistant;
	}
}