package plugin.hardcoded.ample.syntax;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.ICharacterPairMatcher;

import plugin.hardcoded.ample.AmpleLogger;

public class AmpleCharacterPairMatcher implements ICharacterPairMatcher {
	private String group = "{}[]()";
	private int anchor;
	
	public void clear() {
		
	}
	
	public void dispose() {
		
	}
	
	public int getAnchor() {
		return anchor;
	}
	
	public IRegion match(IDocument document, int offset) {
		int length = document.getLength();
		if(offset > length) return null;
		
		String body = document.get();
		
		try {
			for(int i = 0; i < 2; i++) {
				int pos = offset + i - 1;
				if(pos >= length) return null;
				if(pos < 0) continue;
				
				char c = body.charAt(pos);
				int index = group.indexOf(c);
				if(index < 0) continue;
				
				if((index & 1) == 0) {
					char open = c;
					char close = group.charAt(index + 1);
					
					anchor = RIGHT;
					String string = body.substring(pos + 1);
					return match_forwards(string, pos, open, close);
				} else {
					char open = group.charAt(index - 1);
					char close = c;
					
					anchor = LEFT;
					String string = body.substring(0, pos);
					return match_backwards(string, open, close);
				}
			}
		} catch(BadLocationException e) {
			AmpleLogger.log(e);
		}
		
		return null;
	}
	
	private IRegion match_forwards(String string, final int index, char open, char close) throws BadLocationException {
		int level = 1;
		
		// Match forward
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			
			if(c == open) level++;
			if(c == close) level--;
			
			if(level == 0) {
				final int offset = index + i + 1;
				return new IRegion() {
					public int getOffset() { return offset; }
					public int getLength() { return 1; }
				};
			}
		}
		
		return null;
	}
	
	private IRegion match_backwards(String string, char open, char close) throws BadLocationException {
		int level = -1;
		
		for(int i = string.length() - 1; i >= 0; i--) {
			char c = string.charAt(i);
			
			if(c == open) level++;
			if(c == close) level--;
			
			if(level == 0) {
				final int offset = i;
				return new IRegion() {
					public int getOffset() { return offset; }
					public int getLength() { return 1; }
				};
			}
		}
		
		return null;
	}
}
