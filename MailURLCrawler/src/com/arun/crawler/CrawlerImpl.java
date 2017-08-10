package com.arun.crawler;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.arun.crawler.DO.ScannerDO;
import com.arun.crawler.appconstants.AppConstants;
import com.arun.crawler.processor.LinkCacheService;
import com.arun.crawler.processor.MailDownloaderService;
import com.arun.crawler.utils.Utils;

public class CrawlerImpl implements Crawler {
	private final static Logger LOGGER = Logger.getLogger(CrawlerImpl.class.getName());

	@Override
	public void ScanURLs(ScannerDO scannerdo) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		String baseUrl = Utils.getURL();
		String year = Utils.getYear();
		int monthCounter = 1;
		ThreadPoolExecutor executorLinks = (ThreadPoolExecutor) Executors.newFixedThreadPool(AppConstants.MAXIMUM_THREADS_CACHELINKS);

		for (; monthCounter <= 12; monthCounter++) { // change it to 12
			LinkCacheService LinkSvcWorker = new LinkCacheService(monthCounter, scannerdo, baseUrl, year,
					String.valueOf(monthCounter));
			System.out.println("A new task has been added : " + LinkSvcWorker.getName());
			executorLinks.execute(LinkSvcWorker);

		}
		executorLinks.shutdown();
		while (!executorLinks.isTerminated()) {
		}

	}

	@Override
	public void DownloadMails(ScannerDO scannerdo) {
		// TODO Auto-generated method stub
		File yearDir = new File(Utils.getYear());
		if (yearDir.exists() && yearDir.isDirectory()) {

			for (File c : yearDir.listFiles())
				c.delete();

		}
		yearDir.mkdir();
		long time1 = System.currentTimeMillis();
		LOGGER.log(Level.INFO, String.valueOf(time1));
		// while (!scannerdo.getLinksToVisit().isEmpty()) {
		// downloadMails(scannerdo.getNextLinkToVisit());
		// }

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(AppConstants.MAXIMUM_THREADS_DOWNLOADMAILS);

		int counter = 1;
		while (scannerdo.getLinksToVisit().size() > 0) {
			MailDownloaderService MailSvcWorker = new MailDownloaderService(scannerdo.getNextLinkToVisit(),
					String.valueOf(counter++));
			System.out.println("A new task has been added : " + MailSvcWorker.getName());
			executor.execute(MailSvcWorker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}

		System.out.println("Finished all threads");

		long time2 = System.currentTimeMillis();
		LOGGER.log(Level.INFO, String.valueOf(time2));

		System.out.println("Time for completion:" + (time2 - time1));
		LOGGER.log(Level.INFO, ("Time for completion:" + (time2 - time1)));

	}

}
