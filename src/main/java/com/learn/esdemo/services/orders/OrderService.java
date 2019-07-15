package com.learn.esdemo.services.orders;

import com.learn.esdemo.entities.Location;
import com.learn.esdemo.entities.Order;
import com.learn.esdemo.exceptions.PersistenceException;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void create(Order order) throws PersistenceException;

    void save(Order order) throws PersistenceException;

    Optional<Order> get(String id) throws PersistenceException;

    List<Order> getAll() throws PersistenceException;

    List<Order> getAll(Location location);
}
