package com.example.owngoogle.controller;

import com.example.owngoogle.model.SearchItemModel;
import com.example.owngoogle.service.IIndexService;
import com.example.owngoogle.storage.ISiteStorage;
import com.example.owngoogle.storage.StorageItem;
import com.example.owngoogle.storage.StorageResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SearchController.class)
@ContextConfiguration(classes = {ControllersTestConfig.class})
public class SearchControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean private IIndexService iIndexService;
	@MockBean private ISiteStorage siteStorage;

	@Test
	public void getStartPage() throws Exception {

		final MvcResult mvcResult = mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andReturn();
		Assertions.assertEquals("search", mvcResult.getModelAndView().getViewName());
	}

	@Test
	public void getStartPageViaPost() throws Exception {

		final MvcResult mvcResult = mvc.perform(post("/"))
				.andExpect(status().isMethodNotAllowed())
				.andReturn();
	}

	@Test
	public void searchWithoutQuery() throws Exception {

		final MvcResult mvcResult = mvc.perform(get("/search"))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void searchWithoutResults() throws Exception {

		final String query = "www.site.com";
		final MvcResult mvcResult = mvc.perform(get("/search?q=" + query))
				.andExpect(status().isOk())
				.andReturn();
		Assertions.assertEquals("search", mvcResult.getModelAndView().getViewName());
		final Map<String, Object> model = mvcResult.getModelAndView().getModel();
		Assertions.assertEquals(new ArrayList<>(), model.get("results"));
		Assertions.assertEquals(0, model.get("totalHits"));
		Assertions.assertEquals(1, model.get("page"));
		Assertions.assertEquals(query, model.get("query"));
		Assertions.assertEquals("rel", model.get("sort"));
	}

	@Test
	public void searchWithOneResults() throws Exception {
		final String query = "www.site1.com";
		List<StorageItem> items = new ArrayList<>();
		items.add(new StorageItem("www.site1.com", "-site-description-", "-site-body-"));
		StorageResult storageResult = new StorageResult(1, items);
		when(siteStorage.find(eq(query), eq(0), eq("rel"))).thenReturn(storageResult);
		final MvcResult mvcResult = mvc.perform(get("/search?q=" + query))
				.andExpect(status().isOk())
				.andReturn();

		Assertions.assertEquals("search", mvcResult.getModelAndView().getViewName());
		final Map<String, Object> model = mvcResult.getModelAndView().getModel();
		List<SearchItemModel> actualResult = (List<SearchItemModel>) model.get("results");

		Assertions.assertEquals(items.get(0).getUrl(), actualResult.get(0).getLink());
		Assertions.assertEquals(items.get(0).getDescription(), actualResult.get(0).getDescription());
		Assertions.assertEquals(items.get(0).getBody(), actualResult.get(0).getText());
		Assertions.assertEquals(1, model.get("totalHits"));
		Assertions.assertEquals(1, model.get("page"));
		Assertions.assertEquals(query, model.get("query"));
		Assertions.assertEquals("rel", model.get("sort"));
	}
}
