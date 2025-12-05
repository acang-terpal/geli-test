package service;

import entity.EntityColour;
import entity.EntityColourResponse;
import entity.EntityItem;
import entity.EntityItemResponse;
import helper.Helper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.GeliColourRepo;
import repository.GeliItemRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    public Optional<EntityColour> getColourById(EntityColour entityColour) {
        Optional<EntityColour> entityColourResponse = geliColourRepo.findById(entityColour.getColourId());
        return entityColourResponse;
    }

    @Transactional
    public EntityColour createColour(HashMap objParams) {
        EntityColour entityColour = new EntityColour();
        entityColour.setValue(objParams.get("value").toString());
        EntityColour savedEntity = geliColourRepo.save(entityColour);
        return savedEntity;
    }

    @Transactional
    public EntityColour updateColour(HashMap objParams) {
        EntityColour entityColour = new EntityColour();
        entityColour.setColourId(Long.parseLong(objParams.get("colourId").toString()));
        entityColour.setValue(objParams.get("value").toString());
        EntityColour savedEntity = geliColourRepo.save(entityColour);
        return savedEntity;
    }

    @Transactional
    public void deleteColour(HashMap objParams) {
        try {
            geliColourRepo.deleteById(Long.parseLong(objParams.get("colourId").toString()));
        } catch (Exception ex){

        }
    }
}
