package service;

import entity.EntityItem;
import helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Object>  getAccount(HashMap<String, Object> objParams) {
        List<EntityItem> entityItems = geliStockRepo.getAccount(objParams.get("login_username").toString(), objParams.get("account_password").toString());
        List  result = new ArrayList();
        if(entityItems.size() > 0) {
            HashMap<String, Object> mapAccount = (HashMap<String, Object>) this.helper.convertObjectToMap(entityItems.get(0));
            result.add(mapAccount);
        }
        return result;
    }

}
