package plugin.hardcoded.ample.views;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.*;

import plugin.hardcoded.ample.AmplePreferences;
import plugin.hardcoded.ample.util.RegexRule;
import plugin.hardcoded.ample.util.TokenUtils;
import plugin.hardcoded.ample.util.WordDetectors;
import pluhin.hardcoded.ample.rules.scanner.AmpleRuleBasedScanner;

public class AmpleScanner extends AmpleRuleBasedScanner {
	private static final String[] KEYWORDS = {
		"while", "for", "if", "else", "switch", "case", "default",
		"continue", "break", "return", "export", "unsigned", "signed",
		"class", "inline"
	};
	
	private static final String[] PRIMITIVES = {
		"void", "long", "int", "short", "byte", "char", "bool",
		"double", "float"
	};
	
	public AmpleScanner(IPreferenceStore store) {
		super(store);
		updateRules();
	}
	
	public void setRange(IDocument document, int offset, int length) {
		super.setRange(document, 0, document.getLength());
	}
	
	private class LiteralRule implements IRule {
		public IToken evaluate(ICharacterScanner scanner) {
			int length = 0;
			int c;
			while((c = scanner.read()) != EOF) {
				if(!Character.isJavaIdentifierPart(c)) {
					scanner.unread();
					break;
				}
				
				length ++;
			}
			
			if(length == 0) {
				return Token.UNDEFINED;
			}
			
			return literals_token;
		}
	}
	
	private class BracketRule implements IRule {
		public IToken evaluate(ICharacterScanner scanner) {
			int c;
			if((c = scanner.read()) == EOF) return Token.EOF;
			
			switch(c) {
				case '(': return brackets_token;
				case ')': return brackets_token;
				case '[': return brackets_token;
				case ']': return brackets_token;
				case '{': return brackets_token;
				case '}': return brackets_token;
			}
			
			scanner.unread();
			return Token.UNDEFINED;
		}
	}
	
	private IToken keywords_token;
	private IToken primitives_token;
	private IToken literals_token;
	private IToken strings_token;
	private IToken chars_token;
	private IToken mlcomment_token;
	private IToken slcomment_token;
	private IToken brackets_token;
	private IToken processor_token;
	private IToken numbers_token;
	private IToken others_token;
	
	public void updateRules() {
		keywords_token = TokenUtils.getToken(preferenceStore, AmplePreferences.AMPLE_COLOR_KEYWORDS);
		primitives_token = TokenUtils.getToken(preferenceStore, AmplePreferences.AMPLE_COLOR_PRIMITIVES);
		literals_token = TokenUtils.getToken(preferenceStore, AmplePreferences.AMPLE_COLOR_LITERALS);
		strings_token = TokenUtils.getToken(preferenceStore, AmplePreferences.AMPLE_COLOR_STRINGS);
		chars_token = TokenUtils.getToken(preferenceStore, AmplePreferences.AMPLE_COLOR_CHARS);
		mlcomment_token = TokenUtils.getToken(preferenceStore, AmplePreferences.AMPLE_COLOR_ML_COMMENT);
		slcomment_token = TokenUtils.getToken(preferenceStore, AmplePreferences.AMPLE_COLOR_SL_COMMENT);
		brackets_token = TokenUtils.getToken(preferenceStore, AmplePreferences.AMPLE_COLOR_BRACKETS);
		processor_token = TokenUtils.getToken(preferenceStore, AmplePreferences.AMPLE_COLOR_PROCESSOR);
		numbers_token = TokenUtils.getToken(preferenceStore, AmplePreferences.AMPLE_COLOR_NUMBERS);
		others_token = TokenUtils.getToken(preferenceStore, AmplePreferences.AMPLE_COLOR_OTHERS);
		
		WordRule keywords = new WordRule(WordDetectors.WORD_DETECTOR);
		for(String str : KEYWORDS) {
			keywords.addWord(str, keywords_token);
		}
		
		WordRule primitives = new WordRule(WordDetectors.WORD_DETECTOR);
		for(String str : PRIMITIVES) {
			primitives.addWord(str, primitives_token);
		}
		
		SingleLineRule slcomment = new SingleLineRule("//", "\n", slcomment_token, '\0', true);
		MultiLineRule mlcomment = new MultiLineRule("/*", "*/", mlcomment_token);
		SingleLineRule processor = new SingleLineRule("@", " ", processor_token, '\0', true);
		MultiLineRule strings = new MultiLineRule("\"", "\"", strings_token, '\\', true);
		SingleLineRule chars = new SingleLineRule("\'", "\'", chars_token, '\\', true);
		LiteralRule literals = new LiteralRule();
		BracketRule brackets = new BracketRule();
		RegexRule numbers = new RegexRule(numbers_token,
			"0x[0-9a-fA-F]+[Ll]?",
			"[0-9]+[Ll]?"
		);
		
		setDefaultReturnToken(others_token);
		setRules(new IRule[] {
			mlcomment,
			slcomment,
			processor,
			strings,
			chars,
			keywords,
			primitives,
			numbers,
			literals,
			brackets,
		});
	}
}