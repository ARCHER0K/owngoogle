package com.example.owngoogle.sitefetcher;

public class SiteModel {

	private String description;
	private String bodyText;

	public SiteModel(String description, String bodyText) {
		this.description = description;
		this.bodyText = bodyText;
	}

	public String getDescription() {
		return description;
	}

	public String getBodyText() {
		return bodyText;
	}
}
