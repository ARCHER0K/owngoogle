package com.example.owngoogle.sitefetcher;

import com.example.owngoogle.storage.ISiteStorage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class JsoupSiteFetcherImpl extends AbstractSiteFetcher {

	private Element siteBody;

	public JsoupSiteFetcherImpl(ISiteStorage siteStorage) {
		super(siteStorage);
	}

	public JsoupSiteFetcherImpl(ISiteStorage siteStorage, List<String> visitedSites) {
		super(siteStorage, visitedSites);
	}

	@Override
	protected SiteModel getSiteData(String url) throws IOException {
		Document document = Jsoup.connect(url).get();
		String description = document.title();
		siteBody = document.body();
		String parsedHtml = htmlToText(siteBody.html());
		return new SiteModel(description, parsedHtml);
	}

	@Override
	protected List<String> getInnerUrls() {
		final Elements aElements = siteBody.select("a[href]");
		return aElements.stream()
				.map(element -> element.attr("href"))
				.filter(link -> !link.startsWith("javascript:void(0)") && !link.startsWith("#"))
				.collect(toList());
	}

	@Override
	protected ISiteFetcher getInnerSiteFetcher() {
		return new JsoupSiteFetcherImpl(siteStorage, visitedSites);
	}

	private String htmlToText(String html) {
		return Jsoup.parse(html).text();
	}
}
