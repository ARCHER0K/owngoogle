package com.example.owngoogle.storage;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.owngoogle.Constants.PAGE_SIZE;
import static com.example.owngoogle.Constants.SORTING_ALPHABETICALLY;
import static org.apache.lucene.document.Field.Store.YES;
import static org.apache.lucene.search.SortField.Type.STRING;

@Component
public class SiteStorage implements ISiteStorage {

	private static final Logger log = LoggerFactory.getLogger(SiteStorage.class);

	private final StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
	private final Directory directory = new RAMDirectory();
	private final IndexWriterConfig config = new IndexWriterConfig(standardAnalyzer);
	private final IndexWriter writer = new IndexWriter(directory, config);

	private static final String FIELD_NAME_CONTENT = "content";
	private static final String FIELD_NAME_DESCRIPTION_SORTING = "descrsorting";
	private static final String FIELD_NAME_DESCRIPTION = "description";
	private static final String FIELD_NAME_LINK = "link";

	public SiteStorage() throws IOException {
	}

	@PostConstruct
	private void init() throws IOException {
		writer.commit();
	}

	public void add(StorageItem item){
		try {
			Document document = new Document();
			document.add(new TextField(FIELD_NAME_DESCRIPTION, item.getDescription(), YES));
			document.add(new SortedDocValuesField(FIELD_NAME_DESCRIPTION_SORTING, new BytesRef(item.getDescription())));
			document.add(new TextField(FIELD_NAME_CONTENT, item.getBody(), YES));
			document.add(new TextField(FIELD_NAME_LINK, item.getUrl(), YES));
			final Term term = new Term(FIELD_NAME_LINK, item.getUrl());
			writer.updateDocument(term, document);
			writer.commit();
		} catch (IOException e) {
			log.error("Impossible to add data to storage", e);
		}
	}

	public StorageResult find(String queryValue, int page, String sorting) {
		try (IndexReader reader = DirectoryReader.open(directory)) {
			IndexSearcher searcher = new IndexSearcher(reader);
			final QueryParser parser = getQueryParser();
			Query query = parser.parse(queryValue);
			TopDocsCollector<? extends ScoreDoc> collector = getTopDocsCollector(page, sorting);
			searcher.search(query, collector);
			final TopDocs results = collector.topDocs(page * PAGE_SIZE, PAGE_SIZE);
			List<StorageItem> searchResult = new ArrayList<>();
			for (ScoreDoc result : results.scoreDocs) {
				Document resultDoc = searcher.doc(result.doc);
				final StorageItem storageItem = mapToStorageItem(resultDoc);
				searchResult.add(storageItem);
			}
			return new StorageResult((int) results.totalHits.value, searchResult);
		} catch (IOException | ParseException e) {
			log.error("Impossible to get data from storage", e);
			return StorageResult.empty();
		}
	}

	private TopDocsCollector<? extends ScoreDoc> getTopDocsCollector(int page, String sorting) {
		TopDocsCollector<? extends ScoreDoc> collector;
		if (SORTING_ALPHABETICALLY.equalsIgnoreCase(sorting)) {
			final Sort sort = new Sort(new SortField(FIELD_NAME_DESCRIPTION_SORTING, STRING));
			collector = TopFieldCollector.create(sort, (page + 1) * PAGE_SIZE, 1);
		} else {
			collector = TopScoreDocCollector.create((page + 1) * PAGE_SIZE, 1);
		}
		return collector;
	}

	private QueryParser getQueryParser() {
		return new MultiFieldQueryParser(
						new String[] {
								FIELD_NAME_CONTENT,
								FIELD_NAME_DESCRIPTION,
								FIELD_NAME_LINK
						},
						standardAnalyzer);
	}

	private StorageItem mapToStorageItem(Document resultDoc) {
		final String description = resultDoc.get(FIELD_NAME_DESCRIPTION);
		final String content = resultDoc.get(FIELD_NAME_CONTENT);
		final String link = resultDoc.get(FIELD_NAME_LINK);
		return new StorageItem(link, description, content);
	}
}
