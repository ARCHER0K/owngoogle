package com.example.owngoogle.storage;

import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.search.TopScoreDocCollector;

import static com.example.owngoogle.Constants.*;
import static org.apache.lucene.search.SortField.Type.STRING;

public class CollectorFactory {

	static TopDocsCollector<? extends ScoreDoc> getTopDocsCollector(int page, String sorting) {
		TopDocsCollector<? extends ScoreDoc> collector;
		if (SORTING_ALPHABETICALLY.equalsIgnoreCase(sorting)) {
			final Sort sort = new Sort(new SortField(SITE_STORAGE_FIELD_NAME_DESCRIPTION_SORTING, STRING));
			collector = TopFieldCollector.create(sort, (page + 1) * PAGE_SIZE, 1);
		} else {
			collector = TopScoreDocCollector.create((page + 1) * PAGE_SIZE, 1);
		}
		return collector;
	}
}
