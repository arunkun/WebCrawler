package com.arun;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
//import org.jsoup.nodes.Document;

import com.arun.crawler.*;
import com.arun.crawler.utils.HtmlManipulator;
import com.arun.crawler.utils.Utils;

//import webcrawler.Utility;


public class Test {
	static String baseUrl=null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		bufferedWriter = new BufferedWriter(new OutputStreamWriter(zos, "UTF-8"));

		 baseUrl = "http://mail-archives.apache.org/mod_mbox/maven-users/";
		StringBuilder monthUrl=new StringBuilder();
		monthUrl.append("http://mail-archives.apache.org/mod_mbox/maven-users/201506.mbox/ajax");
		String monthUrl_base = monthUrl.toString();

		monthUrl.append("/thread?0");
		try {
			String response=  getResponse(monthUrl.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static public String getResponse(String requestString) throws IOException {

		URL requestUrl = new URL(requestString);
		HttpURLConnection httpConnection = null;
		StringBuilder responseBuffer = new StringBuilder();
		StringBuilder responseBuffer1 = new StringBuilder();

		InputStreamReader inputStream = null;
	
		
		httpConnection = (HttpURLConnection) requestUrl.openConnection();		
		
		if (httpConnection != null)
			httpConnection.setReadTimeout(6 * 1000);/* Timeout after 6 seconds */
		if (httpConnection != null && httpConnection.getInputStream() != null) {
			inputStream = new InputStreamReader(httpConnection.getInputStream(), Charset.defaultCharset());
			BufferedReader bufferedReader = new BufferedReader(inputStream);
			if (bufferedReader != null) {
				int cp;
				while ((cp = bufferedReader.read()) != -1) {
					responseBuffer.append((char) cp);
				}
				bufferedReader.close();
			}
		}
		
		if(inputStream != null)
			inputStream.close();
//		System.out.println(responseBuffer.toString());
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(responseBuffer.toString())));
//			String str = doc.getDocumentElement().getAttribute("index");				
			NodeList messages = doc.getElementsByTagName("message");

			System.out.println(doc.toString());
			for(int i=0;i<messages.getLength();i++){
			System.out.println("Test"+messages.item(i).getAttributes().getNamedItem("id"));
			String resultString = "";
//			System.out.println("connecting to mail URL ");
			String URL=baseUrl+"201506.mbox/"+messages.item(i).getAttributes().getNamedItem("id").getNodeValue();
//			int statusCode = connectURL(URL);
//			if (statusCode == 200) {
////				logger.info("connection successuful , downloading Document from   "+URL);
//				org.jsoup.nodes.Document doc2 = (org.jsoup.nodes.Document) Jsoup.connect(URL).get();
//				resultString = doc2.toString();
//				System.out.println("MailContent");
//				System.out.println(resultString);
//
//			}
			
			URL requestUrl1 = new URL(URL);
			HttpURLConnection httpConnection1 = null;
			 responseBuffer1 = new StringBuilder();
			InputStreamReader inputStream1 = null;
		
			
			httpConnection1 = (HttpURLConnection) requestUrl1.openConnection();		
			
			if (httpConnection1 != null)
				httpConnection1.setReadTimeout(6 * 1000);/* Timeout after 6 seconds */
			if (httpConnection1 != null && httpConnection1.getInputStream() != null) {
				inputStream1 = new InputStreamReader(httpConnection1.getInputStream(), Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(inputStream1);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						responseBuffer1.append((char) cp);
					}
					bufferedReader.close();
				}
			}
			
			if(inputStream1 != null)
				inputStream1.close();
//			System.out.println(responseBuffer1.toString());
//			return responseBuffer.toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str[]=getmessageContent(responseBuffer1.toString());
		System.out.println(str);

		return responseBuffer.toString();

	}
	public static int connectURL(String webURL) throws IOException {
		Connection.Response response = Jsoup
				.connect(webURL)
				.userAgent(
						"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
						.timeout(10000).execute();

		int statusCode = response.statusCode();
		return statusCode;
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

			
//			zipOutStream.putNextEntry(new ZipEntry(zipEntryName));

			bufferedWriter.write("From:");
			bufferedWriter.newLine();
			bufferedWriter.write("    " + HtmlManipulator.replaceHtmlEntities(messageContents[0]));
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
			bufferedWriter.write("    " + HtmlManipulator.replaceHtmlEntities(messageContents[3]));
			bufferedWriter.newLine();

			bufferedWriter.flush();
//			zipOutStream.closeEntry();

		} catch (IOException ioExcep) {
			
			ioExcep.printStackTrace();
		}

		return bufferedWriter.toString();
	}


}
