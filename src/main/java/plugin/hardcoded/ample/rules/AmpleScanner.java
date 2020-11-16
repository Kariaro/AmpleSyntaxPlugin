package plugin.hardcoded.ample.rules;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.*;

import plugin.hardcoded.ample.SyntaxSet;

@Deprecated(forRemoval = true)
public class AmpleScanner implements ITokenScanner {
	private final int EOF = -1;
	
	private final NRule[] rules;
	
	private static class BracketRule implements NRule {
		public IToken process(AmpleScanner scanner) {
			int c = scanner.read();
			
			switch(c) {
				case '{': return SyntaxSet.L_CURLYBRACKET;
				case '}': return SyntaxSet.R_CURLYBRACKET;
				case '[': return SyntaxSet.L_SQUAREBRACKET;
				case ']': return SyntaxSet.R_SQUAREBRACKET;
				case '(': return SyntaxSet.L_PARENTHESIS;
				case ')': return SyntaxSet.R_PARENTHESIS;
				default: scanner.unread();
			}
			
			return Token.UNDEFINED;
		}
	}
	
	private static class CodeRule implements NRule {
		public IToken process(AmpleScanner scanner) {
			int c = scanner.read();
			if(c != '@') {
				scanner.unread();
				return Token.UNDEFINED;
			}
			
			while((c = scanner.read()) != EOF) {
				if(!Character.isJavaIdentifierPart(c)) {
					scanner.unread();
					break;
				}
			}
			
			if(scanner.getTokenLength() > 1) {
				return SyntaxSet.SYNTAX_CODE;
			}
			
			return Token.UNDEFINED;
		}
	}
	
	private static class LiteralRule implements NRule {
		public IToken process(AmpleScanner scanner) {
			int c;
			while((c = scanner.read()) != EOF) {
				if(!Character.isJavaIdentifierPart(c)) {
					scanner.unread();
					break;
				}
			}
			
			if(scanner.getTokenLength() > 0) {
				return SyntaxSet.LITERAL;
			}
			
			return Token.UNDEFINED;
		}
	}
	
	private static class CommentRule implements NRule {
		public IToken process(AmpleScanner scanner) {
			if(scanner.matchesAdvance("/*")) {
				while(true) {
					if(scanner.matchesAdvance("*/")) {
						break;
					}
					
					if(scanner.read() == EOF) break;
				}
				
				return SyntaxSet.MULTILINE_COMMENT;
			}
			
			if(scanner.matchesAdvance("//")) {
				while(true) {
					int c = scanner.read();
					
					if(c == '\r' || c == '\n' || c == EOF) {
						return SyntaxSet.SINGLELINE_COMMENT;
					}
				}
			}
			
			return Token.UNDEFINED;
		}
	}
	
	private static class StringRule implements NRule {
		public IToken process(AmpleScanner scanner) {
			int c = scanner.read();
			if(!(c == '\'' || c == '\"')) return Token.UNDEFINED;
			
			char target = (char)c;
			while((c = scanner.read()) != EOF) {
				if(c == '\\') {
					// Skip slashes and one more character
					scanner.read();
					continue;
				}
				
				if(c == target) break;
			}
			
			return target == '"' ? SyntaxSet.STRING_LITERAL:SyntaxSet.CHAR_LITERAL;
		}
	}
	
	private static class WhitespaceRule implements NRule {
		public IToken process(AmpleScanner scanner) {
			int c;
			while((c = scanner.read()) != EOF) {
				if(!Character.isWhitespace(c)) {
					scanner.unread();
					break;
				}
			}
			
			if(scanner.getTokenLength() > 0) {
				return Token.WHITESPACE;
			}
			
			return Token.UNDEFINED;
		}
	}
	
	private static class KeywordRule implements NRule {
		private static final String[] KEYWORDS = {
			"while", "for", "if", "else", "switch", "case", "default",
			"continue", "break", "return", "export", "unsigned", "signed",
			"class", "inline"
		};
		
		public IToken process(AmpleScanner scanner) {
			for(String str : KEYWORDS) {
				if(scanner.matchesAdvance(str)) {
					return SyntaxSet.KEYWORD;
				}
			}
			
			return Token.UNDEFINED;
		}
	}
	
	private static class TypeNameRule implements NRule {
		private static final String[] PRIMITIVES = {
			"void", "long", "int", "short", "byte", "char", "bool",
			"double", "float"
		};
		
		public IToken process(AmpleScanner scanner) {
			for(String str : PRIMITIVES) {
				if(scanner.matchesAdvance(str)) {
					return SyntaxSet.PRIMITIVE;
				}
			}
			
			return Token.UNDEFINED;
		}
	}
	
	private static interface NRule {
		public static final int EOF = ICharacterScanner.EOF;
		public IToken process(AmpleScanner scanner);
	}
	
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