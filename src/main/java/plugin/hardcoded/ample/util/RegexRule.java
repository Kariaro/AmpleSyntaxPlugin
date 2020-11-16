package plugin.hardcoded.ample.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.rules.*;

/**
 * This rule could be a very expensive operation
 */
public class RegexRule implements IRule {
	private List<Pattern> list = new ArrayList<>();
	private IToken token;
	
	public RegexRule(IToken token, String... patterns) {
		for(String str : patterns) {
			list.add(Pattern.compile(str));
		}
		
		this.token = token;
	}
	
	public IToken evaluate(ICharacterScanner scanner) {
		String buffer = readAll(scanner);
		
		String current = "";
		for(Pattern pattern : list) {
			Matcher matcher = pattern.matcher(buffer);
			if(matcher.lookingAt()) {
				int end = matcher.end();
				String cut = buffer.substring(0, end);
				
				if(cut.length() > current.length()) {
					current = cut;
				}
			}
		}
		
		if(current.length() == 0) {
			return Token.UNDEFINED;
		}
		
		for(int i = 0; i < current.length(); i++) {
			scanner.read();
		}
		
		return token;
	}

	private String readAll(ICharacterScanner scanner) {
		String buffer = "";
		int length = 0;
		
		int max = 255;
		while(true) {
			int c = scanner.read();
			if(c == -1) {
				scanner.unread();
				break;
			}
			
			buffer += (char)c;
			length++;
			
			max--;
			if(max < 0) break;
		}
		
		for(int i = 0; i < length; i++) {
			scanner.unread();
		}
		
		return buffer;
	}
	
}
