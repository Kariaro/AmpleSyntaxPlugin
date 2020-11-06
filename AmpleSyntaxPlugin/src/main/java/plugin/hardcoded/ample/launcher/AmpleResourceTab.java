package plugin.hardcoded.ample.launcher;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class AmpleResourceTab extends AbstractLaunchConfigurationTab {
	private boolean isInitialized;
	
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		
	}
	
	public void initializeFrom(ILaunchConfiguration config) {
		updateButtons();
	}

	public void performApply(ILaunchConfigurationWorkingCopy config) {
		
	}
	
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		{
			comp.setLayout(new GridLayout());
		}
		
		{
			Label label = new Label(comp, SWT.NONE);
			label.setText("Select all source paths");
		}
		
		{
			Composite source_panel = new Composite(comp, SWT.NONE);
			GridLayout layout = new GridLayout();
			source_panel.setLayout(layout);
			source_panel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			layout.numColumns = 2;
			
			// TODO: Implement adding and removing resources
			
			{
				TableViewer tableViewer = new TableViewer(source_panel, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
				tableViewer.setLabelProvider(new LabelProvider());
				
				Table table = tableViewer.getTable();
				table.setHeaderVisible(false);
				table.setLinesVisible(true);
				table.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				
				TableViewerColumn columnViewer = new TableViewerColumn(tableViewer, SWT.NONE);
				columnViewer.getColumn().setWidth(300);
				source_panel.addListener(SWT.Resize, new Listener() {
					public void handleEvent(Event event) {
						parent.getDisplay().asyncExec(() -> {
							columnViewer.getColumn().setWidth(table.getBounds().width - 20);
						});
					}
				});
				columnViewer.getColumn().setResizable(false);
				columnViewer.getColumn().setText("Testing");
				
				for(int i = 0; i < 10; i++) {
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText("Object" + (i + 1));
				}
			}
			
			{
				Composite panel = new Composite(source_panel, SWT.NONE);
				GridLayout panel_layout = new GridLayout();
				panel_layout.marginHeight = 0;
				panel_layout.marginWidth = 0;
				panel.setLayout(panel_layout);
				panel.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false));
				
				Button add_button = new Button(panel, SWT.NONE);
				add_button.setText("Add");
				add_button.setLayoutData(new GridData(100, 23));
				add_button.addListener(SWT.MouseDown, new Listener() {
					public void handleEvent(Event event) {
						// Folder view inside the project...
						// TODO: Give errors if source is missing..
						// TODO: This page should be inside the project settings instead of launch options.
						
					}
				});
				
				for(int i = 0; i < 5; i++) {
					Button button = new Button(panel, SWT.NONE);
					button.setText("Button" + (i + 1));
					button.setLayoutData(new GridData(100, 23));
				}
			}
		}
		
		isInitialized = true;
		updateButtons();
	}
	
	public String getName() {
		return "Source folders";
	}
	
	protected void updateButtons() {
		if(!isInitialized) return;
		
		updateLaunchConfigurationDialog();
	}
}
