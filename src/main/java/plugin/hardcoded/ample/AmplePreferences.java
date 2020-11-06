package plugin.hardcoded.ample;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class AmplePreferences {
	public static final String MATCHING_BRACKETS				= ".matching_brackets";
	public static final String MATCHING_BRACKETS_COLOR			= ".matching_brackets_color";
	
	// TODO: Syntax colors and preference pages.
	// TODO: Color cache.
	
	private static Map<ImageDescriptor, Image> cached_images = new HashMap<>();
	public static final ImageDescriptor AMPLE_PROJECT_ICON		= getResourceImage("icons/ample_projectdecal.png");
	public static final ImageDescriptor AMPLE_EMPTY_FILE_ICON	= getResourceImage("icons/ample_file_empty.png");
	public static final ImageDescriptor AMPLE_OUTLINE_BLOB		= getResourceImage("icons/ample_outline_blob.png");
	public static final ImageDescriptor AMPLE_SOURCE_FOLDER		= getResourceImage("icons/ample_sourcefolder.png");
	public static final ImageDescriptor AMPLE_SOURCE_PACKAGE	= getResourceImage("icons/ample_sourcepackage.png");
	
	static void dispose() {
		for(Image image : cached_images.values()) {
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
	
	private static ImageDescriptor getResourceImage(String path) {
		AmpleSyntaxPlugin plugin = AmpleSyntaxPlugin.getDefault();
		URL url = plugin.getBundle().getResource(path);
		return ImageDescriptor.createFromURL(url);
	}
}
