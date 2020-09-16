package com.example.owngoogle.service;

import com.example.owngoogle.model.ResultModel;
import com.example.owngoogle.model.SearchItemModel;
import com.example.owngoogle.storage.ISiteStorage;
import com.example.owngoogle.storage.StorageItem;
import com.example.owngoogle.storage.StorageResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class SearchServiceImplUnitTest {

	private static final String TEST_URL_1  = "TEST_URL_VALUE_1";
	private static final String TEST_DESCRIPTION_1 = "TEST_DESCRIPTION_VALUE_1";
	private static final String TEST_BODY_1 = "TEST_BODY_VALUE_1";

	private static final String TEST_URL_2  = "TEST_URL_VALUE_2";
	private static final String TEST_DESCRIPTION_2 = "TEST_DESCRIPTION_VALUE_2";
	private static final String TEST_BODY_2 = "TEST_BODY_VALUE_2";

	private final ISiteStorage siteStorage = mock(ISiteStorage.class);
	private final ISearchService searchService = new SearchServiceImpl(siteStorage);

	@Test
	public void returnEmptyListWhenStorageDoesNotReturnData() {
		when(siteStorage.find(null, 0, "rel")).thenReturn(new StorageResult(0, new ArrayList<>()));
		final ResultModel results = searchService.find(null, 0, "rel");
		Assertions.assertEquals(0, results.getResults().size());
	}

	@Test
	public void returnTwoResultsWhenStorageReturnsTwoRecords() {
		final List<StorageItem> storageItems = new ArrayList<>();
		StorageItem item1 = new StorageItem(TEST_URL_1, TEST_DESCRIPTION_1, TEST_BODY_1);
		StorageItem item2 = new StorageItem(TEST_URL_2, TEST_DESCRIPTION_2, TEST_BODY_2);
		storageItems.add(item1);
		storageItems.add(item2);
		when(siteStorage.find(null, 0, "rel")).thenReturn(new StorageResult(2, storageItems));
		final ResultModel results = searchService.find(null, 0, "rel");
		Assertions.assertEquals(2, results.getResults().size());
		final SearchItemModel resultModel1 = results.getResults().get(0);
		Assertions.assertEquals(TEST_URL_1, resultModel1.getLink());
		Assertions.assertEquals(TEST_DESCRIPTION_1, resultModel1.getDescription());
		Assertions.assertEquals(TEST_BODY_1, resultModel1.getText());
		final SearchItemModel resultModel2 = results.getResults().get(1);
		Assertions.assertEquals(TEST_URL_2, resultModel2.getLink());
		Assertions.assertEquals(TEST_DESCRIPTION_2, resultModel2.getDescription());
		Assertions.assertEquals(TEST_BODY_2, resultModel2.getText());
	}
}