package com.example.owngoogle.controller;

import com.example.owngoogle.model.ResultModel;
import com.example.owngoogle.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.owngoogle.Constants.SORTING_RELEVANCE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class SearchController {

	private final ISearchService service;

	public SearchController(ISearchService service) {
		this.service = service;
	}

	@GetMapping(path = "/")
	public String startPage(Model model) {
		model.addAttribute("sort", SORTING_RELEVANCE);
		return "search";
	}

	@RequestMapping(method = {POST, GET}, path = "/search")
	public String searchPage(Model model,
	                         @RequestParam(name = "q")  String query,
	                         @RequestParam(name = "p", required = false, defaultValue = "1")  Integer page,
	                         @RequestParam(name = "s", required = false, defaultValue = SORTING_RELEVANCE)  String sort) {

		ResultModel results = service.find(query, page - 1, sort);
		model.addAttribute("results", results.getResults());
		model.addAttribute("totalHits", results.getTotalHits());
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		model.addAttribute("sort", sort);
		return "search";
	}
}
