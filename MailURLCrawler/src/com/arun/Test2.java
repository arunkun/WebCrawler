package com.arun;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.ZipOutputStream;

import com.arun.crawler.utils.*;

//import webcrawler.TransmitData;
 

public class Test2 {
	public static void main(String[] args) throws IOException{
		String res=null;
		String URL = "http://mail-archives.apache.org/mod_mbox/maven-users/201512.mbox/ajax/%3C20151229162618.43BF718146%40minotaur.apache.org%3E";
		try {
			res=TransmitData.getResponse(URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] messageContent = Test.getmessageContent(res);
		BufferedWriter bufferWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("200.txt"), "UTF-8"));
//
		String content = Test.downloadFile(messageContent,  bufferWriter);
//		System.out.println(content);
//		
//		
//		String mailResponse = TransmitData.getResponse(mailRequest);
//
//		String[] messageContent = getmessageContent(mailResponse);
//
//		String zipEntryName = "" + monthCounter + "/" + messageContent[2] + ".txt";
//		if (zipEntryName.equals(prevFileName)) {
//			zipEntryName = "" + monthCounter + "/" + messageContent[2] + "(" + prevFilecount + ")" + ".txt";
//			prevFilecount++;
//		} else {
//			prevFileName = zipEntryName;
//			prevFilecount = 1;
//		}
//
//		bufferedWriter = downloadFile(messageContent, zipOutStream, zipEntryName, bufferedWriter);

					


	}
}
