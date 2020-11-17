package plugin.hardcoded.ample.preferences;

import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IWorkbenchPreferencePage;

import static plugin.hardcoded.ample.AmplePreferences.*;
import plugin.hardcoded.ample.lir.LIRSyntaxColor;

public class LirColorPreferencePage extends ColorPreferencePage implements IWorkbenchPreferencePage {
	public LirColorPreferencePage() {
		super(new LIRSyntaxColor());
		
		TreeObject syntax = addNode("Syntax", false);
		addNode(syntax, "Labels", LIR_COLOR_LABELS);
		addNode(syntax, "Numbers", LIR_COLOR_NUMBERS);
		addNode(syntax, "Registers", LIR_COLOR_REGISTERS);
		addNode(syntax, "Bracket Content", LIR_COLOR_BRACKETCONTENT);
		addNode(syntax, "Instructions", LIR_COLOR_INSTRUCTIONS);
		addNode(syntax, "Types", LIR_COLOR_TYPES);
		addNode(syntax, "Others", LIR_COLOR_OTHERS);
	}
	
	@Override
	public void initDocument(IDocument document) {
		document.set("i8 print( i64* ):\n"
				+ "    mov     i64*             [$A], [753664]\n"
				+ "    mov     i64*             [$B], [753660]\n"
				+ "    read    i32              [$C], [$B]\n"
				+ "    add     i64*             [$A], [$A], [$C]\n"
				+ "    _while.next_0:\n"
				+ "    read    i8               [$D], [@string]\n"
				+ "    brz                      [$D], [_while.end_2]\n"
				+ "    read    i8               [$E], [@string]\n"
				+ "    write   i64*             [$A], [$E]\n"
				+ "    add     i64*             [$A], [$A], [1]\n"
				+ "    add     i64*             [@string], [@string], [1]\n"
				+ "    read    i32              [$F], [$B]\n"
				+ "    add     i32              [$G], [$F], [1]\n"
				+ "    write   i64*             [$B], [$G]\n"
				+ "    br                       [_while.next_0]\n"
				+ "    _while.end_2:\n"
				+ "    write   i64*             [$A], [10]\n"
				+ "    read    i32              [$H], [$B]\n"
				+ "    add     i32              [$I], [$H], [1]\n"
				+ "    write   i64*             [$B], [$I]\n"
				+ "    ret     i32              [0]\n"
		);
	}
}
