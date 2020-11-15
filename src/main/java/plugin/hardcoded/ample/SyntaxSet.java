package plugin.hardcoded.ample;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

public final class SyntaxSet {
	private SyntaxSet() {}
	
	/**
	 * This is the default token used to display syntax.
	 */
	public static final Token DEFAULT = getToken(ColorSet.get(0, 0, 0));
	
	
	/* Language colors */
	
	public static final Token SINGLELINE_COMMENT = getToken(ColorSet.get(127, 127, 0));
	public static final Token MULTILINE_COMMENT = getToken(ColorSet.get(65, 140, 255));
	
	public static final Token PRIMITIVE = getToken(ColorSet.get(112, 0, 255));
	public static final Token KEYWORD = getToken(ColorSet.get(112, 0, 255), SWT.BOLD);
	public static final Token LITERAL = getToken(ColorSet.get(33, 33, 33));
	
	public static final Token STRING_LITERAL = getToken(ColorSet.get(88, 175, 107));
	public static final Token CHAR_LITERAL = getToken(ColorSet.get(88, 175, 107));
	
	public static final Token SYNTAX_CODE = getToken(ColorSet.get(193, 110, 0));
	
	
	/* Tokens */
	
	public static final Token L_CURLYBRACKET = new Token("l_curlybracket");
	public static final Token R_CURLYBRACKET = new Token("r_curlybracket");
	public static final Token L_SQUAREBRACKET = new Token("l_squarebracket");
	public static final Token R_SQUAREBRACKET = new Token("r_squarebracket");
	public static final Token L_PARENTHESIS = new Token("l_parenthesis");
	public static final Token R_PARENTHESIS = new Token("r_parenthesis");
	
	
	
	
	
	
	
	
	
	
	
	private static Token getToken(Color foreground) { return new Token(new TextAttribute(foreground)); }
	private static Token getToken(Color foreground, int style) { return getToken(foreground, null, style); }
	@SuppressWarnings("unused")
	private static Token getToken(Color foreground, Color background) { return getToken(foreground, background, SWT.NORMAL); }
	private static Token getToken(Color foreground, Color background, int style) { return new Token(new TextAttribute(foreground, background, style)); }
}
