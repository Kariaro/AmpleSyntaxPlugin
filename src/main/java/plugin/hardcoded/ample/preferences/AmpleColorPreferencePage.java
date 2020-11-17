package plugin.hardcoded.ample.preferences;

import static plugin.hardcoded.ample.AmplePreferences.*;

import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IWorkbenchPreferencePage;

import plugin.hardcoded.ample.views.AmpleSyntaxColor;

public class AmpleColorPreferencePage extends ColorPreferencePage implements IWorkbenchPreferencePage {
	
	public AmpleColorPreferencePage() {
		super(new AmpleSyntaxColor());
		
		TreeObject syntax = addNode("Syntax", false);
		addNode(syntax, "Brackets", AMPLE_COLOR_BRACKETS);
		addNode(syntax, "Chars", AMPLE_COLOR_CHARS);
		addNode(syntax, "Strings", AMPLE_COLOR_STRINGS);
		addNode(syntax, "Keywords", AMPLE_COLOR_KEYWORDS);
		addNode(syntax, "Primitives", AMPLE_COLOR_PRIMITIVES);
		addNode(syntax, "Processor", AMPLE_COLOR_PROCESSOR);
		addNode(syntax, "Literals", AMPLE_COLOR_LITERALS);
		addNode(syntax, "Numbers", AMPLE_COLOR_NUMBERS);
		addNode(syntax, "Others", AMPLE_COLOR_OTHERS);

		TreeObject comments = addNode("Comments", false);
		addNode(comments, "Singleline Comment", AMPLE_COLOR_SL_COMMENT);
		addNode(comments, "Multiline Comment", AMPLE_COLOR_ML_COMMENT);
	}
	
	@Override
	public void initDocument(IDocument document) {
		document.set("@set GLOBAL 32;\n" +
				"@type WORD short;\n" +
				"// @unset WORD;\n" +
				"\n" +
				"/* A long multiline comment that\n" +
				" * you can write stuff.\n" +
				" */\n" +
				"export void main(int a, int b) {\n" +
				"    int var = 204 ^ 229l * 119 ^ 75 ^ ('a');\n" +
				"    WORD[] array;\n" +
				"    \n" +
				"    int arg = 32L, arg2 = 0xAb75;\n" +
				"    // Unary get pointer address to value.\n" +
				"    int* reference = &arg2;\n" +
				"    \n" +
				"    print(\"Generic message\");\n" +
				"    \n" +
				"    // Should throw an error.\n" +
				"    void __0 = (byte)0x1234L;\n" +
				"    \n" +
				"    while(a > b) {\n" +
				"        if(b++) break;\n" +
				"    }\n" +
				"    \n" +
				"    bool result = print(text);\n" +
				"    return result;\n" +
				"}\n"
		);
	}
}
