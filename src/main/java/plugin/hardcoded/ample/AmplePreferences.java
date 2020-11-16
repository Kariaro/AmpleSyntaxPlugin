package plugin.hardcoded.ample;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class AmplePreferences {
	public static final String PLUGIN_ID = "plugin.hardcoded.ample";
	
	public static final String MATCHING_BRACKETS				= ".matching_brackets";
	public static final String MATCHING_BRACKETS_COLOR			= ".matching_brackets_color";
	
	// syntax coloring masks
	private static final int BOLD = 1;
	private static final int ITALIC = 2;
	// private static final int STRIKETHOUGH = 4;
	// private static final int UNDERLINE = 8;
	
	// syntax coloring ids
	public static final String LIR_COLOR_ID						= getPref("lir.colors");
	public static final String AMPLE_COLOR_ID					= getPref("ample.colors");
	
	// lir syntax coloring preferences
	public static final String LIR_COLOR_LABELS					= getPref("lir.colors.labels");
	public static final String LIR_COLOR_NUMBERS				= getPref("lir.colors.numbers");
	public static final String LIR_COLOR_REGISTERS				= getPref("lir.colors.registers");
	public static final String LIR_COLOR_BRACKETCONTENT			= getPref("lir.colors.bracketcontent");
	public static final String LIR_COLOR_INSTRUCTIONS			= getPref("lir.colors.instructions");
	public static final String LIR_COLOR_TYPES					= getPref("lir.colors.types");
	public static final String LIR_COLOR_OTHERS					= getPref("lir.colors.others");
	// default theme
	public static final String LIR_COLOR_DEF_LABELS				= getHighlight(95, 97, 76, ITALIC);
	public static final String LIR_COLOR_DEF_NUMBERS			= getHighlight(0, 0, 0);
	public static final String LIR_COLOR_DEF_REGISTERS			= getHighlight(95, 97, 76);
	public static final String LIR_COLOR_DEF_BRACKETCONTENT		= getHighlight(45, 128, 162, BOLD);
	public static final String LIR_COLOR_DEF_INSTRUCTIONS		= getHighlight(85, 52, 144, BOLD);
	public static final String LIR_COLOR_DEF_TYPES				= getHighlight(112, 0, 255);
	public static final String LIR_COLOR_DEF_OTHERS				= getHighlight(0, 0, 0);
	// dark theme
	public static final String LIR_COLOR_DDEF_LABELS			= getHighlight(255, 255, 128, ITALIC);
	public static final String LIR_COLOR_DDEF_NUMBERS			= getHighlight(192, 192, 192);
	public static final String LIR_COLOR_DDEF_REGISTERS			= getHighlight(234, 77, 0);
	public static final String LIR_COLOR_DDEF_BRACKETCONTENT	= getHighlight(219, 216, 164, BOLD | ITALIC);
	public static final String LIR_COLOR_DDEF_INSTRUCTIONS		= getHighlight(193, 166, 140, BOLD);
	public static final String LIR_COLOR_DDEF_TYPES				= getHighlight(182, 109, 109);
	public static final String LIR_COLOR_DDEF_OTHERS			= getHighlight(192, 192, 192);
	
	
	// ample syntax coloring preferences
	public static final String AMPLE_COLOR_KEYWORDS				= getPref("ample.colors.keywords");
	public static final String AMPLE_COLOR_PRIMITIVES			= getPref("ample.colors.primitives");
	public static final String AMPLE_COLOR_STRINGS				= getPref("ample.colors.strings");
	public static final String AMPLE_COLOR_CHARS				= getPref("ample.colors.chars");
	public static final String AMPLE_COLOR_PROCESSOR			= getPref("ample.colors.processor");
	public static final String AMPLE_COLOR_LITERALS				= getPref("ample.colors.literals");
	public static final String AMPLE_COLOR_NUMBERS				= getPref("ample.colors.numbers");
	public static final String AMPLE_COLOR_SL_COMMENT			= getPref("ample.colors.singlelinecomment");
	public static final String AMPLE_COLOR_ML_COMMENT			= getPref("ample.colors.multilinecomment");
	public static final String AMPLE_COLOR_BRACKETS				= getPref("ample.colors.brackets");
	public static final String AMPLE_COLOR_OTHERS				= getPref("ample.colors.others");
	// default theme
	public static final String AMPLE_COLOR_DEF_KEYWORDS			= getHighlight(112, 0, 255, BOLD);
	public static final String AMPLE_COLOR_DEF_PRIMITIVES		= getHighlight(112, 0, 255);
	public static final String AMPLE_COLOR_DEF_STRINGS			= getHighlight(88, 175, 107);
	public static final String AMPLE_COLOR_DEF_CHARS			= getHighlight(88, 175, 107);
	public static final String AMPLE_COLOR_DEF_PROCESSOR		= getHighlight(193, 110, 0);
	public static final String AMPLE_COLOR_DEF_LITERALS			= getHighlight(33, 33, 33);
	public static final String AMPLE_COLOR_DEF_NUMBERS			= getHighlight(0, 0, 0);
	public static final String AMPLE_COLOR_DEF_SL_COMMENT		= getHighlight(127, 127, 0);
	public static final String AMPLE_COLOR_DEF_ML_COMMENT		= getHighlight(65, 140, 255);
	public static final String AMPLE_COLOR_DEF_BRACKETS			= getHighlight(0, 0, 0);
	public static final String AMPLE_COLOR_DEF_OTHERS			= getHighlight(0, 0, 0);
	// dark theme
	public static final String AMPLE_COLOR_DDEF_KEYWORDS		= getHighlight(255, 128, 128, BOLD);
	public static final String AMPLE_COLOR_DDEF_PRIMITIVES		= getHighlight(255, 128, 128);
	public static final String AMPLE_COLOR_DDEF_STRINGS			= getHighlight(88, 175, 107);
	public static final String AMPLE_COLOR_DDEF_CHARS			= getHighlight(88, 175, 107);
	public static final String AMPLE_COLOR_DDEF_PROCESSOR		= getHighlight(217, 217, 0);
	public static final String AMPLE_COLOR_DDEF_LITERALS		= getHighlight(255, 255, 255);
	public static final String AMPLE_COLOR_DDEF_NUMBERS			= getHighlight(255, 255, 255);
	public static final String AMPLE_COLOR_DDEF_SL_COMMENT		= getHighlight(69, 186, 128);
	public static final String AMPLE_COLOR_DDEF_ML_COMMENT		= getHighlight(65, 140, 255);
	public static final String AMPLE_COLOR_DDEF_BRACKETS		= getHighlight(255, 255, 255);
	public static final String AMPLE_COLOR_DDEF_OTHERS			= getHighlight(162, 162, 162);
	
	
	
	
	// TODO: Color cache.
	private static Map<ImageDescriptor, Image> cached_images = new HashMap<>();
	public static final ImageDescriptor PROJECT_DECAL			= getResourceImage("icons/ovr16/projectdecal.png");
	public static final ImageDescriptor OUTLINE_BLOB			= getResourceImage("icons/ample_outline_blob_2.png");
	
	public static final ImageDescriptor PROJECT_ICON			= getResourceImage("icons/obj16/project.png");
	public static final ImageDescriptor LIBRARY_ICON			= getResourceImage("icons/obj16/library.png");
	public static final ImageDescriptor FOLDER_ICON				= getResourceImage("icons/obj16/folder.png");
	public static final ImageDescriptor SOURCE_FILE				= getResourceImage("icons/obj16/sourcefile.png");
	public static final ImageDescriptor SOURCE_FILE_DISABLED	= getResourceImage("icons/obj16/sourcefile_disabled.png");
	public static final ImageDescriptor SOURCE_FOLDER			= getResourceImage("icons/obj16/sourcefolder.png");
	
	static void dispose() {
		// We don't need to dispose any of these probably :s
//		for(Image image : cached_images.values()) {
//			image.dispose();
//		}
//		
//		for(Image image : cached_overlay.values()) {
//			image.dispose();
//		}
	}
	
	public static Image getImage(ImageDescriptor descriptor) {
		Image image = cached_images.get(descriptor);
		
		if(image == null || image.isDisposed()) {
			image = descriptor.createImage();
			cached_images.put(descriptor, image);
		}
		
		return image;
	}
	
	private static Map<String, Image> cached_overlay = new HashMap<>();
	public static Image getOverlayImage(ImageDescriptor baseImage, String sharedImage, int quadrant) {
		ImageDescriptor overlayImage = PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(sharedImage);
		if(overlayImage == null) return getImage(baseImage);
		
		String custom_id = baseImage.hashCode() + ":" + sharedImage;
		Image image = cached_overlay.get(custom_id);
		
		if(image == null || image.isDisposed()) {
			DecorationOverlayIcon icon = new DecorationOverlayIcon(
				baseImage,
				overlayImage,
				quadrant
			);
			
			image = icon.createImage();
			cached_overlay.put(custom_id, image);
		}
		
		return image;
	}
	
	private static ImageDescriptor getResourceImage(String path) {
		AmpleSyntaxPlugin plugin = AmpleSyntaxPlugin.getDefault();
		URL url = plugin.getBundle().getResource(path);
		return ImageDescriptor.createFromURL(url);
	}
	
	
	// Default coloring methods
	static String getHighlight(int red, int green, int blue, boolean bold, boolean italic, boolean strikethough, boolean underline) {
		return getHighlight(red, green, blue, 
			(bold ? 1:0) |
			(italic ? 2:0) |
			(strikethough ? 4:0) |
			(underline ? 8:0)
		);
	}
	
	private static String getHighlight(int red, int green, int blue) {
		return getHighlight(red, green, blue, 0);
	}
	
	private static String getHighlight(int red, int green, int blue, int flags) {
		return red + "," + green + "," + blue + "," + flags;
	}
	
	private static String getPref(String id) {
		return PLUGIN_ID + "." + id;
	}
}
