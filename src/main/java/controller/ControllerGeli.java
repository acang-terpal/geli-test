package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import configuration.DependencyConfig;
import configuration.TransactionalPayment;
import constant.ConstantMessage;
import entity.*;
import helper.Helper;
import jakarta.servlet.http.HttpServletRequest;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.GeliColourService;
import service.GeliItemService;
import service.GeliSizeService;
import service.GeliStockService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author hasan
 */

@Component("ControllerGeli")
@Scope("prototype")
public class ControllerGeli {
    private final Helper hlp;
    private final Cache cache;
    private final GeliItemService geliItemService;
    private final GeliColourService geliColourService;
    private final GeliSizeService geliSizeService;
    private final GeliStockService geliStockService;
    private final HttpHeaders headers;
    private final TransactionalPayment transactionalPayment;
    private JsonObject jObjResponse;
    private ResponseEntity<byte[]> response;
    private HashMap objParams;
    private HashMap objRequestProperties;
    private String htttpGetQuery;

    @Autowired
    public ControllerGeli(DependencyConfig dependencyConfig, Helper hlp, GeliItemService geliItemService, GeliColourService geliColourService, GeliSizeService geliSizeService, GeliStockService geliStockService, TransactionalPayment transactionalPayment) {
        this.cache = dependencyConfig.getCache();
        this.hlp = hlp;
        this.geliItemService = geliItemService;
        this.geliColourService = geliColourService;
        this.geliSizeService = geliSizeService;
        this.geliStockService = geliStockService;
        this.transactionalPayment = transactionalPayment;
        this.headers = new HttpHeaders();
        this.headers.add("Content-Type", String.valueOf(MediaType.APPLICATION_JSON));
        this.jObjResponse = ConstantMessage.Message.getFailed.message();
        this.jObjResponse.addProperty("rc", "01");
        this.jObjResponse.addProperty("message", "Failed");
    }

    public void handleRequestProperties(HttpServletRequest request) {
        this.objRequestProperties = this.hlp.handleRequestProperties(request);
        this.response = (ResponseEntity<byte[]>) objRequestProperties.get("response");
        this.htttpGetQuery = objRequestProperties.get("query").toString();
        this.objParams = (HashMap) objRequestProperties.get("objParams");
    }

    public ResponseEntity<byte[]> createItem(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntityItem itemEntity = this.geliItemService.createItem(objParams);
            Optional<EntityItemResponse> itemEntityResponseOptional = this.geliItemService.getItemById(itemEntity);
            if (!itemEntityResponseOptional.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                return this.response;
            }
            EntityItemResponse itemEntityResponse = itemEntityResponseOptional.get();
            JsonObject jsonObjItem = new JsonObject();
            jsonObjItem.addProperty("itemId", itemEntityResponse.getItemId());
            jsonObjItem.addProperty("name", itemEntityResponse.getName());
            jsonObjItem.addProperty("price", itemEntityResponse.getPrice());
            jsonObjItem.addProperty("stock", itemEntityResponse.getStock());
            jsonObjItem.addProperty("size", itemEntityResponse.getSize());
            jsonObjItem.addProperty("colour", itemEntityResponse.getColour());
            jsonObjItem.addProperty("unit", itemEntityResponse.getUnit());
            this.jObjResponse.add("data", jsonObjItem);
            this.jObjResponse.addProperty("rc", "00");
            this.jObjResponse.addProperty("message", "success");
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> createColour(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntityColour colourEntity = this.geliColourService.createColour(objParams);
            Optional<EntityColour> colourEntityOptional = this.geliColourService.getColourById(colourEntity);
            if (!colourEntityOptional.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                return this.response;
            }
            colourEntity = colourEntityOptional.get();
            JsonObject jsonObjItem = new JsonObject();
            jsonObjItem.addProperty("colourId", colourEntity.getColourId());
            jsonObjItem.addProperty("value", colourEntity.getValue());
            this.jObjResponse.add("data", jsonObjItem);
            this.jObjResponse.addProperty("rc", "00");
            this.jObjResponse.addProperty("message", "success");
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> createSize(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntitySize entitySize = this.geliSizeService.createSize(objParams);
            Optional<EntitySize> entitySizeOptional = this.geliSizeService.getSizeById(entitySize);
            if (!entitySizeOptional.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                return this.response;
            }
            entitySize = entitySizeOptional.get();
            JsonObject jsonObjItem = new JsonObject();
            jsonObjItem.addProperty("sizeId", entitySize.getSizeId());
            jsonObjItem.addProperty("value", entitySize.getValue());
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jsonObjItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> createStock(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntityStock entityStock = this.geliStockService.createStock(objParams);
            Optional<EntityStock> entityStockOptional = this.geliStockService.getStockById(entityStock);
            if (!entityStockOptional.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                return this.response;
            }
            entityStock = entityStockOptional.get();
            JsonObject jsonObjItem = new JsonObject();
            jsonObjItem.addProperty("stockId", entityStock.getStockId());
            jsonObjItem.addProperty("value", entityStock.getValue());
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jsonObjItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> getItem(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            List<EntityItemResponse> listItem = this.geliItemService.getItem(objParams);
            JsonArray jArrItem = new JsonArray();
            for (int i = 0; i < listItem.size(); i++) {
                JsonObject jsonObjItem = new JsonObject();
                EntityItemResponse entityItemResponse = listItem.get(i);
                jsonObjItem.addProperty("itemId", entityItemResponse.getItemId());
                jsonObjItem.addProperty("name", entityItemResponse.getName());
                jsonObjItem.addProperty("price", entityItemResponse.getPrice());
                jsonObjItem.addProperty("stock", entityItemResponse.getStock());
                jsonObjItem.addProperty("size", entityItemResponse.getSize());
                jsonObjItem.addProperty("colour", entityItemResponse.getColour());
                jsonObjItem.addProperty("unit", entityItemResponse.getUnit());
                jArrItem.add(jsonObjItem);
            }
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jArrItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> getItemById(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntityItem itemEntity = new EntityItem();
            itemEntity.setItemId(Long.parseLong(objParams.get("itemId").toString()));
            Optional<EntityItemResponse> itemEntityResponseOptional = this.geliItemService.getItemById(itemEntity);
            if (!itemEntityResponseOptional.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                return this.response;
            }
            EntityItemResponse itemEntityResponse = itemEntityResponseOptional.get();
            JsonObject jsonObjItem = new JsonObject();
            jsonObjItem.addProperty("itemId", itemEntityResponse.getItemId());
            jsonObjItem.addProperty("name", itemEntityResponse.getName());
            jsonObjItem.addProperty("price", itemEntityResponse.getPrice());
            jsonObjItem.addProperty("stock", itemEntityResponse.getStock());
            jsonObjItem.addProperty("size", itemEntityResponse.getSize());
            jsonObjItem.addProperty("colour", itemEntityResponse.getColour());
            jsonObjItem.addProperty("unit", itemEntityResponse.getUnit());
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jsonObjItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> getColour(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            List<EntityColourResponse> listItem = this.geliColourService.getColour(objParams);
            JsonArray jArrItem = new JsonArray();
            for (int i = 0; i < listItem.size(); i++) {
                JsonObject jsonObjItem = new JsonObject();
                EntityColourResponse entityItemResponse = listItem.get(i);
                jsonObjItem.addProperty("colourId", entityItemResponse.getColourId());
                jsonObjItem.addProperty("value", entityItemResponse.getValue());
                jArrItem.add(jsonObjItem);
            }
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jArrItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> getColourById(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntityColour entityColour = new EntityColour();
            entityColour.setColourId(Long.parseLong(objParams.get("colourId").toString()));
            Optional<EntityColour> entityColourOptional = this.geliColourService.getColourById(entityColour);
            if (!entityColourOptional.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                return this.response;
            }
            entityColour = entityColourOptional.get();
            JsonObject jsonObjItem = new JsonObject();
            jsonObjItem.addProperty("colourId", entityColour.getColourId());
            jsonObjItem.addProperty("value", entityColour.getValue());
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jsonObjItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> getSize(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            List<EntitySizeResponse> listItem = this.geliSizeService.getSize(objParams);
            JsonArray jArrItem = new JsonArray();
            for (int i = 0; i < listItem.size(); i++) {
                JsonObject jsonObjItem = new JsonObject();
                EntitySizeResponse entityItemResponse = listItem.get(i);
                jsonObjItem.addProperty("sizeId", entityItemResponse.getSizeId());
                jsonObjItem.addProperty("value", entityItemResponse.getValue());
                jArrItem.add(jsonObjItem);
            }
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jArrItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> getSizeById(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntitySize entitySize = new EntitySize();
            entitySize.setSizeId(Long.parseLong(objParams.get("sizeId").toString()));
            Optional<EntitySize> entitySizeOptional = this.geliSizeService.getSizeById(entitySize);
            if (!entitySizeOptional.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                return this.response;
            }
            entitySize = entitySizeOptional.get();
            JsonObject jsonObjItem = new JsonObject();
            jsonObjItem.addProperty("sizeId", entitySize.getSizeId());
            jsonObjItem.addProperty("value", entitySize.getValue());
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jsonObjItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> getStock(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            List<EntityStockResponse> listItem = this.geliStockService.getStock(objParams);
            JsonArray jArrItem = new JsonArray();
            for (int i = 0; i < listItem.size(); i++) {
                JsonObject jsonObjItem = new JsonObject();
                EntityStockResponse entityItemResponse = listItem.get(i);
                jsonObjItem.addProperty("stockId", entityItemResponse.getStockId());
                jsonObjItem.addProperty("value", entityItemResponse.getValue());
                jArrItem.add(jsonObjItem);
            }
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jArrItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> getStockById(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntityStock entityStock = new EntityStock();
            entityStock.setStockId(Long.parseLong(objParams.get("stockId").toString()));
            Optional<EntityStock> optionalEntityStock = this.geliStockService.getStockById(entityStock);
            if (!optionalEntityStock.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                return this.response;
            }
            entityStock = optionalEntityStock.get();
            JsonObject jsonObjItem = new JsonObject();
            jsonObjItem.addProperty("stockId", entityStock.getStockId());
            jsonObjItem.addProperty("value", entityStock.getValue());
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jsonObjItem);
            this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> updateItem(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntityItem itemEntity = this.geliItemService.updateItem(objParams);
            Optional<EntityItemResponse> itemEntityResponseOptional = this.geliItemService.getItemById(itemEntity);
            if (!itemEntityResponseOptional.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                return this.response;
            }
            EntityItemResponse itemEntityResponse = itemEntityResponseOptional.get();
            JsonObject jsonObjItem = new JsonObject();
            jsonObjItem.addProperty("itemId", itemEntityResponse.getItemId());
            jsonObjItem.addProperty("name", itemEntityResponse.getName());
            jsonObjItem.addProperty("price", itemEntityResponse.getPrice());
            jsonObjItem.addProperty("stock", itemEntityResponse.getStock());
            jsonObjItem.addProperty("size", itemEntityResponse.getSize());
            jsonObjItem.addProperty("colour", itemEntityResponse.getColour());
            jsonObjItem.addProperty("unit", itemEntityResponse.getUnit());
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jsonObjItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> updateColour(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntityColour colourEntity = this.geliColourService.updateColour(objParams);
            Optional<EntityColour> colourEntityOptional = this.geliColourService.getColourById(colourEntity);
            if (!colourEntityOptional.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                return this.response;
            }
            colourEntity = colourEntityOptional.get();
            JsonObject jsonObjItem = new JsonObject();
            jsonObjItem.addProperty("colourId", colourEntity.getColourId());
            jsonObjItem.addProperty("value", colourEntity.getValue());
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jsonObjItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> updateSize(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntitySize entitySize = this.geliSizeService.updateSize(objParams);
            Optional<EntitySize> entitySizeOptional = this.geliSizeService.getSizeById(entitySize);
            if (!entitySizeOptional.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                return this.response;
            }
            entitySize = entitySizeOptional.get();
            JsonObject jsonObjItem = new JsonObject();
            jsonObjItem.addProperty("sizeId", entitySize.getSizeId());
            jsonObjItem.addProperty("value", entitySize.getValue());
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jsonObjItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> updateStock(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntityStock entityStock = this.geliStockService.updateStock(objParams);
            Optional<EntityStock> entityStockOptional = this.geliStockService.getStockById(entityStock);
            if (!entityStockOptional.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                return this.response;
            }
            entityStock = entityStockOptional.get();
            JsonObject jsonObjItem = new JsonObject();
            jsonObjItem.addProperty("stockId", entityStock.getStockId());
            jsonObjItem.addProperty("value", entityStock.getValue());
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jsonObjItem);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> deleteItem(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntityItem entityItem = new EntityItem();
            entityItem.setItemId(Long.parseLong(objParams.get("itemId").toString()));
            Optional<EntityItemResponse> itemEntityResponseOptional = this.geliItemService.getItemById(entityItem);
            if (itemEntityResponseOptional.isPresent()) {
                this.geliItemService.deleteItem(objParams);
                itemEntityResponseOptional = this.geliItemService.getItemById(entityItem);
                if (!itemEntityResponseOptional.isPresent()) {
                    this.jObjResponse = ConstantMessage.Message.getSuccess.message();
                    this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                    return this.response;
                } else {
                    this.jObjResponse = ConstantMessage.Message.getFailed.message();
                    this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                    return this.response;
                }
            } else {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> deleteColour(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntityColour entityColour = new EntityColour();
            entityColour.setColourId(Long.parseLong(objParams.get("colourId").toString()));
            Optional<EntityColour> entityColourOptional = this.geliColourService.getColourById(entityColour);
            if (entityColourOptional.isPresent()) {
                this.geliColourService.deleteColour(objParams);
                entityColourOptional = this.geliColourService.getColourById(entityColour);
                if (!entityColourOptional.isPresent()) {
                    this.jObjResponse = ConstantMessage.Message.getSuccess.message();
                    this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                    return this.response;
                } else {
                    this.jObjResponse = ConstantMessage.Message.getFailedDeleteStillHasChildren.message();
                    this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                    return this.response;
                }
            } else {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> deleteStock(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntityStock entityStock = new EntityStock();
            entityStock.setStockId(Long.parseLong(objParams.get("stockId").toString()));
            Optional<EntityStock> entityStockOptional = this.geliStockService.getStockById(entityStock);
            if (entityStockOptional.isPresent()) {
                this.geliStockService.deleteStock(objParams);
                entityStockOptional = this.geliStockService.getStockById(entityStock);
                if (!entityStockOptional.isPresent()) {
                    this.jObjResponse = ConstantMessage.Message.getSuccess.message();
                    this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                    return this.response;
                } else {
                    this.jObjResponse = ConstantMessage.Message.getFailedDeleteStillHasChildren.message();
                    this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                    return this.response;
                }
            } else {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> deleteSize(HttpServletRequest request) {
        this.handleRequestProperties(request);
        try {
            EntitySize entitySize = new EntitySize();
            entitySize.setSizeId(Long.parseLong(objParams.get("sizeId").toString()));
            Optional<EntitySize> entitySizeOptional = this.geliSizeService.getSizeById(entitySize);
            if (entitySizeOptional.isPresent()) {
                this.geliSizeService.deleteSize(objParams);
                entitySizeOptional = this.geliSizeService.getSizeById(entitySize);
                if (!entitySizeOptional.isPresent()) {
                    this.jObjResponse = ConstantMessage.Message.getSuccess.message();
                    this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                    return this.response;
                } else {
                    this.jObjResponse = ConstantMessage.Message.getFailedDeleteStillHasChildren.message();
                    this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                    return this.response;
                }
            } else {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> addCart(HttpServletRequest request){
        this.handleRequestProperties(request);
        try {
            String itemId = objParams.get("itemId").toString();
            String amount = objParams.get("amount").toString();
            EntityItem itemEntity = new EntityItem();
            itemEntity.setItemId(Long.parseLong(itemId));
            Optional<EntityItemResponse> itemEntityResponseOptional = this.geliItemService.getItemById(itemEntity);
            if (!itemEntityResponseOptional.isPresent()) {
                this.jObjResponse = ConstantMessage.Message.getNotFound.message();
                this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                return this.response;
            }
            EntityItemResponse itemEntityResponse = itemEntityResponseOptional.get();
            if(itemEntityResponse.getStock() - Long.parseLong(amount) < 0){
                this.jObjResponse = ConstantMessage.Message.getOutOffStock.message();
                this.response = new ResponseEntity<>(this.jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
                return this.response;
            }
            //add to cart on memory
            Element cacheElement = this.cache.get("cart");
            HashMap<String, String> hashMapCart = (HashMap<String, String>) cacheElement.getObjectValue();
            hashMapCart.put(itemId, amount);
            this.cache.put(new Element("cart", hashMapCart));

            JsonArray jsonArray = new JsonArray();
            if(hashMapCart.size() == 0){
                itemEntity = new EntityItem();
                itemEntity.setItemId(Long.parseLong(itemId));
                itemEntityResponseOptional = this.geliItemService.getItemById(itemEntity);
                if (itemEntityResponseOptional.isPresent()) {
                    itemEntityResponse = itemEntityResponseOptional.get();
                    JsonObject jsonObjItem = new JsonObject();
                    jsonObjItem.addProperty("itemId", itemEntityResponse.getItemId());
                    jsonObjItem.addProperty("name", itemEntityResponse.getName());
                    jsonObjItem.addProperty("price", itemEntityResponse.getPrice());
                    jsonObjItem.addProperty("stock", itemEntityResponse.getStock());
                    jsonObjItem.addProperty("size", itemEntityResponse.getSize());
                    jsonObjItem.addProperty("colour", itemEntityResponse.getColour());
                    jsonObjItem.addProperty("unit", itemEntityResponse.getUnit());
                    jsonObjItem.addProperty("amount", amount);
                    jsonArray.add(jsonObjItem);
                }
            } else {
                //get all cart
                for (Map.Entry<String, String> entry : hashMapCart.entrySet()) {
                    String itemId_ = entry.getKey();
                    String amount_ = entry.getValue();
                    itemEntity = new EntityItem();
                    itemEntity.setItemId(Long.parseLong(itemId_));
                    itemEntityResponseOptional = this.geliItemService.getItemById(itemEntity);
                    if (itemEntityResponseOptional.isPresent()) {
                        itemEntityResponse = itemEntityResponseOptional.get();
                        JsonObject jsonObjItem = new JsonObject();
                        jsonObjItem.addProperty("itemId", itemEntityResponse.getItemId());
                        jsonObjItem.addProperty("name", itemEntityResponse.getName());
                        jsonObjItem.addProperty("price", itemEntityResponse.getPrice());
                        jsonObjItem.addProperty("stock", itemEntityResponse.getStock());
                        jsonObjItem.addProperty("size", itemEntityResponse.getSize());
                        jsonObjItem.addProperty("colour", itemEntityResponse.getColour());
                        jsonObjItem.addProperty("unit", itemEntityResponse.getUnit());
                        jsonObjItem.addProperty("amount", amount_);
                        jsonArray.add(jsonObjItem);
                    }
                }
            }
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jsonArray);
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> getCart(HttpServletRequest request){
        this.handleRequestProperties(request);
        try {
            Element cacheElement = this.cache.get("cart");
            HashMap<String, String> hashMapCart = (HashMap<String, String>) cacheElement.getObjectValue();

            BigDecimal totalPrice = new BigDecimal(0);
            JsonArray jsonArray = new JsonArray();
            for (Map.Entry<String, String> entry : hashMapCart.entrySet()) {
                String itemId_ = entry.getKey();
                String amount_ = entry.getValue();
                EntityItem itemEntity = new EntityItem();
                itemEntity.setItemId(Long.parseLong(itemId_));
                Optional<EntityItemResponse> itemEntityResponseOptional = this.geliItemService.getItemById(itemEntity);
                if (itemEntityResponseOptional.isPresent()) {
                    EntityItemResponse itemEntityResponse = itemEntityResponseOptional.get();
                    JsonObject jsonObjItem = new JsonObject();
                    jsonObjItem.addProperty("itemId", itemEntityResponse.getItemId());
                    jsonObjItem.addProperty("name", itemEntityResponse.getName());
                    jsonObjItem.addProperty("price", itemEntityResponse.getPrice());
                    jsonObjItem.addProperty("stock", itemEntityResponse.getStock());
                    jsonObjItem.addProperty("size", itemEntityResponse.getSize());
                    jsonObjItem.addProperty("colour", itemEntityResponse.getColour());
                    jsonObjItem.addProperty("unit", itemEntityResponse.getUnit());
                    jsonObjItem.addProperty("amount", amount_);
                    jsonArray.add(jsonObjItem);
                    BigDecimal subTotal = itemEntityResponse.getPrice().multiply(BigDecimal.valueOf(Long.parseLong(amount_)));
                    totalPrice = totalPrice.add(subTotal);
                }
            }
            this.jObjResponse = ConstantMessage.Message.getSuccess.message();
            this.jObjResponse.add("data", jsonArray);
            this.jObjResponse.addProperty("totalPrice", String.valueOf(totalPrice));
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }

    public ResponseEntity<byte[]> payCart(HttpServletRequest request){
        this.handleRequestProperties(request);
        try {
            boolean status = transactionalPayment.doPayment();
            if(status){
                this.jObjResponse = ConstantMessage.Message.getSuccess.message();
                this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
            } else{
                this.jObjResponse = ConstantMessage.Message.getOutOffStock.message();
                this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), this.headers, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.response;
    }
}
