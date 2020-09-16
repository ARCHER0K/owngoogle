package com.example.owngoogle.service;

import com.example.owngoogle.sitefetcher.ISiteFetcher;
import com.example.owngoogle.sitefetcher.SiteFetcherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IIndexService {

	private static final Logger log = LoggerFactory.getLogger(IndexServiceImpl.class);

	private final SiteFetcherFactory fetcherFactory;
	private final IIndexValidationService validationService;

	public IndexServiceImpl(SiteFetcherFactory fetcherFactory, IIndexValidationService validationService) {
		this.fetcherFactory = fetcherFactory;
		this.validationService = validationService;
	}

	@Override
	public void index(String url, Integer deepness) {
		log.info("Start indexing for URL: {}, deepness: {}", url, deepness);
		validationService.validate(url, deepness);
		Thread thread = new Thread(() -> {
			ISiteFetcher siteFetcher = fetcherFactory.getSiteFetcher();
			siteFetcher.fetchAndStore(url, deepness);
		});
		thread.start();
	}
}
