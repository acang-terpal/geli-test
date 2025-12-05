package service;

import entity.EntityItem;
import entity.EntityItemResponse;
import helper.Helper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.GeliItemRepo;

import java.math.BigDecimal;
import java.util.*;

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

    public Optional<EntityItemResponse> getItemById(EntityItem entityItem) {
        Optional<EntityItemResponse> entityItemResponse = geliItemRepo.getItemById(entityItem.getItemId());
        return entityItemResponse;
    }

    @Transactional
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

    @Transactional
    public EntityItem updateItem(HashMap objParams) {
        EntityItem entityItem = new EntityItem();
        entityItem.setItemId(Long.parseLong(objParams.get("itemId").toString()));
        entityItem.setColourId(Long.parseLong(objParams.get("colourId").toString()));
        entityItem.setSizeId(Long.parseLong(objParams.get("sizeId").toString()));
        entityItem.setStockId(Long.parseLong(objParams.get("stockId").toString()));
        entityItem.setName(objParams.get("name").toString());
        entityItem.setPrice(BigDecimal.valueOf(Double.parseDouble(objParams.get("price").toString())));
        entityItem.setStock(Long.parseLong(objParams.get("stock").toString()));
        EntityItem savedEntity = geliItemRepo.save(entityItem);
        return savedEntity;
    }

    @Transactional
    public void deleteItem(HashMap objParams) {
        geliItemRepo.deleteById(Long.parseLong(objParams.get("itemId").toString()));
    }
}
