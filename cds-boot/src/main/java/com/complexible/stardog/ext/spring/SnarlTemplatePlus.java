package com.complexible.stardog.ext.spring;
import java.util.ArrayList;
import java.util.List;

import org.openrdf.query.QueryEvaluationException;

import com.complexible.common.iterations.Iteration;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.search.SearchConnection;
import com.complexible.stardog.api.search.SearchResult;
import com.complexible.stardog.api.search.SearchResults;
import com.complexible.stardog.api.search.Searcher;

public class SnarlTemplatePlus extends SnarlTemplate {

	public List<SearchResult> search(int limit, String searchQuery, double threshold) {
		List<SearchResult> resultList = new ArrayList<SearchResult>();
		SearchConnection connection = null;
		try {
			connection = getDataSource().getConnection().as(SearchConnection.class);
			Searcher aSearch = connection.search().limit(limit).query(searchQuery).threshold(threshold);
			SearchResults aSearchResults = aSearch.search();
			Iteration<SearchResult, QueryEvaluationException> results = aSearchResults.iteration();
			
			while (results.hasNext()) {
				resultList.add(results.next());
			}
			results.close();
		} catch (StardogException e) {
			log.error("Error during search", e);
			throw new RuntimeException(e);
		} catch (QueryEvaluationException e) {
			log.error("Error during processing search results", e);
			throw new RuntimeException(e);
		} finally {
			getDataSource().releaseConnection(connection);
		}
		return resultList;
	}

}
