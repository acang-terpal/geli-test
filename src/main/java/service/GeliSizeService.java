package service;

import entity.EntityItem;
import helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Object>  getAccount(HashMap<String, Object> objParams) {
        List<EntityItem> entityItems = geliSizeService.getAccount(objParams.get("login_username").toString(), objParams.get("account_password").toString());
        List  result = new ArrayList();
        if(entityItems.size() > 0) {
            HashMap<String, Object> mapAccount = (HashMap<String, Object>) this.helper.convertObjectToMap(entityItems.get(0));
            result.add(mapAccount);
        }
        return result;
    }

}
