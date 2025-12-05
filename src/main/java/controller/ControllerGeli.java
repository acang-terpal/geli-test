package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import configuration.DependencyConfig;
import constant.ConstantMessage;
import entity.EntityItem;
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
import service.GeliItemService;

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
    private final GeliItemService geliItemService;
    private final Helper hlp;
    private final Cache cacheCookies;
    private final Cache cacheMaster;
    private ResponseEntity<byte[]> response;
    private String fullUri, fullUrl, query, webroot, controller, methode, view, webRootUrl, baseUrl, hostPort;
    private HashMap objParams;
    private HashMap objRequestProperties;

    @Autowired
    public ControllerGeli(DependencyConfig dependencyConfig, Helper hlp, GeliItemService geliItemService) {
        this.cacheCookies = dependencyConfig.getCacheCookies();
        this.cacheMaster = dependencyConfig.getCacheMaster();
        this.hlp = hlp;
        this.geliItemService = geliItemService;
    }

    public void handleRequestProperties(HttpServletRequest request) {
        this.objRequestProperties = this.hlp.handleRequestProperties(request);
        this.response = (ResponseEntity<byte[]>) objRequestProperties.get("response");
        this.query = objRequestProperties.get("query").toString();
        this.objParams = (HashMap) objRequestProperties.get("objParams");
    }

    public ResponseEntity<byte[]> getItem(HttpServletRequest request) {
        this.handleRequestProperties(request);
        JsonObject jObjResponse = ConstantMessage.Pesan.getFailed.pesan();
        jObjResponse.addProperty("rc", "01");
        jObjResponse.addProperty("message", "Failed");
        try {
            List<Map<String, Object>> listItem = this.geliItemService.getItem(objParams);
            JsonArray jArrItem = new JsonArray();
            for(int i = 0 ; i < listItem.size(); i++){
                JsonObject jsonObjItem = new JsonObject();
                Map<String, Object> entityItem = (Map<String, Object>) listItem.get(i);
                jsonObjItem.addProperty("name", entityItem.get("name").toString());
                jsonObjItem.addProperty("price", entityItem.get("price").toString());
                jsonObjItem.addProperty("stock", entityItem.get("stock").toString());
                jsonObjItem.addProperty("size", entityItem.get("size").toString());
                jsonObjItem.addProperty("colour", entityItem.get("colour").toString());
                jsonObjItem.addProperty("unit", entityItem.get("unit").toString());
                jArrItem.add(jsonObjItem);
            }
            jObjResponse.add("data", jArrItem);
            jObjResponse.addProperty("rc", "00");
            jObjResponse.addProperty("message", "success");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", String.valueOf(MediaType.TEXT_HTML));
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
