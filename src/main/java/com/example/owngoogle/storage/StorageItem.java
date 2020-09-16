package com.example.owngoogle.storage;

public class StorageItem {

	private final String url;
	private final String description;
	private final String body;

	public StorageItem(String url, String description, String body) {
		this.url = url;
		this.description = description;
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

	public String getBody() {
		return body;
	}

}
