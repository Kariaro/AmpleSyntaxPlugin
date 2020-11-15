package plugin.hardcoded.ample.lir;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import plugin.hardcoded.ample.ColorSet;

public class LIRColors {
	public static final Token INSTRUCTION = getToken(ColorSet.get(85, 52, 144), SWT.BOLD);
	public static final Token BRACKET = getToken(ColorSet.get(45, 128, 162), SWT.BOLD);
	public static final Token LABEL = getToken(ColorSet.get(95, 97, 76), SWT.ITALIC);
	public static final Token ATOM = getToken(ColorSet.get(112, 0, 255));
	
	public static final Token NUMBER = getToken(ColorSet.get(0, 0, 0));
	public static final Token REGISTER = getToken(ColorSet.get(95, 97, 76));
	
	private static Token getToken(Color foreground) { return new Token(new TextAttribute(foreground)); }
	private static Token getToken(Color foreground, int style) { return getToken(foreground, null, style); }
	@SuppressWarnings("unused")
	private static Token getToken(Color foreground, Color background) { return getToken(foreground, background, SWT.NORMAL); }
	private static Token getToken(Color foreground, Color background, int style) { return new Token(new TextAttribute(foreground, background, style)); }
}
