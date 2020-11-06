package plugin.hardcoded.ample.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import plugin.hardcoded.ample.AmplePreferences;

public class AmplePropertyPage extends PropertyPage implements IWorkbenchPropertyPage {
	private AmpleProject project;
	private java.util.List<IFolder> sourceFolders = new ArrayList<>();
	
	// TODO: Save the properties to the projects .aproj
	private TableViewer sourceFolderTable;
	private Button up_button;
	private Button down_button;
	private Button remove_button;
	
	protected Control createContents(Composite parent) {
		try {
			project = AmpleCore.getAmpleProject(getElement());
		} catch(Exception e) {
			Label error = new Label(parent, SWT.NONE);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			error.setText(sw.getBuffer().toString());
			return error;
		}
		
		TabFolder folder = new TabFolder(parent, SWT.NONE);
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		folder.setLayout(new GridLayout());
		addSourceFolderTab(folder);
		
		return folder;
	}

	// TODO: Add project errors if a source folder is missing
	private void addSourceFolderTab(TabFolder folder) {
		TabItem tab_item = new TabItem(folder, SWT.NONE);
		tab_item.setText("Source folders");
		
		Composite panel = new Composite(folder, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setLayout(new GridLayout());
		tab_item.setControl(panel);
		
		Label label = new Label(panel, SWT.NONE);
		label.setText("Select all source paths:");
		
		{
			Composite source_panel = new Composite(panel, SWT.NONE);
			GridLayout layout = new GridLayout();
			source_panel.setLayout(layout);
			source_panel.setLayoutData(new GridData(GridData.FILL_BOTH));
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			layout.numColumns = 2;
			
			{
				sourceFolderTable = new TableViewer(source_panel, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
				sourceFolderTable.setLabelProvider(new LabelProvider());
				
				Table table = sourceFolderTable.getTable();
				table.setHeaderVisible(false);
				table.setLinesVisible(true);
				table.setDragDetect(true);
				table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				
				TableViewerColumn columnViewer = new TableViewerColumn(sourceFolderTable, SWT.NONE);
				columnViewer.getColumn().setWidth(100);
				
				source_panel.addListener(SWT.Resize, new Listener() {
					public void handleEvent(Event event) {
						folder.getDisplay().asyncExec(() -> {
							columnViewer.getColumn().setWidth(table.getBounds().width - 40);
						});
					}
				});
				columnViewer.getColumn().setResizable(false);
				columnViewer.getColumn().setText("");
				
				AmpleDocument doc = project.getDocument();
				for(String str : doc.getSourceFolders()) {
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText(str);
					item.setImage(AmplePreferences.getImage(AmplePreferences.AMPLE_SOURCE_FOLDER));
					
					sourceFolders.add(project.getProject().getFolder(str));
				}
			}
			
			{
				Composite button_panel = new Composite(source_panel, SWT.NONE);
				GridLayout panel_layout = new GridLayout();
				panel_layout.marginHeight = 0;
				panel_layout.marginWidth = 0;
				button_panel.setLayout(panel_layout);
				button_panel.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false));
				
				up_button = new Button(button_panel, SWT.NONE);
				up_button.setText("Up");
				up_button.setEnabled(false);
				up_button.setLayoutData(new GridData(92, 25));
				up_button.addListener(SWT.MouseDown, new Listener() {
					public void handleEvent(Event event) {
						Table table = sourceFolderTable.getTable();
						int index = table.getSelectionIndex();
						if(index < 1) return;
						
						TableItem t0 = table.getItem(index);
						TableItem t1 = table.getItem(index - 1);
						String old = t0.getText();
						t0.setText(t1.getText());
						t1.setText(old);
						table.select(index - 1);
						
						{
							IFolder a = sourceFolders.get(index);
							IFolder b = sourceFolders.get(index - 1);
							sourceFolders.set(index, b);
							sourceFolders.set(index - 1, a);
						}
						
						updateArrowButtonSourcePage();
					}
				});
				
				down_button = new Button(button_panel, SWT.NONE);
				down_button.setText("Down");
				down_button.setEnabled(false);
				down_button.setLayoutData(new GridData(92, 25));
				down_button.addListener(SWT.MouseDown, new Listener() {
					public void handleEvent(Event event) {
						Table table = sourceFolderTable.getTable();
						int index = table.getSelectionIndex();
						int count = table.getItemCount();
						if(index > count - 1) return;
						
						TableItem t0 = table.getItem(index);
						TableItem t1 = table.getItem(index + 1);
						String old = t0.getText();
						t0.setText(t1.getText());
						t1.setText(old);
						table.select(index + 1);
						
						{
							IFolder a = sourceFolders.get(index);
							IFolder b = sourceFolders.get(index + 1);
							sourceFolders.set(index, b);
							sourceFolders.set(index + 1, a);
						}
						
						updateArrowButtonSourcePage();
					}
				});
				
				Button add_button = new Button(button_panel, SWT.NONE);
				add_button.setText("Add");
				add_button.setLayoutData(new GridData(92, 25));
				add_button.addListener(SWT.MouseDown, new Listener() {
					public void handleEvent(Event event) {
						ElementTreeSelectionDialog dlg = new ElementTreeSelectionDialog(
							folder.getShell(),
							new WorkbenchLabelProvider(),
							new AmpleProjectFolderContentProvider(sourceFolders)
						);
						dlg.setInput(getProject());
						dlg.setMessage("Add a new source folder");
						dlg.setTitle("Add source folder");
						
						if(dlg.open() == Window.OK) {
							Object object = dlg.getFirstResult();
							
							if(object instanceof IFolder) {
								IFolder folder = (IFolder)object;
								TableItem item = new TableItem(sourceFolderTable.getTable(), SWT.NONE);
								item.setImage(AmplePreferences.getImage(AmplePreferences.AMPLE_SOURCE_FOLDER));
								item.setText(folder.getName());
								sourceFolders.add(folder);
							}
						}
					}
				});
				
				remove_button = new Button(button_panel, SWT.NONE);
				remove_button.setText("Remove");
				remove_button.setEnabled(false);
				remove_button.setLayoutData(new GridData(92, 25));
				remove_button.addListener(SWT.MouseDown, new Listener() {
					public void handleEvent(Event event) {
						Table table = sourceFolderTable.getTable();
						int index = table.getSelectionIndex();
						int count = table.getItemCount();
						if(index < 0) return;
						
						table.remove(index);
						sourceFolders.remove(index);
						if(count != 1) {
							if(index > count - 2) {
								table.select(index - 1);
							} else {
								table.select(index);
							}
						}
						
						updateArrowButtonSourcePage();
					}
				});
				
				sourceFolderTable.getTable().addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						updateArrowButtonSourcePage();
					}
				});
				
				// Update the buttons
				updateArrowButtonSourcePage();
			}
		}
	}
	
	private void updateArrowButtonSourcePage() {
		Table table = sourceFolderTable.getTable();
		
		int index = table.getSelectionIndex();
		int count = table.getItemCount();
		
		up_button.setEnabled(index > 0);
		down_button.setEnabled((index != -1) && (index < count - 1));
		remove_button.setEnabled(index != -1);
	}
	
	protected void performApply() {
		super.performApply();
		
		try {
			AmpleDocument doc = project.getDocument();
			doc.updateSourceFolders(sourceFolders);
			project.saveDocument();
			
			// TODO: Does this update the project?
			project.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private IProject getProject() {
		return (IProject)getElement();
	}
}
