package com.example.owngoogle.controller;

import com.example.owngoogle.service.IIndexService;
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

		final MvcResult mvcResult = mvc.perform(get("/search?q=www.site1.com"))
				.andExpect(status().isOk())
				.andReturn();
	}

}
