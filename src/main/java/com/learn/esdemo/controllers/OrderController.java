package com.learn.esdemo.controllers;

import com.learn.esdemo.dto.OrderDTO;
import com.learn.esdemo.entities.Order;
import com.learn.esdemo.exceptions.PersistenceException;
import com.learn.esdemo.services.orders.OrderService;
import com.learn.esdemo.transformers.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.learn.esdemo.transformers.OrderTransformer.transform;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        try {
            orderService.create(transform(orderDTO));
            return ok(orderDTO);
        } catch (PersistenceException e) {
            return status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> get() {

        try {
            List<OrderDTO> dtoList = orderService.getAll()
                    .stream()
                    .map(OrderTransformer::transform)
                    .collect(toList());
            return ok(dtoList);
        } catch (PersistenceException e) {
            return status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable String id) {
        try {
            Optional<Order> orderOpt = orderService.get(id);
            if (orderOpt.isPresent()) {
                return ok(transform(orderOpt.get()));
            }
            return noContent().build();
        } catch (PersistenceException e) {
            return status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
