package plugin.hardcoded.ample.launcher;

import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.console.IOConsoleOutputStream;

import hardcoded.OutputFormat;
import hardcoded.compiler.AmpleCompilerBuild;
import hardcoded.compiler.BuildConfiguration;
import hardcoded.compiler.instruction.IRProgram;
import hardcoded.vm.AmpleVm;
import plugin.hardcoded.ample.AmpleSyntaxPlugin;
import plugin.hardcoded.ample.console.AmpleConsole;
import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleProject;

public class AmpleLaunchShortcut implements ILaunchShortcut {
	public void launch(ISelection selection, String mode) {
		Object source = null;
		
		if(selection instanceof TreeSelection) {
			TreeSelection sel = (TreeSelection)selection;
			if(sel.size() > 1 || sel.isEmpty()) return;
			source = sel.getFirstElement();
		}
		
		if(source instanceof IFile) {
			run((IFile)source, mode);
		}
	}
	
	public void launch(IEditorPart editor, String mode) {
		if(editor == null) return;
		IEditorInput input = editor.getEditorInput();
		
		if(input instanceof IFileEditorInput) {
			run(((IFileEditorInput)input).getFile(), mode);
		}
	}
	
	private void run(IFile source, String mode) {
		if(source == null) return;
		
		AmpleProject project = AmpleCore.getAmpleProject(source);
		if(project == null) return;
		
		AmpleConsole console = AmpleSyntaxPlugin.getDefault().console;
		console.activate();
		console.clearConsole();
		IOConsoleOutputStream stream = console.console.newOutputStream();
		
		try {
			BuildConfiguration config = new BuildConfiguration();
			config.setWorkingDirectory(project.getProject().getLocation().toFile());
			config.setStartFile(source.getProjectRelativePath().toOSString());
			for(IFolder folder : project.getSourceFolders()) {
				config.addSourceFolder(folder.getProjectRelativePath().toOSString());
			}
			config.setOutputFormat(OutputFormat.IR);
			// TODO: Add output folder settings.
			config.setOutputFile("bin/output.lir");
			
			System.out.println("Launching from file: " + source);
			System.out.println("=========================================================================================");
			
			// TODO: What do we do if we get stuch here?
			// TODO: Should the user be notified of build errors?
			
			AmpleCompilerBuild build = new AmpleCompilerBuild();
			IRProgram program = build.build(config);
			AmpleVm.run(program, new PrintStream(stream));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			stream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
