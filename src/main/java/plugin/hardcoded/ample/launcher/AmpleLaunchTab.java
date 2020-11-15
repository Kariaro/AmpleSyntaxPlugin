package plugin.hardcoded.ample.launcher;

import java.util.ArrayList;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import plugin.hardcoded.ample.AmpleLogger;
import plugin.hardcoded.ample.core.AmpleCore;
import plugin.hardcoded.ample.core.AmpleProject;

public class AmpleLaunchTab extends AbstractLaunchConfigurationTab {
	private boolean isInitialized;
	
	private AmpleProject project;
	
	private Text project_field;
	private Text entry_field;
	
	private Button entry_button;
	
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout());
		setControl(comp);
		
		{
			Group panel = new Group(comp, SWT.NONE);
			panel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			panel.setText("Project:");
			
			GridLayout layout = new GridLayout();
			panel.setLayout(layout);
			layout.numColumns = 2;
			
			project_field = new Text(panel, SWT.BORDER);
			project_field.setEditable(false); // TODO: Allow the user to write project names
			project_field.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			Button project_button = new Button(panel, SWT.NONE);
			project_button.setText("Browse");
			project_button.setLayoutData(new GridData(100, 23));
			project_button.addListener(SWT.MouseDown, new Listener() {
				public void handleEvent(Event event) {
					ElementTreeSelectionDialog dlg = new ElementTreeSelectionDialog(
						parent.getShell(),
					    new WorkbenchLabelProvider(),
					    new AmpleProjectContentProvider()
					);
					dlg.setTitle("Project Selection");
					dlg.setMessage("Select a Ample project");
					dlg.setAllowMultiple(false);
					dlg.setInput(ResourcesPlugin.getWorkspace().getRoot());
					if(project != null) {
						dlg.setInitialSelection(project.getProject());
					}
					
					if(dlg.open() == Window.OK) {
						project = AmpleCore.getAmpleProject(dlg.getFirstResult());
						updateButtons();
					}
				}
			});
		}
		
		{
			Group panel = new Group(comp, SWT.NONE);
			panel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			panel.setText("Main file:");
			
			GridLayout layout = new GridLayout();
			panel.setLayout(layout);
			layout.numColumns = 2;
			
			entry_field = new Text(panel, SWT.BORDER);
			entry_field.setMessage("Entry file");
			entry_field.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			entry_field.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					updateButtons();
				}
			});
			
			entry_button = new Button(panel, SWT.NONE);
			entry_button.setText("Search");
			entry_button.setLayoutData(new GridData(100, 23));
			entry_button.addListener(SWT.MouseDown, new Listener() {
				public void handleEvent(Event event) {
					if(project == null) return;
					
					ElementListSelectionDialog dlg = new ElementListSelectionDialog(
						parent.getShell(),
						new WorkbenchLabelProvider()
					);
					dlg.setMessage("Select type (? = any character, * = any String):");
					dlg.setTitle("Select main file");
					dlg.setMultipleSelection(false);
					try {
						dlg.setElements(getAllProjectFiles());
					} catch(CoreException e) {
						AmpleLogger.log(e);
					}
					
					if(dlg.open() == Window.OK) {
						IFile file = (IFile)dlg.getFirstResult();
						
						if(file != null) {
							entry_field.setText("" + file.getProjectRelativePath());
							updateButtons();
						}
					}
				}
			});
		}
		
		isInitialized = true;
		updateButtons();
	}
	
	public String getName() {
		return "Main";
	}
	
	public boolean isValid(ILaunchConfiguration launchConfig) {
		if(!isInitialized) return false;
		return project != null && !entry_button.getText().isBlank();
	}
	
	protected void updateButtons() {
		if(!isInitialized) return;
		project_field.setText(getProjectString());
		entry_button.setEnabled(project != null);
		updateLaunchConfigurationDialog();
	}
	
	protected String getProjectString() {
		if(project == null) return "";
		return project.getName();
	}
	
	protected void setProject(String name) {
		if(name == null || name.isEmpty()) return;
		
		try {
			project = AmpleCore.getAmpleProject(
				ResourcesPlugin.getWorkspace().getRoot().getProject(name)
			);
		} catch(Exception e) {
			// There are no [IWorkspaceRoot.validateProjectName] methods
			// so we just skip the exceptions made by [getProject]
		}
	}
	
	protected Object[] getAllProjectFiles() throws CoreException {
		IProject resource = project.getProject();
		if(!resource.isOpen()) return new Object[0];
		
		java.util.List<IResource[]> parent = new ArrayList<>();
		parent.add(project.getSourceFolders().toArray(IResource[]::new));
		java.util.List<Object> list = new ArrayList<>();
		
		// TODO: There is a possibility that this loop gets stuck.
		while(!parent.isEmpty()) {
			IResource[] array = parent.get(0);
			parent.remove(0);
			
			for(int i = 0; i < array.length; i++) {
				IResource res = array[i];
				if(res instanceof IFile) {
					if(res.getName().endsWith(".ample")) {
						list.add(res);
					}
				}
				
				if(res instanceof IFolder) {
					parent.add(((IFolder)res).members());
				}
			}
		}
		
		return list.toArray();
	}
	
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		
	}
	
	public void initializeFrom(ILaunchConfiguration config) {
		try {
			setProject(config.getAttribute(AmpleLaunchAttributes.PROJECT_FIELD, ""));
		} catch(CoreException e) {
			
		}
		
		try {
			entry_field.setText(config.getAttribute(AmpleLaunchAttributes.ENTRY_FIELD, ""));
		} catch(CoreException e) {
			
		}
		
		updateButtons();
	}

	public void performApply(ILaunchConfigurationWorkingCopy config) {
		config.setAttribute(AmpleLaunchAttributes.PROJECT_FIELD, getProjectString());
		config.setAttribute(AmpleLaunchAttributes.ENTRY_FIELD, entry_field.getText());
	}
}
