package com.example.owngoogle.storage;

import java.util.ArrayList;
import java.util.List;

public class StorageResult {

	private int totalHits;
	private List<StorageItem> items;

	public StorageResult(int totalHits, List<StorageItem> items) {
		this.totalHits = totalHits;
		this.items = items;
	}

	public static StorageResult empty() {
		return new StorageResult(0, new ArrayList<>());
	}

	public int getTotalHits() {
		return totalHits;
	}

	public List<StorageItem> getItems() {
		return items;
	}
}
