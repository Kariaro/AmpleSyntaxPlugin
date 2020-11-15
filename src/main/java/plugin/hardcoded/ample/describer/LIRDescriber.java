package plugin.hardcoded.ample.describer;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescriber;
import org.eclipse.core.runtime.content.IContentDescription;

import hardcoded.compiler.instruction.IRSerializer;

public class LIRDescriber implements IContentDescriber {
	public int describe(InputStream contents, IContentDescription description) throws IOException {
		try {
			IRSerializer.read(contents);
		} catch(IOException e) {
			throw e;
		}
		
		return VALID;
	}
	
	public QualifiedName[] getSupportedOptions() {
		return null;
	}
}
