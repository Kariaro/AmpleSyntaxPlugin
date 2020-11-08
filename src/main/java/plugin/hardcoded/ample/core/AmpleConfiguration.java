package plugin.hardcoded.ample.core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.*;

import plugin.hardcoded.ample.AmpleLogger;

public class AmpleConfiguration {
	protected final AmpleProject project;
	protected final Document document;
	protected Element root;
	protected Element sources;
	protected Element libraries;
	private final IFile file;
	
	protected AmpleConfiguration(AmpleProject project, String path) {
		Assert.isNotNull(project);
		Assert.isNotNull(path);
		this.project = project;
		
		DocumentBuilder builder;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch(ParserConfigurationException e) {
			AmpleLogger.log(e);
			throw new IllegalStateException();
		}
		
		file = project.getProject().getFile(path);
		
		if(file.exists()) {
			Document tmp = null;
			try {
				InputStream stream = file.getContents();
				tmp = builder.parse(stream);
				cleanDocumentFromBlank(null, tmp.getDocumentElement());
				stream.close();
			} catch(Exception e) {
				AmpleLogger.log(e);
				tmp = builder.newDocument();
			}
			
			document = tmp;
		} else {
			document = builder.newDocument();
		}
		
		init();
	}
	
	private void cleanDocumentFromBlank(Node parent, Node node) {
		if(node.getNodeType() == Node.TEXT_NODE) {
			String text = node.getTextContent();
			if(text.isBlank()) {
				if(parent != null) parent.removeChild(node);
			}
		} else if(node.getNodeType() == Node.ELEMENT_NODE) {
			NodeList list = node.getChildNodes();
			
			for(int i = 0; i < list.getLength(); i++) {
				Node elm = list.item(i);
				
				int last = list.getLength();
				cleanDocumentFromBlank(node, elm);
				if(last != list.getLength()) {
					i--;
				}
			}
		}
	}
	
	// Make sure that we have some important elements inside the xml file
	private void init() {
		root = getElement(document, "project");
		if(root == null) {
			root = newElement("project");
			document.appendChild(root);
		}
		
		sources = getElement(root, "sources");
		if(sources == null) {
			sources = newElement("sources");
			root.appendChild(sources);
		}
		
		libraries = getElement(root, "libraries");
		if(libraries == null) {
			libraries = newElement("libraries");
			root.appendChild(libraries);
		}
	}

	public String toXMLString() throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer tr = tf.newTransformer();
		tr.setOutputProperty(OutputKeys.INDENT, "yes");
		tr.setOutputProperty(OutputKeys.METHOD, "xml");
		
		StringWriter writer = new StringWriter();
		tr.transform(new DOMSource(document), new StreamResult(writer));
		return writer.getBuffer().toString();
	}

	public Element newElement(String name) {
		return document.createElement(name);
	}
	
	private void clearElement(Node parent) {
		Node node;
		
		while((node = parent.getFirstChild()) != null) {
			parent.removeChild(node);
		}
	}
	
	public List<String> getSourceFolders() {
		List<String> list = new ArrayList<>();
		
		NodeList node_list = sources.getChildNodes();
		for(int i = 0; i < node_list.getLength(); i++) {
			Node node = node_list.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				list.add(((Element)node).getAttribute("path"));
			}
		}
		
		return list;
	}
	
	// TODO: Don't mix match IFolder and String!
	public void updateSourceFolders(List<IFolder> list) {
		clearElement(sources);
		
		for(IFolder folder : list) {
			Element elm = newElement("entry");
			elm.setAttribute("path", folder.getProjectRelativePath().toString());
			sources.appendChild(elm);
		}
	}
	
	protected Element getElement(Object o, String name) {
		NodeList list;
		if(o instanceof Document) {
			list = ((Document)o).getElementsByTagName(name);
		} else if(o instanceof Element) {
			list = ((Element)o).getElementsByTagName(name);
		} else return null;
		
		if(list.getLength() < 1) return null;
		return (Element)list.item(0);
	}
	
	public void save() {
		try(FileOutputStream stream = new FileOutputStream(file.getLocation().toOSString())) {
			stream.write(toXMLString().getBytes());
		} catch(TransformerException e) {
			AmpleLogger.log(e);
		} catch(IOException e) {
			AmpleLogger.log(e);
		}
	}

	protected void remove() {
		try {
			file.delete(true, null);
		} catch(CoreException e) {
			AmpleLogger.log(e);
		}
	}
}
