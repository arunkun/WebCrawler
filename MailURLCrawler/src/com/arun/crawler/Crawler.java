package com.arun.crawler;

import com.arun.crawler.DO.ScannerDO;

public interface Crawler {

	public void ScanURLs(ScannerDO scannerdo);

	public void DownloadMails(ScannerDO scannerdo);

}
