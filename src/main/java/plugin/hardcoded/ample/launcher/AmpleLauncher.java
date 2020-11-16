package plugin.hardcoded.ample.launcher;

import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.ui.console.IOConsoleOutputStream;

import hardcoded.OutputFormat;
import hardcoded.compiler.AmpleCompilerBuild;
import hardcoded.compiler.BuildConfiguration;
import hardcoded.compiler.instruction.IRProgram;
import hardcoded.vm.AmpleVm;
import plugin.hardcoded.ample.AmpleLogger;
import plugin.hardcoded.ample.AmpleSyntaxPlugin;
import plugin.hardcoded.ample.console.AmpleConsole;
import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleProject;

public class AmpleLauncher {
	public static void run(IFile source, String mode) {
		if(source == null) return;
		
		AmpleProject project = AmpleCore.getAmpleProject(source);
		if(project == null) return;
		
		// TODO: Read the projects launch configurations and if they are absent, generate a new configuration entry in the run/debug panels.
		
		AmpleConsole console = AmpleSyntaxPlugin.getDefault().console;
		console.activate();
		console.clearConsole();
		IOConsoleOutputStream stream = console.newOutputStream();
		
		try {
			BuildConfiguration config = new BuildConfiguration();
			config.setWorkingDirectory(project.getProject().getLocation().toFile());
			config.setStartFile(source.getProjectRelativePath().toOSString());
			for(IFolder folder : project.getSourceFolders()) {
				config.addSourceFolder(folder.getProjectRelativePath().toOSString());
			}
			config.setOutputFormat(OutputFormat.IR);
			
			IFolder outputFolder = project.getConfiguration().getOutputFolder();
			if(outputFolder != null) {
				IFile outputFile = project.getConfiguration().getOutputFolder().getFile("output.lir");
				config.setOutputFile(outputFile.getLocation().toFile());
				
				System.out.println("Launching from file: " + source);
				System.out.println("=========================================================================================");
				
				// TODO: What do we do if we get stuch here?
				// TODO: Should the user be notified of build errors?
				
				AmpleCompilerBuild build = new AmpleCompilerBuild();
				IRProgram program = build.build(config);
				AmpleVm.run(program, new PrintStream(stream));
			} else {
				System.out.println("Invalid output folder 'null' ");
			}
		} catch(Exception e) {
			AmpleLogger.log(e);
		}
		
		try {
			stream.close();
		} catch(IOException e) {
			AmpleLogger.log(e);
		}
	}
}
