package plugin.hardcoded.ample.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class AmpleProjectNatureHandler extends AbstractHandler {
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		
		if(currentSelection instanceof IStructuredSelection) {
			Object firstElement = ((IStructuredSelection)currentSelection).getFirstElement();
			
			IAdapterManager adapterManager = Platform.getAdapterManager();
			IResource resourceAdapter = adapterManager.getAdapter(firstElement, IResource.class);
			
			if(resourceAdapter != null) {
				IResource resource = resourceAdapter;
				IProject project = resource.getProject();
				
				try {
					IProjectDescription description = project.getDescription();
					
					List<String> list = new ArrayList<>(Arrays.asList(description.getNatureIds()));
					if(!list.isEmpty()) {
						return Status.CANCEL_STATUS;
					}
					
					list.add(AmpleProject.NATURE_ID);
					String[] natures = list.toArray(String[]::new);
					
					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					IStatus status = workspace.validateNatureSet(natures);
					
					if(status.isOK()) {
						description.setNatureIds(natures);
						project.setDescription(description, null);
					}
					
					return status;
				} catch(CoreException e) {
					throw new ExecutionException(e.getMessage(), e);
				}
			}
		}
		
		return Status.OK_STATUS;
	}
}
