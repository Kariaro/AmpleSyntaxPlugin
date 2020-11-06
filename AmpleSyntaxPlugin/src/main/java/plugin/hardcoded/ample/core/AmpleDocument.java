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
import org.w3c.dom.*;

public class AmpleDocument {
	protected Document document;
	protected Element root;
	protected Element sources;
	
	protected AmpleDocument() {
		this(null, false);
	}
	
	protected AmpleDocument(IFile file) {
		this(file, file != null && file.exists());
	}
	
	private AmpleDocument(IFile file, boolean hasFile) {
		DocumentBuilder builder;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			if(hasFile) {
				InputStream stream = file.getContents();
				document = builder.parse(stream);
				cleanDocumentFromBlank(null, document.getDocumentElement());
				stream.close();
			} else {
				document = builder.newDocument();
			}
		} catch(Exception e) {
			e.printStackTrace();
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
		
		sources = getElement(root, "source_folders");
		if(sources == null) {
			sources = newElement("source_folders");
			root.appendChild(sources);
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
	
	public void updateSourceFolders(List<IFolder> list) {
		clearElement(sources);
		
		for(IFolder folder : list) {
			Element elm = newElement("entry");
			elm.setAttribute("path", folder.getName());
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
	
	public void save(IFile file) {
		try(FileOutputStream stream = new FileOutputStream(file.getLocation().toOSString())) {
			stream.write(toXMLString().getBytes());
		} catch(TransformerException e) {
		} catch(IOException e) {
			
		}
	}
}
