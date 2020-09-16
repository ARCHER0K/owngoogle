package com.example.owngoogle.sitefetcher;

import com.example.owngoogle.storage.ISiteStorage;
import com.example.owngoogle.storage.StorageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSiteFetcher implements ISiteFetcher {

	protected static final Logger log = LoggerFactory.getLogger(AbstractSiteFetcher.class);

	protected final ISiteStorage siteStorage;
	protected List<String> visitedSites = new ArrayList<>();

	public AbstractSiteFetcher(ISiteStorage siteStorage) {
		this.siteStorage = siteStorage;
	}

	public AbstractSiteFetcher(ISiteStorage siteStorage, List<String> visitedSites) {
		this.siteStorage = siteStorage;
		this.visitedSites = visitedSites;
	}

	protected abstract SiteModel getSiteData(String normalizedUrl) throws IOException;
	protected abstract List<String> getInnerUrls();
	protected abstract ISiteFetcher getInnerSiteFetcher();

	public void fetchAndStore(String url, int deepness) {
		log.info("Fetcher:: Fetching site by URL: {}", url);
		final String normalizedUrl = normalizeUrl(url);
		if (visitedSites.contains(normalizedUrl)) {
			log.info("Fetcher:: URL {} is already scanned in this request", url);
			return;
		}
		try {
			final SiteModel siteData = getSiteData(normalizedUrl);

			siteStorage.add(new StorageItem(url, siteData.getDescription(), siteData.getBodyText()));
			deepness--;
			visitedSites.add(normalizedUrl);

			if (deepness == 0) {
				return;
			}
			handleInnerUrls(deepness);
		} catch (SSLHandshakeException e) {
			log.error("Fetcher:: Impossible to handle SSL connection");
		} catch (IOException e) {
			String errorMessage = String.format("Fetcher:: Impossible to fetch site %s", url);
			log.error(errorMessage, e);
		}
	}

	private void handleInnerUrls(int deepness) {
		final List<String> innerUrls = getInnerUrls();
		log.info("Fetcher:: {} inner URLS were found", innerUrls.size());
		innerUrls.forEach(innerUrl -> getInnerSiteFetcher().fetchAndStore(innerUrl, deepness));
	}

	private String normalizeUrl(String url) {
		url = url.replace("https://", "http://");
		url = url.startsWith("//") ? url.replaceFirst("//", "") : url;
		url = url.endsWith("/") ? url.substring(0, url.length()-1) : url;
		return url.startsWith("http://") ? url : "http://" + url;
	}
}
