package com.example.owngoogle.controller;

import com.example.owngoogle.service.IIndexService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/index")
public class IndexController {

	private final IIndexService indexService;

	public IndexController(IIndexService indexService) {
		this.indexService = indexService;
	}

	@GetMapping
	public String indexPage(Model model) {
		return "indexing";
	}

	@PostMapping
	public String indexAction(Model model,
	                          @RequestParam(name = "q")  String query,
	                          @RequestParam(required = false, defaultValue = "1")  Integer deepness) {

		indexService.index(query, deepness);

		model.addAttribute("deepness", deepness);
		model.addAttribute("query", query);
		return "indexing";
	}
}
