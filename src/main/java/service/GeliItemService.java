package service;

import entity.EntityItem;
import entity.EntityItemResponse;
import helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public List<EntityItemResponse>  getItem(HashMap<String, Object> objParams) {
        List<EntityItemResponse> listEntityItemResponse = geliItemRepo.getItem(PageRequest.of(Integer.parseInt(objParams.get("page").toString()), Integer.parseInt(objParams.get("size").toString())));
        return listEntityItemResponse;
    }

}
