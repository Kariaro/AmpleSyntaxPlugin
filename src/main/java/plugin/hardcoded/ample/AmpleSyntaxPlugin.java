package plugin.hardcoded.ample;

import java.util.Objects;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import plugin.hardcoded.ample.console.AmpleConsole;

/**
 * A plugin implementing the ample language and syntax highlighting.
 * 
 * @author HardCoded
 */	
public class AmpleSyntaxPlugin extends AbstractUIPlugin {
	private static AmpleSyntaxPlugin plugin;
	
	private IPropertyChangeListener themeChange;
	private IPreferenceStore store;
	
	// @Deprecated
	// public AmpleResourceListener resourceListener = new AmpleResourceListener();
	public AmpleConsole console = new AmpleConsole();
	
	public AmpleSyntaxPlugin() {
		super();
		plugin = this;
	}
	
	/**
	 * Returns a shared instance of this plugin.
	 * @return a shared instance of this plugin
	 */
	public static AmpleSyntaxPlugin getDefault() {
		return plugin;
	}
	
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
//		try {
//			ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		
		AmplePreferences.dispose();
		store.removePropertyChangeListener(themeChange);
	}
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		// ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceListener, 63);
		store = getPreferenceStore();
		
		themeChange = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if(event.getProperty().equals(AmplePreferences.CURRENT_COLOR_THEME)) {
					if(Objects.equals(event.getNewValue(), "dark")) {
						AmplePreferenceInitializer.updateDark(store);
					} else {
						AmplePreferenceInitializer.updateLight(store);
					}
				}
			}
		};
		store.addPropertyChangeListener(themeChange);
	}
}
