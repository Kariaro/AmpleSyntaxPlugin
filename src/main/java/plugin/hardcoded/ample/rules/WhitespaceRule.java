package plugin.hardcoded.ample.rules;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * 
 * @author HardCoded
 */
public class WhitespaceRule implements NRule {
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
