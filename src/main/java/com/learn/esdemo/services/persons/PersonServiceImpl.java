package com.learn.esdemo.services.persons;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.esdemo.entities.Person;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class PersonServiceImpl implements PersonService {

    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public PersonServiceImpl(RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) {
        this.restHighLevelClient = restHighLevelClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Person save(Person person) {
        Map<String, Object> mappedDocument = objectMapper.convertValue(person, Map.class);
        IndexRequest request = new IndexRequest("persons", "_doc").id(UUID.randomUUID().toString()).source(mappedDocument);
        try {
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            DocWriteResponse.Result result = response.getResult();
            System.out.println(result.name());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<Person> findOne(String id) {
        return null;
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>();
    }
}
