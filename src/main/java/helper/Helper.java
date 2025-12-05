/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import configuration.DependencyConfig;
import jakarta.servlet.http.HttpServletRequest;
import net.sf.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Hasan
 */
@Component("Helper")
@Scope("prototype")
public class Helper {
    private final Cache cacheMaster;
    private Cache cacheCookies;

    @Autowired
    public Helper(DependencyConfig dependencyConfig) {
        this.cacheMaster = dependencyConfig.getCacheMaster();
        this.cacheCookies = dependencyConfig.getCacheCookies();
    }

    public ResponseEntity<byte[]> routeDefaultHandler(HttpServletRequest request) {
        ResponseEntity<byte[]> response = new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        try {
            String fullUrl = request.getRequestURI();
            String fullUri = request.getRequestURI().substring(1);
            String baseUrl = request.getRequestURL().toString().replace(fullUrl, "");
            String[] uriSplit = fullUri.split("/");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
        return response;
    }

    public HashMap handleRequestProperties(HttpServletRequest request) {
        String data = "Internal Error";
        byte[] dataOut = data.getBytes(StandardCharsets.UTF_8);
        //ResponseEntity<byte[]> response = ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        ResponseEntity<byte[]> response = new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

        String fullUri = request.getRequestURI().substring(1);
        String fullUrl = request.getRequestURI();
        String query = (Objects.isNull(request.getQueryString())) ? "" : request.getQueryString();
        String baseUrl = "";
        if (!fullUri.equals("")) {
            baseUrl = request.getRequestURL().toString().replace(fullUrl, "");
        } else {
            fullUrl = request.getRequestURL().toString();
            baseUrl = fullUrl.substring(0, fullUrl.length() - 1);
        }
        String hostPort = String.valueOf(request.getServerPort());

//        System.out.println("fullUri : " + fullUri);
//        System.out.println("fullUrl : " + fullUrl);
//        System.out.println("query : " + query);

        //to convert query get and post param http to hashmap
        HashMap<String, Object> objParams = new HashMap<>();
        for (Object params : Collections.list(request.getParameterNames())) {
            // Whatever you want to do with your map
            // Key : params
            // Value : httpServletRequest.getParameter(params)
//            System.out.println("params : " + params);
//            System.out.println("Value : " + request.getParameter(params));
            objParams.put((String) params, request.getParameter((String) params));
        }

        String[] uriSplit = fullUri.split("/");
//        for (String uri : uriSplit) {
//            System.out.println("uriSplit : " + uri);
//        }
        String webroot = uriSplit[0];
        String controller = uriSplit[1];
        String methode = uriSplit[2];
        String view = (uriSplit.length >= 4) ? uriSplit[3] : "";
        String webRootUrl = request.getRequestURL().toString().replace(fullUrl, "") + "/" + webroot + "/";

        HashMap<String, Object> objRequestProperties = new HashMap<>();
        objRequestProperties.put("response", response);
        objRequestProperties.put("fullUri", fullUri);
        objRequestProperties.put("fullUrl", fullUrl);
        objRequestProperties.put("query", query);
        objRequestProperties.put("objParams", objParams);
        objRequestProperties.put("webroot", webroot);
        objRequestProperties.put("controller", controller);
        objRequestProperties.put("methode", methode);
        objRequestProperties.put("view", view);
        objRequestProperties.put("webRootUrl", webRootUrl);
        objRequestProperties.put("baseUrl", baseUrl);
        objRequestProperties.put("hostPort", hostPort);

        return objRequestProperties;
    }

    public HashMap hashStringToHashMap(String hashStr) {
        StringBuilder sb = new StringBuilder(hashStr);
        sb.deleteCharAt(0); // Remove the first character
        sb.deleteCharAt(sb.length() - 1); // Remove the last character
        String modifiedString = sb.toString();
        HashMap<String, Object> hashObj = new HashMap<>();
        String[] pairs = modifiedString.split(",(?![^{]*})"); // Split by comma not inside braces
        for (int i = 0; i < pairs.length; i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split("=", 2); // Split by first '=' only
            if (keyValue.length == 2) {
                if (String.valueOf(keyValue[1]).startsWith("{") && String.valueOf(keyValue[1]).endsWith("}")) {
                    // Recursively parse nested map
                    hashObj.put(String.valueOf(keyValue[0]).replaceAll("\\s+", ""), hashStringToHashMap(keyValue[1]));
                } else {
                    hashObj.put(String.valueOf(keyValue[0]).replaceAll("\\s+", ""), String.valueOf(keyValue[1]));
                }
            }
        }
        return hashObj;
    }

    public Map<String, Object> convertObjectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields(); // Get all declared fields (public, private, protected)

        for (Field field : fields) {
            try {
                field.setAccessible(true); // Allow access to private fields
                if(field.get(obj) == null){
                    map.put(field.getName(), "");
                } else {
                    map.put(field.getName(), field.get(obj)); // Get field name as key, field value as value
                }
                //System.out.println(field.getName());
                //System.out.println(field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // Handle potential access issues
            }
        }
        return map;
    }
}
