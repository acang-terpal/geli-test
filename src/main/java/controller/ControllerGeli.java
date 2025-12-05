package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import configuration.DependencyConfig;
import constant.ConstantMessage;
import entity.*;
import helper.Helper;
import jakarta.servlet.http.HttpServletRequest;
import net.sf.ehcache.Cache;
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

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hasan
 */

@Component("ControllerGeli")
@Scope("prototype")
public class ControllerGeli {
    private final Helper hlp;
    private final Cache cacheCookies;
    private final Cache cacheMaster;
    private final GeliItemService geliItemService;
    private final GeliColourService geliColourService;
    private final GeliSizeService geliSizeService;
    private final GeliStockService geliStockService;
    private ResponseEntity<byte[]> response;
    private HashMap objParams;
    private HashMap objRequestProperties;
    private String htttpGetQuery;

    @Autowired
    public ControllerGeli(DependencyConfig dependencyConfig, Helper hlp, GeliItemService geliItemService, GeliColourService geliColourService, GeliSizeService geliSizeService, GeliStockService geliStockService) {
        this.cacheCookies = dependencyConfig.getCacheCookies();
        this.cacheMaster = dependencyConfig.getCacheMaster();
        this.hlp = hlp;
        this.geliItemService = geliItemService;
        this.geliColourService = geliColourService;
        this.geliSizeService = geliSizeService;
        this.geliStockService = geliStockService;
    }

    public void handleRequestProperties(HttpServletRequest request) {
        this.objRequestProperties = this.hlp.handleRequestProperties(request);
        this.response = (ResponseEntity<byte[]>) objRequestProperties.get("response");
        this.htttpGetQuery = objRequestProperties.get("query").toString();
        this.objParams = (HashMap) objRequestProperties.get("objParams");
    }

    public ResponseEntity<byte[]> getItem(HttpServletRequest request) {
        this.handleRequestProperties(request);
        JsonObject jObjResponse = ConstantMessage.Pesan.getFailed.pesan();
        jObjResponse.addProperty("rc", "01");
        jObjResponse.addProperty("message", "Failed");
        try {
            List<EntityItemResponse> listItem = this.geliItemService.getItem(objParams);
            JsonArray jArrItem = new JsonArray();
            for(int i = 0 ; i < listItem.size(); i++){
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
            jObjResponse.add("data", jArrItem);
            jObjResponse.addProperty("rc", "00");
            jObjResponse.addProperty("message", "success");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", String.valueOf(MediaType.APPLICATION_JSON));
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this.response;
    }

    public ResponseEntity<byte[]> getColour(HttpServletRequest request) {
        this.handleRequestProperties(request);
        JsonObject jObjResponse = ConstantMessage.Pesan.getFailed.pesan();
        jObjResponse.addProperty("rc", "01");
        jObjResponse.addProperty("message", "Failed");
        try {
            List<EntityColourResponse> listItem = this.geliColourService.getColour(objParams);
            JsonArray jArrItem = new JsonArray();
            for(int i = 0 ; i < listItem.size(); i++){
                JsonObject jsonObjItem = new JsonObject();
                EntityColourResponse entityItemResponse = listItem.get(i);
                jsonObjItem.addProperty("colourId", entityItemResponse.getColourId());
                jsonObjItem.addProperty("value", entityItemResponse.getValue());
                jArrItem.add(jsonObjItem);
            }
            jObjResponse.add("data", jArrItem);
            jObjResponse.addProperty("rc", "00");
            jObjResponse.addProperty("message", "success");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", String.valueOf(MediaType.APPLICATION_JSON));
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this.response;
    }

    public ResponseEntity<byte[]> getSize(HttpServletRequest request) {
        this.handleRequestProperties(request);
        JsonObject jObjResponse = ConstantMessage.Pesan.getFailed.pesan();
        jObjResponse.addProperty("rc", "01");
        jObjResponse.addProperty("message", "Failed");
        try {
            List<EntitySizeResponse> listItem = this.geliSizeService.getSize(objParams);
            JsonArray jArrItem = new JsonArray();
            for(int i = 0 ; i < listItem.size(); i++){
                JsonObject jsonObjItem = new JsonObject();
                EntitySizeResponse entityItemResponse = listItem.get(i);
                jsonObjItem.addProperty("sizeId", entityItemResponse.getSizeId());
                jsonObjItem.addProperty("value", entityItemResponse.getValue());
                jArrItem.add(jsonObjItem);
            }
            jObjResponse.add("data", jArrItem);
            jObjResponse.addProperty("rc", "00");
            jObjResponse.addProperty("message", "success");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", String.valueOf(MediaType.APPLICATION_JSON));
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this.response;
    }

    public ResponseEntity<byte[]> getStock(HttpServletRequest request) {
        this.handleRequestProperties(request);
        JsonObject jObjResponse = ConstantMessage.Pesan.getFailed.pesan();
        jObjResponse.addProperty("rc", "01");
        jObjResponse.addProperty("message", "Failed");
        try {
            List<EntityStockResponse> listItem = this.geliStockService.getStock(objParams);
            JsonArray jArrItem = new JsonArray();
            for(int i = 0 ; i < listItem.size(); i++){
                JsonObject jsonObjItem = new JsonObject();
                EntityStockResponse entityItemResponse = listItem.get(i);
                jsonObjItem.addProperty("stockId", entityItemResponse.getStockId());
                jsonObjItem.addProperty("value", entityItemResponse.getValue());
                jArrItem.add(jsonObjItem);
            }
            jObjResponse.add("data", jArrItem);
            jObjResponse.addProperty("rc", "00");
            jObjResponse.addProperty("message", "success");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", String.valueOf(MediaType.APPLICATION_JSON));
            this.response = new ResponseEntity<>(jObjResponse.toString().getBytes(StandardCharsets.UTF_8), headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this.response;
    }
}
