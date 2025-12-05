package service;

import entity.EntityItem;
import entity.EntitySize;
import entity.EntitySizeResponse;
import helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.GeliItemRepo;
import repository.GeliSizeRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GeliSizeService {

    private final GeliSizeRepo geliSizeService;
    private final Helper helper;

    @Autowired
    public GeliSizeService(GeliSizeRepo geliSizeRepo, Helper helper) {
        this.geliSizeService = geliSizeRepo;
        this.helper = helper;
    }

    public List<EntitySizeResponse>  getSize(HashMap<String, Object> objParams) {
        List<EntitySizeResponse> entitySizeList = geliSizeService.getSize(PageRequest.of(Integer.parseInt(objParams.get("page").toString()), Integer.parseInt(objParams.get("size").toString())));
        return entitySizeList;
    }

    public EntitySize getSizeById(EntitySize entitySize) {
        EntitySize entitySizeResponse = geliSizeService.getReferenceById(entitySize.getSizeId());
        return entitySizeResponse;
    }

    public EntitySize createSize(HashMap objParams) {
        EntitySize entitySize = new EntitySize();
        entitySize.setValue(objParams.get("value").toString());
        entitySize = geliSizeService.save(entitySize);
        return entitySize;
    }
}
