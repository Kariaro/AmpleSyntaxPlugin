package plugin.hardcoded.ample;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

public class ColorSet {
	private ColorSet() {}
	
	private static final Map<RGB, Color> colors = new HashMap<>();
	
	public static Color get(int red, int green, int blue) {
		return get(new RGB(red, green, blue));
	}
	
	public static Color get(RGB rgb) {
		Color color = colors.get(rgb);
		
		if(color == null) {
			color = new Color(rgb);
			colors.put(rgb, color);
		}
		
		return color;
	}
}
