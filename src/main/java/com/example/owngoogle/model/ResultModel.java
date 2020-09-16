package com.example.owngoogle.model;

import java.util.List;

public class ResultModel {

	private int totalHits;
	private List<SearchItemModel> results;

	public ResultModel(int totalHits, List<SearchItemModel> results) {
		this.totalHits = totalHits;
		this.results = results;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public List<SearchItemModel> getResults() {
		return results;
	}
}
