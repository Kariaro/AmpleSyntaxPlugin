package plugin.hardcoded.ample.views;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import hardcoded.compiler.errors.SyntaxMarker;
import hardcoded.compiler.impl.IProgram;
import hardcoded.compiler.parsetree.ParseTreeGenerator;
import plugin.hardcoded.ample.AmplePreferences;
import plugin.hardcoded.ample.AmpleUtils;
import plugin.hardcoded.ample.outline.AmpleOutlinePage;
import plugin.hardcoded.ample.syntax.AmpleCharacterPairMatcher;

public class AmpleSyntaxEditor extends TextEditor implements IDocumentListener {
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "plugin.hardcoded.ample.views.AmpleSyntaxEditor";
	public static final int PROP_PARSETREE = 0x1001;
	
	// NOTE: This editor is bound to a single file.
	
//	private ProjectionAnnotationModel annotationModel;
//	@SuppressWarnings("unused")
//	private ProjectionSupport projectionSupport;
//	private Annotation[] oldAnnotations;
	private AmpleSyntaxColor syntax;
	private AmpleOutlinePage outlinePage;
	private IProgram parseTree;
	
	public AmpleSyntaxEditor() {
		super();
		addPropertyListener(new IPropertyListener() {
			public boolean initialized;
			
			public void propertyChanged(Object source, int propId) {
				if(propId == PROP_PARSETREE) return;
				
				if(!initialized) {
					if(getEditorInput() != null) {
						updateParseTree();
						initialized = true;
					}
				} else {
					if(!isDirty() && propId == IEditorPart.PROP_DIRTY) {
						updateParseTree();
					}
				}
			}
		});
		
		syntax = new AmpleSyntaxColor(this);
		outlinePage = new AmpleOutlinePage(this);
		
		setSourceViewerConfiguration(syntax);
//		setDocumentProvider(new AmpleDocumentProvider());
	}
	
	public void documentAboutToBeChanged(DocumentEvent event) {
		System.out.println("AmpleSyntaxEditor: documentAboutToBeChanged(DocumentEvent event) : [" + event + "]");
	}
	
	public void documentChanged(DocumentEvent event) {
		System.out.println("AmpleSyntaxEditor: documentChanged(DocumentEvent event) : [" + event + "]");
	}
	
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		
//		ProjectionViewer viewer = (ProjectionViewer)getSourceViewer();
//		projectionSupport = new ProjectionSupport(viewer, getAnnotationAccess(), getSharedColors());
//		projectionSupport.install();
//		
//		viewer.doOperation(ProjectionViewer.TOGGLE);
//		annotationModel = viewer.getProjectionAnnotationModel();
	}
	
	protected void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
	}
	
	public void updateFoldingStructure(List<Position> positions) {
//		Annotation[] annotations = new Annotation[positions.size()];
//		
//		Map<ProjectionAnnotation, Position> newAnnotations = new HashMap<>();
//		
//		for(int i = 0; i < positions.size(); i++) {
//			ProjectionAnnotation annotation = new ProjectionAnnotation();
//			newAnnotations.put(annotation, positions.get(i));
//			
//			annotations[i] = annotation;
//		}
//		
//		annotationModel.modifyAnnotations(oldAnnotations, newAnnotations, null);
//		oldAnnotations = annotations;
	}
	
	
	protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
		ISourceViewer viewer = new ProjectionViewer(parent, ruler, getOverviewRuler(), isOverviewRulerVisible(), styles);
		getSourceViewerDecorationSupport(viewer);
		return viewer;
	}
	
	
	
	
	public <T> T getAdapter(Class<T> adapter) {
		if(IContentOutlinePage.class.equals(adapter)) {
			return adapter.cast(outlinePage);
		}
		
		//System.out.println("GetAdapter: " + adapter);
		
		return super.getAdapter(adapter);
	}
	
	protected void configureSourceViewerDecorationSupport(SourceViewerDecorationSupport support) {
		super.configureSourceViewerDecorationSupport(support);

		IPreferenceStore store = getPreferenceStore();
		store.setValue(AmplePreferences.MATCHING_BRACKETS, true);
		store.setValue(AmplePreferences.MATCHING_BRACKETS_COLOR, new Color(0,0,0).getRGB().toString());
		
		support.setCharacterPairMatcher(new AmpleCharacterPairMatcher());
		support.setMatchingCharacterPainterPreferenceKeys(
			AmplePreferences.MATCHING_BRACKETS,
			AmplePreferences.MATCHING_BRACKETS_COLOR
		);
	}
	
	public void updateParseTree() {
		final IResource resource = getEditorInput().getAdapter(IResource.class);
		if(resource == null) return;
		
		try {
			resource.deleteMarkers(null, true, IResource.DEPTH_INFINITE);
		} catch(CoreException e) {
			// TODO: Wait until we can perform the parse tree update.
			return;
		}
		
		SafeRunner.run(() -> {
			IProject project = resource.getProject();
			if(!project.exists()) return;
			
			File projectPath = AmpleUtils.fileFromIProject(project);
			String filePath = resource.getProjectRelativePath().toOSString();
			
			System.out.printf("Refreshing: [%s] [%s]\n", projectPath, filePath);
			
			try {
				ParseTreeGenerator generator = new ParseTreeGenerator();
				parseTree = generator.init(projectPath, filePath);
				
				for(SyntaxMarker syntaxMarker : parseTree.getSyntaxMarkers()) {
					IMarker marker = resource.createMarker(IMarker.PROBLEM);
					
					int type = syntaxMarker.getSeverity();
					int marker_type = 0;
					
					if(type == SyntaxMarker.ERROR) {
						marker_type = IMarker.SEVERITY_ERROR;
					} else if(type == SyntaxMarker.WARNING) {
						marker_type = IMarker.SEVERITY_WARNING;
					} else {
						marker_type = IMarker.SEVERITY_INFO;
					}
					
					marker.setAttribute(IMarker.CHAR_START, syntaxMarker.getFileOffset());
					marker.setAttribute(IMarker.CHAR_END, syntaxMarker.getFileOffset() + syntaxMarker.getLocationLength());
					marker.setAttribute(IMarker.SEVERITY, marker_type);
					marker.setAttribute(IMarker.LINE_NUMBER, syntaxMarker.getLineIndex());
					marker.setAttribute(IMarker.MESSAGE, syntaxMarker.getMessage());
				}
			} catch(Throwable t) {
				System.out.println("Had errors!");
				t.printStackTrace();
			}
			
			if(parseTree != null) {
				firePropertyChange(PROP_PARSETREE);
			}
		});
	}
	
	public IProgram getParseTree() {
		return parseTree;
	}
}
