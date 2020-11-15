package plugin.hardcoded.ample.lir;

import java.util.regex.Pattern;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.*;

import hardcoded.compiler.constants.Atom;
import hardcoded.compiler.instruction.IRType;
import plugin.hardcoded.ample.AmplePreferences;
import plugin.hardcoded.ample.util.TokenUtils;

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
	
	
	private class LabelRule implements IRule {
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
					if(count == 1) {
						in.unread();
						return Token.UNDEFINED;
					}
					
					return label_token;
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
	
	private class BracketRule implements IRule {
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
					if(Pattern.matches(NUMBER_PATTERN, buffer)) return number_token;
					if(Pattern.matches(REGISTER_PATTERN, buffer)) return register_token;
					return bracketcontent_token;
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
	
	private IToken instruction_token;
	private IToken types_token;
	private IToken number_token;
	private IToken register_token;
	private IToken others_token;
	private IToken bracketcontent_token;
	private IToken label_token;
	
	public LIRScanner(IPreferenceStore store) {
		instruction_token = TokenUtils.getToken(store, AmplePreferences.LIR_COLOR_INSTRUCTIONS);
		types_token = TokenUtils.getToken(store, AmplePreferences.LIR_COLOR_TYPES);
		number_token = TokenUtils.getToken(store, AmplePreferences.LIR_COLOR_NUMBERS);
		register_token = TokenUtils.getToken(store, AmplePreferences.LIR_COLOR_REGISTERS);
		others_token = TokenUtils.getToken(store, AmplePreferences.LIR_COLOR_OTHERS);
		bracketcontent_token = TokenUtils.getToken(store, AmplePreferences.LIR_COLOR_BRACKETCONTENT);
		label_token = TokenUtils.getToken(store, AmplePreferences.LIR_COLOR_LABELS);
		
		WordRule insts = new WordRule(WORD_DETECTOR);
		for(IRType inst : IRType.values()) {
			insts.addWord(inst.name(), instruction_token);
		}
		
		WordRule types = new WordRule(WORD_DETECTOR);
		for(Atom type : Atom.values()) {
			types.addWord(type.name(), types_token);
		}
		
		setDefaultReturnToken(others_token);
		
		setRules(new IRule[] {
			new BracketRule(),
			insts,
			types,
			new LabelRule(),
		});
	}
}
