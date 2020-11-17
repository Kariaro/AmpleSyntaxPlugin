package plugin.hardcoded.ample.preferences;

import java.util.*;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public class TempPreferenceStore implements IPreferenceStore {
	private final List<IPropertyChangeListener> listeners = new ArrayList<>();
	private final Map<String, Object> props = new HashMap<>();
	private IPreferenceStore def;
	
	public void setPreferenceStore(IPreferenceStore store) {
		this.def = store;
	}
	
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		listeners.add(listener);
	}
	
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		listeners.remove(listener);
	}
	
	public boolean contains(String name) { return props.containsKey(name); }
	
	public void clear() {
		props.clear();
		
		// Force update some values
		firePropertyChangeEvent("\0", null, null);
	}
	
	public void firePropertyChangeEvent(String name, Object oldValue, Object newValue) {
		for(IPropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(this, name, oldValue, newValue));
		}
	}
	
	public boolean getDefaultBoolean(String name) { return def == null ? false:def.getDefaultBoolean(name); }
	public double getDefaultDouble(String name) { return def == null ? 0.0D:def.getDefaultDouble(name); }
	public float getDefaultFloat(String name) { return def == null ? 0.0F:def.getDefaultFloat(name); }
	public int getDefaultInt(String name) { return def == null ? 0:def.getDefaultInt(name); }
	public long getDefaultLong(String name) { return def == null ? 0L:def.getDefaultLong(name); }
	public String getDefaultString(String name) { return def == null ? "":def.getDefaultString(name); }

	public boolean getBoolean(String name) { return tryGet(name, false, Boolean.class); }
	public double getDouble(String name) { return tryGet(name, 0.0D, Double.class); }
	public float getFloat(String name) { return tryGet(name, 0.0F, Float.class); }
	public int getInt(String name) { return tryGet(name, 0, Integer.class); }
	public long getLong(String name) { return tryGet(name, 0L, Long.class); }
	public String getString(String name) { return tryGet(name, "", String.class); }

	@Deprecated public void setToDefault(String name) {}
	@Deprecated public void setDefault(String name, double value) {}
	@Deprecated public void setDefault(String name, float value) {}
	@Deprecated public void setDefault(String name, int value) {}
	@Deprecated public void setDefault(String name, long value) {}
	@Deprecated public void setDefault(String name, String value) {}
	@Deprecated public void setDefault(String name, boolean value) {}
	@Deprecated public void setValue(String name, double value) {}
	@Deprecated public void setValue(String name, float value) {}
	@Deprecated public void setValue(String name, int value) {}
	@Deprecated public void setValue(String name, long value) {}
	@Deprecated public void setValue(String name, String value) {}
	@Deprecated public void setValue(String name, boolean value) {}
	
	public void setObject(String name, Object newValue) {
		Object oldValue = props.put(name, newValue);
		firePropertyChangeEvent(name, oldValue, newValue);
	}

	public void putValue(String name, String value) { props.put(name, value); }
	
	public boolean isDefault(String name) { return false; }
	public boolean needsSaving() { return false; }
	
	private <T> T tryGet(String name, T def, Class<T> clazz) {
		try {
			if(this.def != null && !props.containsKey(name)) {
				return clazz.cast(tryGetDef(name, clazz));
			}
			
			return clazz.cast(props.get(name));
		} catch(ClassCastException e) {
			return def;
		}
	}
	
	private <T> T tryGetDef(String name, Class<T> clazz) {
		if(clazz == Boolean.class) return clazz.cast(def.getBoolean(name));
		if(clazz == Double.class) return clazz.cast(def.getDouble(name));
		if(clazz == Float.class) return clazz.cast(def.getFloat(name));
		if(clazz == Integer.class) return clazz.cast(def.getInt(name));
		if(clazz == Long.class) return clazz.cast(def.getLong(name));
		if(clazz == String.class) return clazz.cast(def.getString(name));
		return null;
	}

	public void removeValue(String id) {
		Object oldValue = props.remove(id);
		firePropertyChangeEvent(id, oldValue, null);
	}
}
