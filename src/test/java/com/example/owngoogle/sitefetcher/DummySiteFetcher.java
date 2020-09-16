package com.example.owngoogle.sitefetcher;

import com.example.owngoogle.storage.ISiteStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummySiteFetcher extends AbstractSiteFetcher {

	List<String> innerUrls = new ArrayList<>();

	static Map<String, SiteModel> map = new HashMap<>();
	static {
		map.put("http://www.site1.com", new SiteModel("SITE-DESCRIPTION-1", "SITE-BODY-1"));
		map.put("http://www.site2.com", new SiteModel("SITE-DESCRIPTION-2", "SITE-BODY-2"));
		map.put("http://www.site3.com", new SiteModel("SITE-DESCRIPTION-3<a href=\"www.site4.com\"></a>", "SITE-BODY-3"));
		map.put("http://www.site4.com", new SiteModel("SITE-DESCRIPTION-4", "SITE-BODY-4"));
	}

	public DummySiteFetcher(ISiteStorage siteStorage) {
		super(siteStorage);
	}

	public DummySiteFetcher(ISiteStorage siteStorage, List<String> visitedSites) {
		super(siteStorage, visitedSites);
	}

	@Override
	protected SiteModel getSiteData(String url){
		return map.get(url);
	}

	@Override
	protected List<String> getInnerUrls() {
		return innerUrls;
	}

	@Override
	protected ISiteFetcher getInnerSiteFetcher() {
		return new DummySiteFetcher(siteStorage, visitedSites);
	}
}
