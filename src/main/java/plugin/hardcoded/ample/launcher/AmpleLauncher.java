package plugin.hardcoded.ample.launcher;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;

import hardcoded.OutputFormat;
import hardcoded.compiler.AmpleCompilerBuild;
import hardcoded.compiler.BuildConfiguration;
import hardcoded.compiler.instruction.IRProgram;
import plugin.hardcoded.ample.console.AmpleConsole;
import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleProject;

public class AmpleLauncher {
	private static IRProgram buildProgram(IFile source) {
		if(source == null) return null;
		
		AmpleProject project = AmpleCore.getAmpleProject(source);
		if(project == null) return null;
		
		// Create build configuration
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
		}
		
		if(config.isValid()) {
			System.out.println("Launching from file: " + source);
			System.out.println("=========================================================================================");
			
			AmpleCompilerBuild build = new AmpleCompilerBuild();
			try {
				return build.build(config);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public static void run(IFile source, String mode) {
		IRProgram program = buildProgram(source);
		if(program == null) return;
		
		AmpleConsole console = new AmpleConsole();
		console.addThisConsole();
		
		AmpleProcess process = new AmpleProcess(program);
		process.setOutputStream(console.getPartitioner());
		console.setActiveProcess(process);
		process.addTerminateHook(() -> {
			System.out.println("[ Flushing and closing the console output stream ]");
			
			console.firePropertyChange(console, AmpleConsole.P_PROCESS_TERMINATED, null, null);
		});
		process.start();
	}
}
