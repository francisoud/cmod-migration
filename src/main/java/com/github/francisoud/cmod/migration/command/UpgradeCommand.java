package com.github.francisoud.cmod.migration.command;

import org.apache.commons.codec.digest.DigestUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.*;

import com.github.francisoud.cmod.migration.Version;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UpgradeCommand implements Command {

	private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

	@Override
	public void execute(String folder) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// dbf.setNamespaceAware(true);
		// dbf.setValidating(true);
		// dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			// db.setErrorHandler(new DefaultHandler());

			Document document = db.parse(Paths.get(folder, "changelog.xml").toFile());

			NodeList nodeList = document.getElementsByTagName("include");
			for (int i = 0; i < nodeList.getLength(); i++) {
				final Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					final String mode = node.getAttributes().getNamedItem("mode").getTextContent();
					final String filePath = node.getAttributes().getNamedItem("file").getTextContent();
					final File file = Paths.get(folder, filePath).toFile();
					final String checksum = DigestUtils.sha1Hex(new FileInputStream(file));
					final String author = System.getProperty("user.name");
					final String options = node.getAttributes().getNamedItem("options").getTextContent();
					
					// row = getODChangeLog(where id = i)
					// if ( != checksum.equals(row.getDisplayValue("checksum")) {
					// System.err.println("Error file has been modified since previous migration: " + filePath);
					// System.err.println("Invalid checksum: item n° "+ i + " file computed checksum: " + checksum + " doesn't match previous checksum in od: " + row.getDisplayValue("checksum"));
					// System.exit(-1);
					// }
					
					final String validateCommand = new StringBuilder("arsxml validate -i ").append(file.getAbsolutePath()).toString();
					exec(validateCommand);

					final String arsxmlCommand = new StringBuilder("arsxml ").append(mode).append(" ").append(options)
							.append(" ").append(file.getAbsolutePath()).toString();
					exec(arsxmlCommand);
					
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-hhmmss.S");
					String timestamp = formatter.format(new Date());
					// insert in ODCHANGELOG (i, checksum, filePath, author, timestamp, Version.VERSION)
				}
			}
		} catch (IOException | ParserConfigurationException | SAXException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void exec(String command) throws IOException, InterruptedException {
		System.out.println(command);
		final Runtime runtime = Runtime.getRuntime();
		final Process process = runtime.exec(command);
		int retVal = process.waitFor();
		if (retVal != 0) {
			System.exit(retVal);
		} 

	}
}
