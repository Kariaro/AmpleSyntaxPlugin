package plugin.hardcoded.ample.syntax;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.*;

public class AmpleContentAssist implements IContentAssistProcessor {
	private static final char[] AUTO_CHARACTERS = "{[".toCharArray();
	
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int documentOffset) {
		return null;
	}
	
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int documentOffset) {
		return null;
	}
	
	public char[] getCompletionProposalAutoActivationCharacters() {
		return AUTO_CHARACTERS;
	}
	
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}
	
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}
	
	public String getErrorMessage() {
		return "Testing";
	}
}
