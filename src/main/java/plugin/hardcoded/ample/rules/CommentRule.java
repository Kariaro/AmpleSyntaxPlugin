package plugin.hardcoded.ample.rules;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import plugin.hardcoded.ample.SyntaxSet;

/**
 * 
 * @author HardCoded
 */
public class CommentRule implements NRule {
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
