package com.learn.esdemo.services.orders;

import com.learn.esdemo.entities.Location;
import com.learn.esdemo.entities.Order;
import com.learn.esdemo.exceptions.PersistenceException;
import com.learn.esdemo.repositories.orders.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void create(Order order) throws PersistenceException {
        if (order == null) {
            throw new PersistenceException("Invalid Order", new Exception());
        }

        order.setId(UUID.randomUUID().toString());
        save(order);
    }

    @Override
    public void save(Order order) throws PersistenceException {
        orderRepository.save(order);
    }

    @Override
    public Optional<Order> get(String id) throws PersistenceException {
        return orderRepository.get(id);
    }

    @Override
    public List<Order> getAll() throws PersistenceException {
        return orderRepository.getAll();
    }

    @Override
    public List<Order> getAll(Location location) {
        return null;
    }
}
