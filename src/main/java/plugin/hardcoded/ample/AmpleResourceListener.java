package plugin.hardcoded.ample;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;

@Deprecated
public class AmpleResourceListener implements IResourceChangeListener {
	private List<Listener> listeners = new ArrayList<>();
	
	public synchronized void resourceChanged(IResourceChangeEvent event) {
		for(Listener l : listeners) {
			if((l.eventMask & event.getType()) != 0)
				l.listener.resourceChanged(event);
		}
	}
	
	public synchronized void addChangeListener(IResourceChangeListener l, int mask) {
		listeners.add(new Listener(l, mask));
	}
	
	public synchronized void removeChangeListener(IResourceChangeListener l) {
		@SuppressWarnings("unlikely-arg-type")
		int index = listeners.indexOf(l);
		
		if(index >= 0) {
			listeners.remove(index);
		}
	}
	
	private class Listener {
		public IResourceChangeListener listener;
		public int eventMask;
		
		public Listener(IResourceChangeListener l, int m) {
			listener = Objects.requireNonNull(l);
			eventMask = m;
		}
		
		public int hashCode() {
			return listener.hashCode();
		}
		
		public boolean equals(Object obj) {
			return listener.equals(obj);
		}
	}
}
