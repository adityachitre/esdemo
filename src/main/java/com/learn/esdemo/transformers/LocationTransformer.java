package com.learn.esdemo.transformers;

import com.learn.esdemo.dto.LocationDTO;
import com.learn.esdemo.entities.Location;

public final class LocationTransformer {

    private LocationTransformer() {
        // No instances allowed
    }

    public static Location transform(LocationDTO dto) {

        Location entity = new Location();
        entity.setLat(dto.getLat());
        entity.setLon(dto.getLon());

        return entity;
    }

    public static LocationDTO transform(Location entity) {

        LocationDTO dto = new LocationDTO();
        dto.setLat(entity.getLat());
        dto.setLon(entity.getLon());

        return dto;
    }
}
