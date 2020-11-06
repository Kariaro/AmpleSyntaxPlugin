package plugin.hardcoded.ample.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class AmpleCreationPage extends WizardPage {
	private Composite container;
	private Text textField;
	
	protected AmpleCreationPage() {
		super("Create a Ample project");
		setTitle("Create a Ample project");
		setDescription("Create a new Ample project");
	}
	
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		{
			Composite panel = new Composite(container, SWT.NONE);
			GridLayout panel_layout = new GridLayout();
			panel.setLayout(panel_layout);
			panel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			panel_layout.numColumns = 2;
			
			Label label = new Label(panel, SWT.NONE);
			label.setText("Project name:");
			
			// Create the text field
			textField = new Text(panel, SWT.BORDER | SWT.SINGLE);
			textField.setText("");
			textField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			textField.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					setPageComplete(false);
					
					String text = textField.getText();
					if(text.isEmpty()) return;
					
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(text);
					if(!project.exists()) {
						setPageComplete(true);
					}
				}
			});
		}
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		Label separator = new Label(container, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		separator.setLayoutData(gd);
		
		{
			Composite panel = new Composite(container, SWT.EMBEDDED);
			GridLayout panel_layout = new GridLayout();
			panel.setLayout(panel_layout);
			panel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			panel_layout.numColumns = 2;
			
			Label label = new Label(panel, SWT.NONE);
			label.setText("Project compiler version:");
			label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			Combo combo = new Combo(panel, SWT.BORDER | SWT.READ_ONLY);
			combo.add("Release 0.0.1");
			combo.add("Release 0.0.2");
			combo.add("Release 0.0.3");
			combo.select(0);
		}
		
		setControl(container);
		setPageComplete(false);
	}

	public String getProjectName() {
		return textField.getText();
	}
}
