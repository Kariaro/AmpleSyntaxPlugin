package plugin.hardcoded.ample.decorator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

@Deprecated
public class AmpleDropAdapterAssistant extends CommonDropAdapterAssistant {
	public IStatus handleDrop(CommonDropAdapter aDropAdapter, DropTargetEvent aDropTargetEvent, Object aTarget) {
		return Status.CANCEL_STATUS;
	}
	
	public IStatus validateDrop(Object target, int operation, TransferData transferType) {
		return Status.OK_STATUS;
	}
}
