package com.example.owngoogle.service;

import com.example.owngoogle.sitefetcher.ISiteFetcher;
import com.example.owngoogle.sitefetcher.SiteFetcherFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class IndexServiceImplUnitTest {

	private static final String TEST_URL = "TEST_URL_VALUE";
	private static final Integer TEST_DEEPNESS  = 10;

	private final SiteFetcherFactory siteStorage = mock(SiteFetcherFactory.class);
	private final ISiteFetcher siteFetcher = mock(ISiteFetcher.class);
	private final IIndexValidationService validationService = mock(IIndexValidationService.class);

	private IndexServiceImpl service = new IndexServiceImpl(siteStorage, validationService);

	@Test
	void innerServicesWereCalled() {
		when(siteStorage.getSiteFetcher()).thenReturn(siteFetcher);

		service.index(TEST_URL, TEST_DEEPNESS);
		verify(siteFetcher, times(1)).fetchAndStore(eq(TEST_URL), eq(TEST_DEEPNESS));
		verify(validationService, times(1)).validate(eq(TEST_URL), eq(TEST_DEEPNESS));
	}
}