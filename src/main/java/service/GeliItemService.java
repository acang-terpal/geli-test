package service;

import entity.EntityItem;
import entity.EntityItemResponse;
import helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.GeliItemRepo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeliItemService {

    private final GeliItemRepo geliItemRepo;
    private final Helper helper;

    @Autowired
    public GeliItemService(GeliItemRepo geliItemRepo, Helper helper) {
        this.geliItemRepo = geliItemRepo;
        this.helper = helper;
    }

    public List<EntityItemResponse>  getItem(HashMap<String, Object> objParams) {
        List<EntityItemResponse> listEntityItemResponse = geliItemRepo.getItem(PageRequest.of(Integer.parseInt(objParams.get("page").toString()), Integer.parseInt(objParams.get("size").toString())));
        return listEntityItemResponse;
    }

    public EntityItemResponse getItemById(EntityItem entityItem) {
        EntityItemResponse entityItemResponse = geliItemRepo.getItemById(entityItem.getItemId());
        return entityItemResponse;
    }

    public EntityItem createItem(HashMap objParams) {
        EntityItem entityItem = new EntityItem();
        entityItem.setColourId(Long.parseLong(objParams.get("colourId").toString()));
        entityItem.setSizeId(Long.parseLong(objParams.get("sizeId").toString()));
        entityItem.setStockId(Long.parseLong(objParams.get("stockId").toString()));
        entityItem.setName(objParams.get("name").toString());
        entityItem.setPrice(BigDecimal.valueOf(Double.parseDouble(objParams.get("price").toString())));
        entityItem.setStock(Long.parseLong(objParams.get("stock").toString()));
        EntityItem savedEntity = geliItemRepo.save(entityItem);
        return savedEntity;
    }

}
