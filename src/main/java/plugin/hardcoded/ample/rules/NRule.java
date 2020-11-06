package plugin.hardcoded.ample.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;

public interface NRule {
	public static final int EOF = ICharacterScanner.EOF;
	public IToken process(AmpleScanner scanner);
}
