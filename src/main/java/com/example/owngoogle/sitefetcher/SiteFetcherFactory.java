package com.example.owngoogle.sitefetcher;

import com.example.owngoogle.storage.ISiteStorage;
import org.springframework.stereotype.Component;

@Component
public class SiteFetcherFactory {

	private final ISiteStorage siteStorage;

	public SiteFetcherFactory(ISiteStorage siteStorage) {
		this.siteStorage = siteStorage;
	}

	public ISiteFetcher getSiteFetcher() {
		return new JsoupSiteFetcherImpl(siteStorage);
	}
}
