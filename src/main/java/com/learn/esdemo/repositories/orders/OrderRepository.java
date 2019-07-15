package com.learn.esdemo.repositories.orders;

import com.learn.esdemo.entities.Order;
import com.learn.esdemo.exceptions.PersistenceException;
import com.learn.esdemo.repositories.ESRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Repository
public class OrderRepository extends ESRepository<Order> {

    private static String INDEX = "orders";

    public void save(Order order) throws PersistenceException {
        save(INDEX, order);
    }

    public Optional<Order> get(String id) throws PersistenceException {
        return get(INDEX, id, Order.class);
    }

    public List<Order> getAll() throws PersistenceException {
        return search(INDEX, matchAllQuery(), Order.class);
    }
}
