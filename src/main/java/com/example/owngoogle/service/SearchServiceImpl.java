package com.example.owngoogle.service;

import com.example.owngoogle.model.ResultModel;
import com.example.owngoogle.storage.ISiteStorage;
import com.example.owngoogle.storage.StorageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements ISearchService {

	private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);

	private final ISiteStorage siteStorage;

	public SearchServiceImpl(ISiteStorage siteStorage) {
		this.siteStorage = siteStorage;
	}

	@Override
	public ResultModel find(String query, int page, String sort) {

		log.info("Search with query: {}, page: {}, sort: {}", query, page, sort);
		final StorageResult storageResult = siteStorage.find(query, page, sort);
		log.info("Search with query {} returns {} results", query, storageResult.getItems().size());

		return ResultModelMapper.toResultModel(storageResult, query);
	}
}
