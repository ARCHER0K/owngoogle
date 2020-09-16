package com.example.owngoogle.model;

public class SearchItemModel {

	private final String link;
	private final String description;
	private final String text;

	public SearchItemModel(String link, String description, String text) {
		this.link = link;
		this.description = description;
		this.text = text;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}

	public String getText() {
		return text;
	}
}
