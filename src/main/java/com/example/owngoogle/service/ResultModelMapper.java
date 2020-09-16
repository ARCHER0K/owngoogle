package com.example.owngoogle.service;

import com.example.owngoogle.model.ResultModel;
import com.example.owngoogle.model.SearchItemModel;
import com.example.owngoogle.storage.StorageResult;
import com.example.owngoogle.utils.TextHandler;

import java.util.List;

import static com.example.owngoogle.Constants.PLAIN_TEXT_LIMIT;
import static java.util.stream.Collectors.toList;

public class ResultModelMapper {

	private static final TextHandler textHandler = new TextHandler(PLAIN_TEXT_LIMIT);

	public static ResultModel toResultModel(StorageResult storageResult, String query) {

		final List<SearchItemModel> searchResultItems = storageResult.getItems().stream()
				.map(item -> new SearchItemModel(item.getUrl(), item.getDescription(), textHandler.prepareText(item.getBody(), query)))
				.collect(toList());

		return new ResultModel(storageResult.getTotalHits(), searchResultItems);
	}
}
