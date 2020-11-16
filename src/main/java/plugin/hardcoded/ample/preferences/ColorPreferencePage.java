package plugin.hardcoded.ample.preferences;

import java.util.*;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import plugin.hardcoded.ample.AmpleSyntaxPlugin;
import pluhin.hardcoded.ample.rules.scanner.AmpleTextSourceViewerConfiguration;

public abstract class ColorPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	public static class HighlightColor {
		public RGB color;
		public boolean enabled;
		public boolean bold;
		public boolean italic;
		public boolean strikethough;
		public boolean underline;
		
		public HighlightColor(HighlightColor color) {
			this(color == null ? "0,0,0,0":color.toString());
		}
		
		public HighlightColor(String serial) {
			if(serial == null || serial.isBlank()) {
				color = new RGB(0, 0, 0);
				return;
			}
			
			// red,green,blue,flags
			String[] parts = serial.split(",");
			
			try {
				color = new RGB(
					Integer.valueOf(parts[0]),
					Integer.valueOf(parts[1]),
					Integer.valueOf(parts[2])
				);
				
				int flags = Integer.valueOf(parts[3]);
				bold = (flags & 1) != 0;
				italic = (flags & 2) != 0;
				strikethough = (flags & 4) != 0;
				underline = (flags & 8) != 0;
				enabled = (flags & 16) != 0;
			} catch(NumberFormatException e) {
				color = new RGB(0, 0, 0);
			}
		}
		
		public int getFlags() {
			return (bold ? 1:0)
				| (italic ? 2:0)
				| (strikethough ? 4:0)
				| (underline ? 8:0)
				| (enabled ? 16:0);
		}
		
		public String toString() {
			if(color == null)
				color = new RGB(0, 0, 0);
			
			return color.red + "," + color.green + "," + color.blue + "," + getFlags();
		}
	}
	
	private static class ColorLabelProvider implements ILabelProvider {
		public String getText(Object element) { return Objects.toString(element, ""); }
		public boolean isLabelProperty(Object element, String property) { return false; }
		public void removeListener(ILabelProviderListener listener) {}
		public void addListener(ILabelProviderListener listener) {}
		public Image getImage(Object element) { return null; }
		public void dispose() {}
	}
	
	protected class TreeObject {
		public final Boolean forced_value;
		public final String name;
		public final String id;
		
		private HighlightColor item;
		
		public TreeObject(String name, String id) {
			this(name, id, id == null ? false:null);
		}
		
		public TreeObject(String name, String id, Boolean forced_value) {
			this.name = Objects.toString(name, "");
			this.forced_value = forced_value;
			this.id = id;
		}
		
		public String toSerial() {
			if(item == null) return "0,0,0,0";
			return item.toString();
		}
		
		public String toString() {
			return name;
		}
	}
	
	protected TreeObject addNode(String name) { return addNode(ROOT, name, null, null); }
	protected TreeObject addNode(String name, String id) { return addNode(ROOT, name, id, null); }
	protected TreeObject addNode(String name, Boolean forced_value) { return addNode(ROOT, name, null, forced_value); }
	protected TreeObject addNode(TreeObject parent, String name, String id) { return addNode(parent, name, id, null); }
	protected TreeObject addNode(TreeObject parent, String name, String id, Boolean forced_value) {
		java.util.List<TreeObject> list = tree.get(parent);
		TreeObject result = new TreeObject(name, id, forced_value);
		
		if(list == null) {
			list = new ArrayList<>();
			tree.put(parent, list);
		}
		
		list.add(result);
		return result;
	}
	
	private class ColorContentProvider implements ITreeContentProvider {
		public Object getParent(Object element) { return null; }
		public Object[] getElements(Object input) { return getChildren(input); }
		public Object[] getChildren(Object parent) {
			java.util.List<TreeObject> list = tree.get(parent);
			return list != null ? list.toArray():new Object[0];
		}
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}
	}
	

	private final TreeObject ROOT = new TreeObject("ROOT", null);
	private final Map<TreeObject, java.util.List<TreeObject>> tree = new LinkedHashMap<>();
	private final TempPreferenceStore storeTemp;
	
	private final AmpleTextSourceViewerConfiguration sourceViewer;
	
	private ProjectionViewer viewer;
	private TreeViewer editor_tree;
	private Button enable_button;
	private Label color_label;
	private ColorSelector color_button;
	private Button bold_button;
	private Button italic_button;
	private Button strikethrough_button;
	private Button underline_button;
	private Document document;
	
	private TreeObject selection;
	
	public ColorPreferencePage(AmpleTextSourceViewerConfiguration viewer) {
		Assert.isNotNull(viewer);
		
		storeTemp = new TempPreferenceStore();
		storeTemp.setPreferenceStore(AmpleSyntaxPlugin.getDefault().getPreferenceStore());
		sourceViewer = viewer;
		sourceViewer.setPreferenceStore(storeTemp);
	}
	
	protected Control createContents(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		panel.setLayout(layout);
		
		createApperancePage(panel);
		createPreviewPage(panel);
		
		return panel;
	}
	
	public final void init(IWorkbench workbench) {
		IPreferenceStore store = AmpleSyntaxPlugin.getDefault().getPreferenceStore();
		setPreferenceStore(store);
		
		for(TreeObject object : tree.keySet()) {
			if(object.id != null) {
				object.item = new HighlightColor(store.getString(object.id));
				if(object.forced_value != null) object.item.enabled = object.forced_value;
			}
		}
		
		for(java.util.List<TreeObject> list : tree.values()) {
			for(TreeObject object : list) {
				if(object.id != null) {
					object.item = new HighlightColor(store.getString(object.id));
					if(object.forced_value != null) object.item.enabled = object.forced_value;
				}
			}
		}
	}
	
	protected void performApply() {
		super.performApply();
		
		IPreferenceStore store = getPreferenceStore();
		for(TreeObject object : tree.keySet()) {
			if(object.id != null) {
				store.setValue(object.id, object.toSerial());
			}
		}
		
		for(java.util.List<TreeObject> list : tree.values()) {
			for(TreeObject object : list) {
				if(object.id != null) {
					store.setValue(object.id, object.toSerial());
				}
			}
		}
		
		storeTemp.clear();
	}
	
	protected void performDefaults() {
		super.performDefaults();
		
		IPreferenceStore store = getPreferenceStore();
		for(TreeObject object : tree.keySet()) {
			if(object.id != null) {
				store.setToDefault(object.id);
				object.item = new HighlightColor(store.getDefaultString(object.id));
				if(object.forced_value != null) object.item.enabled = object.forced_value;
				
				if(object == selection) {
					updateButtonsWithSelection();
				}
			}
		}
		
		for(java.util.List<TreeObject> list : tree.values()) {
			for(TreeObject object : list) {
				if(object.id != null) {
					store.setToDefault(object.id);
					object.item = new HighlightColor(store.getDefaultString(object.id));
					if(object.forced_value != null) object.item.enabled = object.forced_value;
					
					if(object == selection) {
						updateButtonsWithSelection();
					}
				}
			}
		}

		storeTemp.clear();
		if(viewer != null) {
			viewer.invalidateTextPresentation();
		}
	}
	
	private void updateButtonsWithSelection() {
		if(selection == null || selection.id == null) {
			enable_button.setEnabled(false);
			color_button.setEnabled(false);
			color_label.setEnabled(false);
			bold_button.setEnabled(false);
			italic_button.setEnabled(false);
			strikethrough_button.setEnabled(false);
			underline_button.setEnabled(false);
			
			color_button.setColorValue(new RGB(0, 0, 0));
			enable_button.setSelection(false);
			bold_button.setSelection(false);
			italic_button.setSelection(false);
			strikethrough_button.setSelection(false);
			underline_button.setSelection(false);
			return;
		}
		
		boolean isEnabled;
		if(selection.forced_value != null) {
			enable_button.setEnabled(false);
			isEnabled = selection.forced_value;
		} else {
			enable_button.setEnabled(true);
			isEnabled = selection.item.enabled;
		}

		color_label.setEnabled(isEnabled);
		color_button.setEnabled(isEnabled);
		bold_button.setEnabled(isEnabled);
		italic_button.setEnabled(isEnabled);
		strikethrough_button.setEnabled(isEnabled);
		underline_button.setEnabled(isEnabled);
		
		HighlightColor item = selection.item;
		enable_button.setSelection(isEnabled);
		color_button.setColorValue(item.color);
		bold_button.setSelection(item.bold);
		italic_button.setSelection(item.italic);
		strikethrough_button.setSelection(item.strikethough);
		underline_button.setSelection(item.underline);
	}
	
	private void createApperancePage(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;
		panel.setLayout(layout);
		
		{
			GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			gd.horizontalSpan = 2;
			Label label = new Label(panel, SWT.NONE);
			label.setText("Element:");
			label.setLayoutData(gd);
		}
		
		{
			editor_tree = new TreeViewer(panel, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
			{
				GridData gridData = new GridData(GridData.FILL_BOTH);
				gridData.widthHint = 252;
				editor_tree.getTree().setLayoutData(gridData);
			}
			editor_tree.setLabelProvider(new ColorLabelProvider());
			editor_tree.setContentProvider(new ColorContentProvider());
			editor_tree.setInput(ROOT);
			editor_tree.getTree().addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					IStructuredSelection sel = editor_tree.getStructuredSelection();
					selection = (TreeObject)sel.getFirstElement();
					updateButtonsWithSelection();
					
//					if(selection.id != null) {
//						System.out.println("Updating: " + selection.id + " / " + selection.item);
//					}
				}
			});
			
			
			Composite buttonsPanel = new Composite(panel, SWT.NONE);
			buttonsPanel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false));
			buttonsPanel.setSize(500, 500);

			GridLayout layout_2 = new GridLayout();
			layout_2.marginHeight = 0;
			layout_2.marginWidth = 0;
			layout_2.numColumns = 2;
			buttonsPanel.setLayout(layout_2);
			
			{
				GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
				gd.horizontalSpan = 2;
				enable_button = new Button(buttonsPanel, SWT.CHECK);
				enable_button.setLayoutData(gd);
				enable_button.setText("Enable");
				enable_button.setEnabled(false);
				
				
				gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
				gd.horizontalIndent = LayoutConstants.getIndent();
				color_label = new Label(buttonsPanel, SWT.LEFT);
				color_label.setLayoutData(gd);
				color_label.setText("Color: ");
				color_button = new ColorSelector(buttonsPanel);
				color_button.getButton().setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
				color_button.setEnabled(false);
				color_label.setEnabled(false);
				
				
				gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
				gd.horizontalIndent = LayoutConstants.getIndent();
				gd.horizontalSpan = 2;
				bold_button = new Button(buttonsPanel, SWT.CHECK);
				bold_button.setText("Bold");
				bold_button.setLayoutData(gd);
				bold_button.setEnabled(false);
				
				
				gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
				gd.horizontalIndent = LayoutConstants.getIndent();
				gd.horizontalSpan = 2;
				italic_button = new Button(buttonsPanel, SWT.CHECK);
				italic_button.setText("Italic");
				italic_button.setLayoutData(gd);
				italic_button.setEnabled(false);
				
				
				gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
				gd.horizontalIndent = LayoutConstants.getIndent();
				gd.horizontalSpan = 2;
				strikethrough_button = new Button(buttonsPanel, SWT.CHECK);
				strikethrough_button.setText("Strikethough");
				strikethrough_button.setLayoutData(gd);
				strikethrough_button.setEnabled(false);
				
				
				gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
				gd.horizontalIndent = LayoutConstants.getIndent();
				gd.horizontalSpan = 2;
				underline_button = new Button(buttonsPanel, SWT.CHECK);
				underline_button.setText("Underline");
				underline_button.setLayoutData(gd);
				underline_button.setEnabled(false);
				
				enable_button.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						if(hasItem()) {
							selection.item.enabled = enable_button.getSelection();
							update(selection);
						}
						
						boolean enable = enable_button.getSelection();
						color_button.setEnabled(enable);
						color_label.setEnabled(enable);
						bold_button.setEnabled(enable);
						italic_button.setEnabled(enable);
						strikethrough_button.setEnabled(enable);
						underline_button.setEnabled(enable);
					}
				});
				color_button.getButton().addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						if(hasItem()) {
							selection.item.color = color_button.getColorValue();
							update(selection);
						}
					}
				});
				bold_button.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						if(hasItem()) {
							selection.item.bold = bold_button.getSelection();
							update(selection);
						}
					}
				});
				italic_button.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						if(hasItem()) {
							selection.item.italic = italic_button.getSelection();
							update(selection);
						}
					}
				});
				strikethrough_button.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						if(hasItem()) {
							selection.item.strikethough = strikethrough_button.getSelection();
							update(selection);
						}
					}
				});
				underline_button.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						if(hasItem()) {
							selection.item.underline = underline_button.getSelection();
							update(selection);
						}
					}
				});
			}
		}
	}
	
	private boolean hasItem() {
		return selection != null && selection.item != null;
	}
	
	private void createPreviewPage(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		panel.setLayout(layout);
		
		Label label = new Label(panel, SWT.NONE);
		label.setText("Preview:");
		
		storeTemp.addPropertyChangeListener(sourceViewer);
		viewer = new ProjectionViewer(panel, null, null, false, SWT.H_SCROLL | SWT.BORDER);
		viewer.getTextWidget().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		viewer.getTextWidget().setFont(JFaceResources.getTextFont());
		viewer.setEditable(false);
		viewer.configure(sourceViewer);
		
		document = new Document();
		initDocument(document);
		
		viewer.setDocument(document);
	}
	
	public IDocument getDocument() {
		return document;
	}
	
	public abstract void initDocument(IDocument document);
	
	private void update(TreeObject object) {
		if(object != null) {
			if(object.item.enabled == false) {
				storeTemp.setObject(object.id, getPreferenceStore().getDefaultString(object.id));
			} else {
				storeTemp.setObject(object.id, object.item.toString());
			}
		}
		
		viewer.refresh();
	}
}
