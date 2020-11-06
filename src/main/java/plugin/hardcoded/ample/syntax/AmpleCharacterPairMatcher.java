package plugin.hardcoded.ample.syntax;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.ICharacterPairMatcher;

public class AmpleCharacterPairMatcher implements ICharacterPairMatcher {
	private String group = "{}[]()";
	
	public AmpleCharacterPairMatcher() {}
	
	public void clear() {
		
	}
	
	public void dispose() {
		
	}
	
	public int getAnchor() {
		return 0;
	}
	
	// TODO: Do not match brackets inside strings!
	// TODO: How do we know that we are inside a string?
	public IRegion match(IDocument document, int offset) {
		int length = document.getLength();
		if(offset > length) return null;
		
		try {
			for(int i = 0; i < 2; i++) {
				int pos = offset - 1 + i;
				if(pos >= length) return null;
				if(pos < 0) continue;
				
				char c = document.getChar(pos);
				int index = group.indexOf(c);
				if(index < 0) continue;
				
				if((index & 1) == 0) {
					char open = c;
					char close = group.charAt(index + 1);
					
					return match_forwards(document, pos, open, close);
				} else {
					char open = group.charAt(index - 1);
					char close = c;
					
					return match_backwards(document, pos, open, close);
				}
			}
		} catch(BadLocationException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private IRegion match_forwards(IDocument document, final int index, char open, char close) throws BadLocationException {
		int length = document.getLength();
		int level = 1;
		
		// Match forward
		for(int i = index + 1; i < length; i++) {
			char c = document.getChar(i);
			
			if(c == open) level++;
			if(c == close) level--;
			if(level == 0) {
				final int idx = i;
				return new IRegion() {
					private final int offset = idx;
					public int getOffset() { return offset; }
					public int getLength() { return 1; }
				};
			}
		}
		return null;
	}
	
	private IRegion match_backwards(IDocument document, final int index, char open, char close) throws BadLocationException {
		int level = -1;
		
		for(int i = index - 1; i >= 0; i--) {
			char c = document.getChar(i);
			
			if(c == open) level++;
			if(c == close) level--;
			if(level == 0) {
				final int idx = i;
				return new IRegion() {
					private final int offset = idx;
					public int getOffset() { return offset; }
					public int getLength() { return 1; }
				};
			}
		}
		
		return null;
	}
}
