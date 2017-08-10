package com.arun.crawler.processor;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.arun.crawler.DO.ScannerDO;
import com.arun.crawler.utils.HtmlDataPull;
import com.arun.crawler.utils.Utils;

/*
 * Runnable class to cache the Mail URLs from the website
 * and save them in the ScannerDO
 * 
 */

public class LinkCacheService implements Runnable {

	ScannerDO scannerDo = null;
	int monthCounter;
	String baseURL;
	String year;
	String name;

	public LinkCacheService(int monthCounter, ScannerDO scannerDO, String baseURL, String year, String name) {
		// TODO Auto-generated constructor stub
		this.scannerDo = scannerDO;
		this.baseURL = baseURL;
		this.monthCounter = monthCounter;
		this.year = year;
		this.name = name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// change it to 12

		StringBuilder monthUrl = urlForMonth(baseURL, monthCounter, year);
		String monthUrl_base = monthUrl.toString();

		monthUrl.append("thread?0");

		String monthResponse = null;

		try {
			monthResponse = HtmlDataPull.getResponse(monthUrl.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;

		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = builder.parse(new InputSource(new StringReader(monthResponse.toString())));
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList messages = doc.getElementsByTagName("message");

		for (int i = 0; i < messages.getLength(); i++) {

			StringBuilder tempString = new StringBuilder(monthUrl_base);
			String id = messages.item(i).getAttributes().getNamedItem("id").toString().replace("id=", "").replace("\"",
					"");
			String encodedURI = Utils.encodeURI(id);
			String mailRequest = tempString.append(encodedURI).toString();
			scannerDo.addLinksToVisit(mailRequest);
			// monthUrl.append(str)
		}

	}

	private static StringBuilder urlForMonth(String baseURL, int monthID, String year) {

		StringBuilder homeURL = new StringBuilder(baseURL);

		String monthString = (monthID <= 9) ? "0" + monthID : "" + monthID;
		homeURL.append(year + monthString);
		homeURL.append(".mbox/ajax/");

		return homeURL;

	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
