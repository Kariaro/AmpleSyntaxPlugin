package plugin.hardcoded.ample.rules;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import plugin.hardcoded.ample.SyntaxSet;

/**
 * 
 * @author HardCoded
 */
public class LiteralRule implements NRule {
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
