package service;

import entity.EntityItem;
import entity.EntityStock;
import entity.EntityStockResponse;
import helper.Helper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.GeliItemRepo;
import repository.GeliStockRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class GeliStockService {

    private final GeliStockRepo geliStockRepo;
    private final Helper helper;

    @Autowired
    public GeliStockService(GeliStockRepo geliStockRepo, Helper helper) {
        this.geliStockRepo = geliStockRepo;
        this.helper = helper;
    }

    public List<EntityStockResponse>  getStock(HashMap<String, Object> objParams) {
        List<EntityStockResponse> entityStockList = geliStockRepo.getStock(PageRequest.of(Integer.parseInt(objParams.get("page").toString()), Integer.parseInt(objParams.get("size").toString())));
        return entityStockList;
    }

    public Optional<EntityStock> getStockById(EntityStock entityStock) {
        Optional<EntityStock> entityStockResponse = geliStockRepo.findById(entityStock.getStockId());
        return entityStockResponse;
    }

    @Transactional
    public EntityStock createStock(HashMap objParams) {
        EntityStock entityStock = new EntityStock();
        entityStock.setValue(objParams.get("value").toString());
        EntityStock entityStockResponse = geliStockRepo.save(entityStock);
        return entityStockResponse;
    }

    @Transactional
    public EntityStock updateStock(HashMap objParams) {
        EntityStock entityStock = new EntityStock();
        entityStock.setStockId(Long.parseLong(objParams.get("stockId").toString()));
        entityStock.setValue(objParams.get("value").toString());
        EntityStock entityStockResponse = geliStockRepo.save(entityStock);
        return entityStockResponse;
    }

    @Transactional
    public void deleteStock(HashMap objParams) {
        try {
            geliStockRepo.deleteById(Long.parseLong(objParams.get("stockId").toString()));
        } catch (Exception ex){

        }
    }
}
