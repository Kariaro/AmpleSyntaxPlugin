package plugin.hardcoded.ample;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorSet {
	private final Map<String, Color> colors;
	
	public ColorSet() {
		colors = new HashMap<>();
	}
	
	public Color get(String name) {
		return colors.get(name);
	}
	
	public void add(String name, Color color) {
		colors.put(name, color);
	}
}
