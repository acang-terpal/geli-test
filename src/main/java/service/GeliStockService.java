package service;

import entity.EntityItem;
import entity.EntityStockResponse;
import helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.GeliItemRepo;
import repository.GeliStockRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

}
