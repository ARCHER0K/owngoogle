package com.example.owngoogle.storage;

import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.search.TopScoreDocCollector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.example.owngoogle.storage.CollectorFactory.getTopDocsCollector;

class CollectorFactoryUnitTest {

	@Test
	void returnRelevanceCollector() {
		final TopDocsCollector<? extends ScoreDoc> collector = getTopDocsCollector(0, "rel");
		Assertions.assertTrue(collector instanceof TopScoreDocCollector);
	}

	@Test
	void returnAlphabeticalCollector() {
		final TopDocsCollector<? extends ScoreDoc> collector = getTopDocsCollector(0, "abc");
		Assertions.assertTrue(collector instanceof TopFieldCollector);
	}

	@Test
	void returnRelevanceCollectorWhenSortIsUnknown() {
		final TopDocsCollector<? extends ScoreDoc> collector = getTopDocsCollector(0, "-test-");
		Assertions.assertTrue(collector instanceof TopScoreDocCollector);
	}
}