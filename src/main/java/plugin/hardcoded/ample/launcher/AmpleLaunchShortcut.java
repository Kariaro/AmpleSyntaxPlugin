package plugin.hardcoded.ample.launcher;

import java.io.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.console.IOConsoleOutputStream;

import hardcoded.compiler.AmpleCompilerBuild;
import hardcoded.compiler.instruction.IRProgram;
import hardcoded.vm.AmpleVm;
import plugin.hardcoded.ample.AmpleSyntaxPlugin;
import plugin.hardcoded.ample.AmpleUtils;
import plugin.hardcoded.ample.console.AmpleConsole;

public class AmpleLaunchShortcut implements ILaunchShortcut {
	public void launch(ISelection selection, String mode) {
		Object source = null;
		
		if(selection instanceof TreeSelection) {
			TreeSelection sel = (TreeSelection)selection;
			
			// Do not run multiple projects at once.
			if(sel.size() > 1 || sel.isEmpty()) return;
			source = sel.getFirstElement();
		}
		
		// Invalid launch
		if(source == null) return;
		// run / debug
		
		// Prevent java sources
		if(!(source instanceof IFile)) return;
		System.out.println("         : " + source);
		
		try {
			AmpleCompilerBuild build = new AmpleCompilerBuild();
			File file = AmpleUtils.fromIFile((IFile)source);
			
			System.out.println("Launching from file: " + file);
			System.out.println("=========================================================================================");
			
			IRProgram program = build.build(file);
			AmpleConsole console = AmpleSyntaxPlugin.getDefault().console;
			console.activate();
			
			IOConsoleOutputStream stream = console.console.newOutputStream();
			AmpleVm.run(program, new PrintStream(stream));
			stream.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void launch(IEditorPart editor, String mode) {
		System.out.println("Editor: " + mode);
		// run / debug
		
		// TODO: Implement
	}
}
