package com.example.owngoogle.controller;

import com.example.owngoogle.OwnGoogleApplication;
import com.example.owngoogle.service.ISearchService;
import com.example.owngoogle.service.SearchServiceImpl;
import com.example.owngoogle.sitefetcher.DummySiteFetcher;
import com.example.owngoogle.sitefetcher.DummySiteStorage;
import com.example.owngoogle.sitefetcher.JsoupSiteFetcherImpl;
import com.example.owngoogle.sitefetcher.SiteFetcherFactory;
import com.example.owngoogle.storage.SiteStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = IndexController.class)
@ContextConfiguration(classes = {ControllersTestConfig.class})
public class IndexControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean private SiteFetcherFactory siteFetcherFactory;
	@MockBean private SearchServiceImpl searchService;

	@BeforeEach
	void setUp() {
		Mockito.when(siteFetcherFactory.getSiteFetcher()).thenReturn(new DummySiteFetcher(new DummySiteStorage()));
	}

	@Test
	public void getIndexPage() throws Exception {

		final MvcResult mvcResult = mvc.perform(get("/index"))
				.andExpect(status().isOk())
				.andReturn();
		Assertions.assertEquals("indexing", mvcResult.getModelAndView().getViewName());
	}

	@Test
	public void indexActionWithoutRequiredParameter() throws Exception {

		mvc.perform(post("/index"))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void indexActionWithIncorrectUrlParameter() throws Exception {

		mvc.perform(post("/index?q=wwwwwww"))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void indexActionWithCorrectUrlParameter() throws Exception {

		final String query = "www.site1.com";
		final MvcResult mvcResult = mvc.perform(post("/index?q=" + query))
				.andExpect(status().isOk())
				.andReturn();
		final Map<String, Object> model = mvcResult.getModelAndView().getModel();

		Assertions.assertEquals(query, model.get("query"));
		Assertions.assertEquals(1, model.get("deepness"));

	}
}
