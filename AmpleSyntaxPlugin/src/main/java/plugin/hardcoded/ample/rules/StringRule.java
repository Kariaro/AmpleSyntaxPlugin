package plugin.hardcoded.ample.rules;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import plugin.hardcoded.ample.SyntaxSet;

/**
 * 
 * @author HardCoded
 */
public class StringRule implements NRule {
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
