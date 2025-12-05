package service;

import entity.EntityColour;
import entity.EntityColourResponse;
import entity.EntityItem;
import entity.EntityItemResponse;
import helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.GeliColourRepo;
import repository.GeliItemRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GeliColourService {

    private final GeliColourRepo geliColourRepo;
    private final Helper helper;

    @Autowired
    public GeliColourService(GeliColourRepo geliColourRepo, Helper helper) {
        this.geliColourRepo = geliColourRepo;
        this.helper = helper;
    }

    public List<EntityColourResponse>  getColour(HashMap<String, Object> objParams) {
        List<EntityColourResponse> listEntityColourResponse = geliColourRepo.getColour(PageRequest.of(Integer.parseInt(objParams.get("page").toString()), Integer.parseInt(objParams.get("size").toString())));
        return listEntityColourResponse;
    }

    public EntityColour getColourById(EntityColour entityColour) {
        EntityColour entityColourResponse = geliColourRepo.getReferenceById(entityColour.getColourId());
        return entityColourResponse;
    }

    public EntityColour createColour(HashMap objParams) {
        EntityColour entityColour = new EntityColour();
        entityColour.setValue(objParams.get("value").toString());
        EntityColour savedEntity = geliColourRepo.save(entityColour);
        return savedEntity;
    }
}
