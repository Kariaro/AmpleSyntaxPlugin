package plugin.hardcoded.ample.syntax;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.widgets.Display;

import plugin.hardcoded.ample.SyntaxSet;
import plugin.hardcoded.ample.rules.AmpleScanner;
import plugin.hardcoded.ample.views.AmpleSyntaxEditor;

public class AmpleReconcilerStrategy implements IReconcilingStrategy {
	private AmpleSyntaxEditor editor;
	private IDocument document;
	private List<Position> list = new ArrayList<>();
	
	public void setDocument(IDocument document) {
		this.document = document;
	}
	
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		calculatePositions();
	}
	
	public void reconcile(IRegion partition) {
		calculatePositions();
	}
	
	private void calculatePositions() {
		list.clear();
		
		AmpleScanner scanner = new AmpleScanner();
		scanner.setRange(document, 0, document.getLength());
		
		LinkedList<Integer> set = new LinkedList<>();
		IToken token;
		while((token = scanner.nextToken()) != Token.EOF) {
			if(token == SyntaxSet.L_CURLYBRACKET) {
				set.add(scanner.getTokenOffset());
			} else if(token == SyntaxSet.R_CURLYBRACKET) {
				if(set.isEmpty()) continue; // Invalid?
				int start = set.pollLast();
				int length = scanner.getTokenOffset() + scanner.getTokenLength();
				
				list.add(new Position(start, length - start + 2));
			}
		}
		
		Display.getDefault().asyncExec(() -> {
			editor.updateFoldingStructure(list);
		});
	}

	public void setEditor(AmpleSyntaxEditor editor) {
		this.editor = editor;
	}
}
