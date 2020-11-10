package plugin.hardcoded.ample.rules;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import plugin.hardcoded.ample.SyntaxSet;

/**
 * 
 * @author HardCoded
 */
public class KeywordRule implements NRule {
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
