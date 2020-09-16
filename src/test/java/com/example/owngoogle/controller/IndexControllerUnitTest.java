package com.example.owngoogle.controller;

import com.example.owngoogle.service.IIndexService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;

@RunWith(MockitoJUnitRunner.class)
class IndexControllerUnitTest {

	private static final String TEST_QUERY_PARAMETER = "TEST_QUERY_VALUE";
	private static final Integer TEST_DEEPNESS_PARAMETER = 1;

	private final IIndexService indexService = Mockito.mock(IIndexService.class);
	private final IndexController indexController = new IndexController(indexService);

	@Test
	void returnIndexingPageName() {
		final String result = indexController.indexPage(null);
		Assert.assertEquals("indexing", result);
	}

	@Test
	void returnIndexingPageOnAction() {
		final ExtendedModelMap model = new ExtendedModelMap();
		final String result = indexController.indexAction(model, null, null);
		Mockito.verify(indexService, Mockito.times(1)).index(Mockito.eq(null), Mockito.eq(null));
		Assert.assertEquals("indexing", result);
		Assert.assertNull(model.get("query"));
		Assert.assertNull(model.get("deepness"));
	}

	@Test
	void parametersDefinedToModelOnAction() {
		final ExtendedModelMap model = new ExtendedModelMap();
		final String result = indexController.indexAction(model, TEST_QUERY_PARAMETER, TEST_DEEPNESS_PARAMETER);
		Mockito.verify(indexService, Mockito.times(1)).index(Mockito.eq(TEST_QUERY_PARAMETER), Mockito.eq(TEST_DEEPNESS_PARAMETER));
		Assert.assertEquals("indexing", result);
		Assert.assertEquals(TEST_QUERY_PARAMETER, model.get("query"));
		Assert.assertEquals(TEST_DEEPNESS_PARAMETER, model.get("deepness"));
	}
}