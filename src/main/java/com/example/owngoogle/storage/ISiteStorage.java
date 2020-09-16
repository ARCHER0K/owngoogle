package com.example.owngoogle.storage;

public interface ISiteStorage {

	void add(StorageItem item);

	StorageResult find(String query, int page, String sort);
}
