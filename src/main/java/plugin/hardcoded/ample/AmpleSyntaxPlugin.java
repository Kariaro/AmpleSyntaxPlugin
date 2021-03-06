package plugin.hardcoded.ample;

import java.util.Objects;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * A plugin implementing the ample language and syntax highlighting.
 * 
 * @author HardCoded
 */	
public class AmpleSyntaxPlugin extends AbstractUIPlugin {
	private static AmpleSyntaxPlugin plugin;
	
	private IPropertyChangeListener themeChange;
	private IPreferenceStore store;
	
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
		AmplePreferences.dispose();
		store.removePropertyChangeListener(themeChange);
	}
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
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
