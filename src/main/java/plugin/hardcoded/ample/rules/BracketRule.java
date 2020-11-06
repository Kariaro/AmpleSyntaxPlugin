package plugin.hardcoded.ample.rules;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import plugin.hardcoded.ample.SyntaxSet;

/**
 * 
 * @author HardCoded
 */
public class BracketRule implements NRule {
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
