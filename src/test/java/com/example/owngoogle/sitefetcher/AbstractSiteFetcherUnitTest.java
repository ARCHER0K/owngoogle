package com.example.owngoogle.sitefetcher;

import com.example.owngoogle.storage.ISiteStorage;
import com.example.owngoogle.storage.StorageItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class AbstractSiteFetcherUnitTest {

	ISiteStorage siteStorage = mock(ISiteStorage.class);

	@Test
	void fetchOneSite() {
		final String url = "www.site1.com";

		AbstractSiteFetcher dummySiteFetcher = new DummySiteFetcher(siteStorage);
		dummySiteFetcher.fetchAndStore(url, 1);

		verify(siteStorage, times(1)).add(Mockito.any(StorageItem.class));
	}

	@Test
	void fetchTwoSitesWithDeepness2() {
		final String url = "www.site3.com";

		AbstractSiteFetcher dummySiteFetcher = new DummySiteFetcher(siteStorage);
		dummySiteFetcher.getInnerUrls().add("www.site4.com");
		dummySiteFetcher.fetchAndStore(url, 2);

		verify(siteStorage, times(2)).add(Mockito.any(StorageItem.class));
	}

	@Test
	void fetchOneSitesWithDeepness1() {
		final String url = "www.site3.com";

		AbstractSiteFetcher dummySiteFetcher = new DummySiteFetcher(siteStorage);
		dummySiteFetcher.getInnerUrls().add("www.site4.com");
		dummySiteFetcher.fetchAndStore(url, 1);

		verify(siteStorage, times(1)).add(Mockito.any(StorageItem.class));
	}

	@Test
	void dontTryToFetchSameSiteTwiceForOneRequest() {
		final String url = "www.site3.com";

		AbstractSiteFetcher dummySiteFetcher = new DummySiteFetcher(siteStorage);
		dummySiteFetcher.getInnerUrls().add("www.site3.com");
		dummySiteFetcher.fetchAndStore(url, 2);

		verify(siteStorage, times(1)).add(Mockito.any(StorageItem.class));
	}

}