package plugin.hardcoded.ample.util;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.swt.SWT;

import plugin.hardcoded.ample.ColorSet;
import plugin.hardcoded.ample.preferences.ColorPreferencePage.HighlightColor;

public class TokenUtils {
	public static IToken create(IPreferenceStore store, String id) {
		HighlightColor color = new HighlightColor(store.getString(id));
		
		if(!color.enabled) {
			color = new HighlightColor(store.getDefaultString(id));
		}
		
		return null;
	}
	
	public static TextAttribute createAttribute(HighlightColor color) {
		TextAttribute attribute = new TextAttribute(ColorSet.get(color.color), null,
			(color.bold ? SWT.BOLD:0) |
			(color.italic ? SWT.ITALIC:0) |
			(color.strikethough ? TextAttribute.STRIKETHROUGH:0) |
			(color.underline ? TextAttribute.UNDERLINE:0)
		);
		
		return attribute;
	}
}
