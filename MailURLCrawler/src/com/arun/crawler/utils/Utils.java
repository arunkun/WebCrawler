package com.arun.crawler.utils;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.arun.crawler.appconstants.AppConstants;

public class Utils {
	static Properties props=null;
	static InputStream input=null;

	static{

		 props = new Properties();

		try {
			input = new FileInputStream(AppConstants.CONFIGURATION_FILE_NAME);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			props.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	

	
	}
	
	public static String getURL(){
		return props.getProperty(AppConstants.URL);
	}
	
	public static String getYear(){
		return props.getProperty(AppConstants.YEAR);
	}
	
	static String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	public static Document stringToXmlDocument(String xmlString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String encodeURI(String uri) {
		String result;
		try {
			result = URLEncoder.encode(uri, AppConstants.CHAR_SET);

		} catch (UnsupportedEncodingException e) {
			result = uri;
		}

		return result;
	}

	public static String toMonthName(int monthId) {
		return months[monthId - 1];
	}

	public static int toMonthId(String monthName) {

		for (int i = 0; i < months.length; i++) {
			if (months[i].equals(monthName))
				return ++i;
		}
		return -1;
	}
	
	 public static String[] getmessageContent(String mailResponse) {

			String[] messageContent = new String[4];

			Document mail = Utils.stringToXmlDocument(mailResponse);

			NodeList mailChildren = mail.getFirstChild().getChildNodes();

			messageContent[0] = mailChildren.item(1).getTextContent(); // fromContent
			messageContent[1] = mailChildren.item(3).getTextContent(); // subject
			messageContent[2] = mailChildren.item(5).getTextContent(); // dateTime
			messageContent[3] = mailChildren.item(7).getTextContent(); // contents

			return messageContent;

		}
	 
	 public static String downloadFile(String[] messageContents, 
				BufferedWriter bufferedWriter) {

			try {

				
//				zipOutStream.putNextEntry(new ZipEntry(zipEntryName));

				bufferedWriter.write("From:");
				bufferedWriter.newLine();
				bufferedWriter.write("    " + HtmlDataTranformer.replaceHtmlEntities(messageContents[0]));
				bufferedWriter.newLine();

				bufferedWriter.write("Subject:");
				bufferedWriter.newLine();
				bufferedWriter.write("    " + messageContents[1]);
				bufferedWriter.newLine();

				bufferedWriter.write("Date And Time:");
				bufferedWriter.newLine();
				bufferedWriter.write("    " + messageContents[2]);
				bufferedWriter.newLine();

				bufferedWriter.write("Contents:");
				bufferedWriter.newLine();
				bufferedWriter.write("    " + HtmlDataTranformer.replaceHtmlEntities(messageContents[3]));
				bufferedWriter.newLine();

				bufferedWriter.flush();
//				zipOutStream.closeEntry();

			} catch (IOException ioExcep) {
				
				ioExcep.printStackTrace();
			}

			return bufferedWriter.toString();
		}

}
