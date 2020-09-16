package com.example.owngoogle.controller;

import com.example.owngoogle.model.ResultModel;
import com.example.owngoogle.model.SearchItemModel;
import com.example.owngoogle.service.ISearchService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class SearchControllerUnitTest {

	private static final String TEST_QUERY_PARAMETER = "TEST_QUERY_VALUE";

	private final ISearchService searchService = mock(ISearchService.class);
	private final SearchController searchController = new SearchController(searchService);

	@Test
	public void returnSearchPageName() {
		final String result = searchController.startPage(null);
		assertEquals("search", result);
	}

	@Test
	public void returnSearchPageOnAction() {
		when(searchService.find(any(), eq(0), eq("rel"))).thenReturn(new ResultModel(0, new ArrayList<>()));
		final ExtendedModelMap model = new ExtendedModelMap();

		final String result = searchController.searchPage(model, null, 1, "rel");
		final List<SearchItemModel> searchResult = new ArrayList<>();
		verify(searchService, times(1)).find(eq(null), eq(0), eq("rel"));
		assertEquals("search", result);
		assertNull(model.get("query"));
		assertEquals(searchResult, model.get("results"));
	}

	@Test
	public void putQueryToModel() {
		when(searchService.find(any(), eq(0), eq("rel"))).thenReturn(new ResultModel(0, new ArrayList<>()));
		final ExtendedModelMap model = new ExtendedModelMap();
		final String result = searchController.searchPage(model, TEST_QUERY_PARAMETER, 1, "rel");
		final List<SearchItemModel> searchResult = new ArrayList<>();
		verify(searchService, times(1)).find(eq(TEST_QUERY_PARAMETER), eq(0), eq("rel"));
		assertEquals("search", result);
		assertEquals(TEST_QUERY_PARAMETER, model.get("query"));
		assertEquals(searchResult, model.get("results"));
	}
}