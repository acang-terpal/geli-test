package service;

import entity.EntityItem;
import helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.GeliItemRepo;

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

    public List<Map<String, Object>>  getItem(HashMap<String, Object> objParams) {
        List<Map<String, Object>> entityItems = geliItemRepo.getItem(objParams.get("limit").toString(), objParams.get("offset").toString());
        return entityItems;
    }

}
