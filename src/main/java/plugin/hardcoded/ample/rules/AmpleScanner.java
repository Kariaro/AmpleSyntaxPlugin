package plugin.hardcoded.ample.rules;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.Token;

import plugin.hardcoded.ample.SyntaxSet;

public class AmpleScanner implements ITokenScanner {
	private final int EOF = -1;
	
	private final NRule[] rules;
	
	public AmpleScanner() {
		rules = new NRule[] {
			new WhitespaceRule(),
			new CommentRule(),
			new CodeRule(),
			new StringRule(),
			
			new LiteralRule(),
			new KeywordRule(),
			new TypeNameRule(),
			
			new BracketRule()
		};
	}
	
	
	private String str = null;
	private int count = 0;
	private int offset = 0;
	private int index = 0;
	
	public int read() {
		if(str == null || index >= count) return EOF;
		return (int)str.charAt(index++);
	}
	
	public void unread() {
		index--;
	}
	
	
	/**
	 * Matches the current input against the string and advances the index if correct.
	 * 
	 * @param str
	 * @return
	 */
	protected boolean matchesAdvance(String str) {
		int last = index;
		
		for(int i = 0; i < str.length(); i++) {
			int c = read();
			if(c == EOF || c != str.charAt(i)) {
				index = last;
				return false;
			}
		}
		
		return true;
	}
	
	public int getTokenLength() {
		return index - last_token;
	}
	
	public int getTokenOffset() {
		return offset + last_token;
	}
	
	private IToken _nextToken() {
		int start = index;
		
		IToken token = SyntaxSet.DEFAULT;
		int longest = 0;
		
		for(NRule rule : rules) {
			IToken result = rule.process(this);
			if(result == Token.UNDEFINED) {
				index = start;
				continue;
			}
			
			int length = getTokenLength();
			index = start;
			
			if(length >= longest) {
				longest = length;
				token = result;
			}
		}
		
		if(longest == 0) {
			// Default
			read();
			index = start;
			longest = 1;
		}
		
		index += longest;
		return token;
	}
	
	private int last_token;
	public IToken nextToken() {
		if(index >= count) return Token.EOF;
		last_token = index;
		return _nextToken();
	}
	
	public void setRange(IDocument document, int offset, int length) {
		String text = null;
		
		offset = 0;
		length = document.getLength();
		
		try {
			text = document.get(offset, length);
		} catch(BadLocationException e) {
			e.printStackTrace();
		}
		
		this.offset = offset;
		this.count = text.length();
		this.str = text;
		this.index = 0;
	}
}
