package plugin.hardcoded.ample.rules;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import plugin.hardcoded.ample.SyntaxSet;

/**
 * 
 * @author HardCoded
 */
public class CodeRule implements NRule {
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
