package com.example.owngoogle.sitefetcher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SiteFetcherFactoryUnitTest {

	private final SiteFetcherFactory factory = new SiteFetcherFactory(null);

	@Test
	void name() {
		final ISiteFetcher siteFetcher = factory.getSiteFetcher();
		Assertions.assertTrue(siteFetcher instanceof JsoupSiteFetcherImpl);

	}
}