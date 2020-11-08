package plugin.hardcoded.ample.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public abstract class DefaultHandler extends AbstractHandler {
	protected abstract boolean validateEnabled(ISelection selection);
	
	protected static final <T> T getFirstElement(ISelection selection, Class<T> clazz) {
		if(!(selection instanceof IStructuredSelection)) return null;
		
		IStructuredSelection sel = (IStructuredSelection)selection;
		if(sel.size() != 1) return null;
		
		Object object = sel.getFirstElement();
		if(object != null && clazz.isAssignableFrom(object.getClass())) {
			return clazz.cast(object);
		}
		
		return null;
	}
	
	private boolean _validate_enabled(Object object) {
		if(!(object instanceof IEvaluationContext)) return false;
		Object selection = ((IEvaluationContext)object).getVariable("selection");
		if(!(selection instanceof ISelection)) return false;
		return validateEnabled((ISelection)selection);
	}
	
	public final void setEnabled(Object object) {
		setBaseEnabled(_validate_enabled(object));
	}
}
