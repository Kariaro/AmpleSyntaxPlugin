package plugin.hardcoded.ample;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class AmplePreferences {
	public static final String MATCHING_BRACKETS				= ".matching_brackets";
	public static final String MATCHING_BRACKETS_COLOR			= ".matching_brackets_color";
	
	// TODO: Syntax colors and preference pages.
	// TODO: Color cache.
	
	private static Map<ImageDescriptor, Image> cached_images = new HashMap<>();
	public static final ImageDescriptor AMPLE_PROJECT_DECAL		= getResourceImage("icons/ample_projectdecal.png");
	public static final ImageDescriptor AMPLE_EMPTY_FILE_ICON	= getResourceImage("icons/ample_file_empty.png");
	public static final ImageDescriptor AMPLE_OUTLINE_BLOB		= getResourceImage("icons/ample_outline_blob_2.png");
	
	public static final ImageDescriptor AMPLE_PROJECT_ICON		= getResourceImage("icons/ample_projecticon.png");
	public static final ImageDescriptor AMPLE_LIBRARY_ICON		= getResourceImage("icons/ample_libraryicon.png");
	public static final ImageDescriptor AMPLE_SOURCE_FILE		= getResourceImage("icons/ample_sourcefile.png");
	public static final ImageDescriptor AMPLE_SOURCE_FOLDER		= getResourceImage("icons/ample_sourcefolder.png");
	public static final ImageDescriptor AMPLE_SOURCE_PACKAGE	= getResourceImage("icons/ample_sourcepackage.png");
	
	static void dispose() {
		for(Image image : cached_images.values()) {
			image.dispose();
		}
		
		for(Image image : cached_overlay.values()) {
			image.dispose();
		}
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
		System.out.println("???? " + custom_id);
		
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
}
