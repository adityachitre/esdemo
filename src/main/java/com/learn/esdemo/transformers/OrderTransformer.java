package com.learn.esdemo.transformers;

import com.learn.esdemo.dto.OrderDTO;
import com.learn.esdemo.entities.Order;

public final class OrderTransformer {

    private OrderTransformer() {
        // No instances allowed
    }

    public static Order transform(OrderDTO dto) {

        Order order = new Order();
        order.setLocation(LocationTransformer.transform(dto.getLocation()));

        return order;
    }

    public static OrderDTO transform(Order entity) {

        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setLocation(LocationTransformer.transform(entity.getLocation()));

        return dto;
    }
}
