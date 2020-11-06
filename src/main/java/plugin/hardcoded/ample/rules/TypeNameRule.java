package plugin.hardcoded.ample.rules;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import plugin.hardcoded.ample.SyntaxSet;

/**
 * 
 * @author HardCoded
 */
public class TypeNameRule implements NRule {
	private static final String[] PRIMITIVES = {
		"void", "long", "int", "short", "byte", "char", "bool",
		"double", "float"
	};
	
	public IToken process(AmpleScanner scanner) {
		for(String str : PRIMITIVES) {
			if(scanner.matchesAdvance(str)) {
				return SyntaxSet.PRIMITIVE;
			}
		}
		
		return Token.UNDEFINED;
	}
}
