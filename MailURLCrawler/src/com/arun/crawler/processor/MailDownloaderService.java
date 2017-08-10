package com.arun.crawler.processor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.arun.crawler.appconstants.AppConstants;
//import com.arun.crawler.Test;
import com.arun.crawler.utils.HtmlDataPull;
import com.arun.crawler.utils.Utils;

/*
 * Runnable class to Download the mails and save it as txt file. 
 * It gets it Mail URL from the ScannerDo
 */

public class MailDownloaderService implements Runnable {
	// ScannerDO scanner=null;
	String name = null;
	String URL = null;

	public MailDownloaderService(String URL, String Name) {
		this.URL = URL;
		this.name = Name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		String res = null;
		// String URL = scanner.getNextLinkToVisit();
		String[] str = URL.split("/");
//		String filename=null;
		String filename = str[str.length - 1];
		filename = (String) filename.subSequence(2, filename.length() - 3);
		try {
			res = HtmlDataPull.getResponse(URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] messageContent = Utils.getmessageContent(res);
		BufferedWriter bufferWriter = null;
		File file = null;
		try {
			try {
				file = new File(Utils.getYear() + "//" + filename + AppConstants.EXTENSION);
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bufferWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		String content = Utils.downloadFile(messageContent, bufferWriter);
		System.out.println(Thread.currentThread().getName() + " (End)");// prints
																		// thread
																		// name

	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
