package com.learn.esdemo.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.esdemo.entities.AbstractEntity;
import com.learn.esdemo.exceptions.PersistenceException;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.elasticsearch.client.RequestOptions.DEFAULT;

public abstract class ESRepository<T extends AbstractEntity> {

    private static final Logger logger = LoggerFactory.getLogger(ESRepository.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ObjectMapper objectMapper;

    public void save(String index, T entity) throws PersistenceException {

        Map<String, Object> mappedDocument = objectMapper.convertValue(entity, Map.class);
        IndexRequest request = new IndexRequest(index).id(entity.getId())
                .source(mappedDocument);
        try {
            IndexResponse response = restHighLevelClient.index(request, DEFAULT);
            switch (response.getResult()) {
                case CREATED:
                    logger.debug("doc created");
                    break;
                case UPDATED:
                    logger.debug("doc updated");
                    break;
                default:
                    logger.warn("something weird happened");
            }
        } catch (IOException e) {
            logger.error("Exception occurred while saving doc", e);
            throw new PersistenceException(e.getMessage(), e);
        } catch (ElasticsearchException e) {
            logger.error("Exception occurred while saving doc", e);
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    public Optional<T> get(String index, String id, Class<T> clazz) throws PersistenceException {
        GetRequest getRequest = new GetRequest(index).id(id);
        try {
            GetResponse response = restHighLevelClient.get(getRequest, DEFAULT);
            if (response.isExists()) {
                return of(objectMapper.readValue(response.getSourceAsString(), clazz));
            }
            return empty();
        } catch (IOException e) {
            logger.error("Exception occurred while saving doc", e);
            throw new PersistenceException(e.getMessage(), e);
        } catch (ElasticsearchException e) {
            logger.error("Exception occurred while saving doc", e);
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    public List<T> search(String index, QueryBuilder queryBuilder, Class<T> clazz) throws PersistenceException {

        List<T> results = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, DEFAULT);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                results.add(objectMapper.readValue(hit.getSourceAsString(), clazz));
            }
        } catch (IOException e) {
            logger.error("Exception occurred while saving doc", e);
            throw new PersistenceException(e.getMessage(), e);
        } catch (ElasticsearchException e) {
            logger.error("Exception occurred while saving doc", e);
            throw new PersistenceException(e.getMessage(), e);
        }
        return results;
    }
}
