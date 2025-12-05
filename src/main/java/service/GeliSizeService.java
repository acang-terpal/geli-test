package service;

import entity.EntitySize;
import entity.EntitySizeResponse;
import helper.Helper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.GeliSizeRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class GeliSizeService {

    private final GeliSizeRepo geliSizeService;
    private final Helper helper;

    @Autowired
    public GeliSizeService(GeliSizeRepo geliSizeRepo, Helper helper) {
        this.geliSizeService = geliSizeRepo;
        this.helper = helper;
    }

    public List<EntitySizeResponse>  getSize(HashMap<String, Object> objParams) {
        List<EntitySizeResponse> entitySizeList = geliSizeService.getSize(PageRequest.of(Integer.parseInt(objParams.get("page").toString()), Integer.parseInt(objParams.get("size").toString())));
        return entitySizeList;
    }

    public Optional<EntitySize> getSizeById(EntitySize entitySize) {
        Optional<EntitySize> entitySizeResponse = geliSizeService.findById(entitySize.getSizeId());
        return entitySizeResponse;
    }

    @Transactional
    public EntitySize createSize(HashMap objParams) {
        EntitySize entitySize = new EntitySize();
        entitySize.setValue(objParams.get("value").toString());
        entitySize = geliSizeService.save(entitySize);
        return entitySize;
    }

    @Transactional
    public EntitySize updateSize(HashMap objParams) {
        EntitySize entitySize = new EntitySize();
        entitySize.setSizeId(Long.parseLong(objParams.get("sizeId").toString()));
        entitySize.setValue(objParams.get("value").toString());
        entitySize = geliSizeService.save(entitySize);
        return entitySize;
    }

    @Transactional
    public void deleteSize(HashMap objParams) {
        try {
            geliSizeService.deleteById(Long.parseLong(objParams.get("sizeId").toString()));
        } catch (Exception ex){

        }
    }
}
