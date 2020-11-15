package plugin.hardcoded.ample.preferences;

import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IWorkbenchPreferencePage;

import plugin.hardcoded.ample.views.AmpleSyntaxColor;

public class AmpleColorPreferencePage extends ColorPreferencePage implements IWorkbenchPreferencePage {
	
	public AmpleColorPreferencePage() {
		super(new AmpleSyntaxColor());
	}
	
	@Override
	public void initDocument(IDocument document) {
		document.set("void main() {\n"
				+ "\t// Testing the code previewer\n"
				+ "}\n"
		);
	}
}
