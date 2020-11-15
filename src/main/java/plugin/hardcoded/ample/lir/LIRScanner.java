package plugin.hardcoded.ample.lir;

import java.util.regex.Pattern;

import org.eclipse.jface.text.rules.*;

import hardcoded.compiler.constants.Atom;
import hardcoded.compiler.instruction.IRType;

public class LIRScanner extends RuleBasedScanner {
	private static final String LABEL_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789"
											+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ_.:";
	
	private static final String WORD_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
	private static final IWordDetector WORD_DETECTOR = new IWordDetector() {
		public boolean isWordStart(char c) { return WORD_CHARS.indexOf(c) >= 0; }
		public boolean isWordPart(char c) { return WORD_CHARS.indexOf(c) >= 0; }
	};
	
	private static final String NUMBER_PATTERN = "[0-9]+";
	private static final String REGISTER_PATTERN = "[@$][a-zA-Z0-9_]+";
	// private static final String LABEL_PATTERN = "[a-zA-Z0-9_\\[\\]]+";
	
	
	private static class LabelRule implements IRule {
		private ICharacterScanner in;
		private int count = 0;
		
		public IToken evaluate(ICharacterScanner in) {
			this.count = 0;
			this.in = in;
			
			for(int i = 0; i < 256; i++) {
				int c = read();
				
				if(c == EOF) {
					unreadAll();
					return Token.EOF;
				}
				
				if(LABEL_CHARS.indexOf(c) < 0) {
					unreadAll();
					return Token.UNDEFINED;
				}
				
				if(c == ':') {
					return LIRColors.LABEL;
				}
			}
			
			unreadAll();
			return Token.UNDEFINED;
		}
		
		public int read() {
			int c = in.read();
			if(c == EOF) return c;
			
			count++;
			return c;
		}
		
		public void unreadAll() {
			for(int j = 0; j < count; j++) {
				in.unread();
			}
		}
	}
	
	private static class BracketRule implements IRule {
		private ICharacterScanner in;
		private int count = 0;
		
		public IToken evaluate(ICharacterScanner in) {
			if(in.getColumn() < 1) return Token.UNDEFINED;
			
			this.count = 0;
			this.in = in;
			
			in.unread();
			int c = in.read();
			if(c != '[') {
				return c == EOF ? Token.EOF:Token.UNDEFINED;
			}
			
			String buffer = "";
			int level = 1;
			
			for(int i = 0; i < 256; i++) {
				c = read();
				
				if(c == EOF) {
					unreadAll();
					return Token.EOF;
				}
				
				if(c == '[') level++;
				if(c == ']') level--;
				
				if(level != 0)
					buffer += (char)c;
				
				if(level == 0) {
					in.unread();
					if(Pattern.matches(NUMBER_PATTERN, buffer)) return LIRColors.NUMBER;
					if(Pattern.matches(REGISTER_PATTERN, buffer)) return LIRColors.REGISTER;
					return LIRColors.BRACKET;
				}
			}
			
			unreadAll();
			return Token.UNDEFINED;
		}
		
		public int read() {
			int c = in.read();
			if(c == EOF) return c;
			
			count++;
			return c;
		}
		
		public void unreadAll() {
			for(int j = 0; j < count; j++) {
				in.unread();
			}
		}
	}
	
	public LIRScanner() {
		WordRule insts = new WordRule(WORD_DETECTOR);
		
		for(IRType inst : IRType.values()) {
			insts.addWord(inst.name(), LIRColors.INSTRUCTION);
		}
		
		WordRule atoms = new WordRule(WORD_DETECTOR);
		for(Atom atom : Atom.values()) {
			atoms.addWord(atom.name(), LIRColors.ATOM);
		}
		
		setRules(new IRule[] {
			new BracketRule(),
			insts,
			atoms,
			new LabelRule(),
		});
	}
}
