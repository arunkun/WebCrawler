package com.arun.crawler.DO;

import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * This class is the Data Object for holding the URLs for the Mails
 */

public class ScannerDO {

	public ScannerDO() {
	}

	public ScannerDO(String linkToCrawl) {
		this.linksToVisit.offer(linkToCrawl);
	}

	private Queue<String> linksToVisit = new ConcurrentLinkedQueue<String>();

	public Queue<String> getLinksToVisit() {
		return linksToVisit;
	}

	// Next link to visit
	public String getNextLinkToVisit() {
		return linksToVisit.poll();
	}

	// Links to visit size
	public int links_to_visit_size() {
		return linksToVisit.size();
	}

	public void addLinksToVisit(String url) {
		linksToVisit.offer(url);
	}

}
