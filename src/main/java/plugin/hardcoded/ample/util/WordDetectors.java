package plugin.hardcoded.ample.util;

import org.eclipse.jface.text.rules.IWordDetector;

public class WordDetectors {
	private static final String WORD_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static final IWordDetector LITERAL_DETECTOR = new IWordDetector() {
		public boolean isWordStart(char c) { return Character.isJavaIdentifierStart(c); }
		public boolean isWordPart(char c) { return Character.isJavaIdentifierPart(c); }
	};
	
	public static final IWordDetector WORD_DETECTOR = new IWordDetector() {
		public boolean isWordStart(char c) { return WORD_CHARS.indexOf(c) >= 0; }
		public boolean isWordPart(char c) { return WORD_CHARS.indexOf(c) >= 0; }
	};
}
