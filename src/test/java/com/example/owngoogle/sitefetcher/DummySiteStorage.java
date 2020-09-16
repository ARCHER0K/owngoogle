package com.example.owngoogle.sitefetcher;

import com.example.owngoogle.storage.ISiteStorage;
import com.example.owngoogle.storage.StorageItem;
import com.example.owngoogle.storage.StorageResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummySiteStorage implements ISiteStorage {

	private final Map<String, List<StorageItem>> map = new HashMap<>();

	@Override
	public void add(StorageItem item) {
		final List<StorageItem> list = map.getOrDefault(item.getUrl(), new ArrayList<>());
		list.add(item);
		map.put(item.getUrl(), list);
	}

	@Override
	public StorageResult find(String query, int page, String sort) {
		final List<StorageItem> items = map.getOrDefault(query, new ArrayList<>());
		return new StorageResult(items.size(), items);
	}
}
