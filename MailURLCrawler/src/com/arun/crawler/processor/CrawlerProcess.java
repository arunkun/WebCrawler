package com.arun.crawler.processor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.arun.Test;
import com.arun.crawler.Crawler;
import com.arun.crawler.CrawlerImpl;
import com.arun.crawler.DO.ScannerDO;
import com.arun.crawler.appconstants.AppConstants;
import com.arun.crawler.utils.*;
/*
 * This is main entry class. This is a processor class which calls 
 * the actual methods to Cache URLs and Download Mails
 * 
 */
public class CrawlerProcess {

	private final static Logger LOGGER = Logger.getLogger(CrawlerProcess.class.getName());

	public static void main(String[] args) {
		long time5 = System.currentTimeMillis();

		try {
			LOGGER.addHandler(new FileHandler(AppConstants.LOGGER_NAME));
			LOGGER.setUseParentHandlers(false);
		} catch (SecurityException securityExcep) {

			securityExcep.printStackTrace();
		} catch (IOException ioExcep) {

			ioExcep.printStackTrace();
		}

		
		ScannerDO scannerdo = new ScannerDO();
		long time3 = System.currentTimeMillis();
		Crawler crawler = new CrawlerImpl();
		crawler.ScanURLs(scannerdo);

		long time4 = System.currentTimeMillis();

		System.out.println("Time for completion for caching links:" + (time4 - time3));
		LOGGER.log(Level.INFO, ("Time for completion for caching links:" + (time4 - time3)));

		crawler.DownloadMails(scannerdo);
		long time6 = System.currentTimeMillis();

		System.out.println("Total Time:" + (time6 - time5));
		LOGGER.log(Level.INFO, ("Total Time:" + (time6 - time5)));

	}

}
