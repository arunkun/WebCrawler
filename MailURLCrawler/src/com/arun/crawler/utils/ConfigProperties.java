package com.arun.crawler.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.arun.crawler.appconstants.AppConstants;

public class ConfigProperties {
	
	static Properties props = new Properties();
	static InputStream input = null;
	
	static{
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

}
