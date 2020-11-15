package plugin.hardcoded.ample;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
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
	
	@Deprecated
	public AmpleResourceListener resourceListener = new AmpleResourceListener();
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
		try {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
		} catch(Exception e) {
			e.printStackTrace();
		}
		InstanceScope.INSTANCE.getNode("plugin.hardcoded.ample.preferences");
		
		AmplePreferences.dispose();
	}
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceListener, 63);
	}

	public static IPreferenceStore getPrefereceStore() {
		return getDefault().getPreferenceStore();
	}
}
