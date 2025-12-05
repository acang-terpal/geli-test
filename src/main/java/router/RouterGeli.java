package router;

import configuration.DependencyConfig;
import helper.Helper;
import jakarta.servlet.http.HttpServletRequest;
import net.sf.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component("routerMakrotik")
@Scope("prototype")
@Order(4)
public class RouterGeli {
    private final Cache cacheMaster;
    private final Cache cacheCookies;
    private final String uuid;
    private final Helper hlp;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public RouterGeli(DependencyConfig dependencyConfig, Helper hlp) {
        this.cacheCookies = dependencyConfig.getCacheCookies();
        this.cacheMaster = dependencyConfig.getCacheMaster();
        this.hlp = hlp;
        this.uuid = UUID.randomUUID().toString(); // Assign a unique ID on creation
        //System.out.println("PrototypeBean instance created with UUID: " + uuid);
    }

    public String getUniqueId() {
        return uuid;
    }

    public ResponseEntity<byte[]> route(HttpServletRequest request) {
        ResponseEntity<byte[]> response = new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        try {
            String fullUrl = request.getRequestURI();
            String fullUri = request.getRequestURI().substring(1);
            String baseUrl = request.getRequestURL().toString().replace(fullUrl, "");
            String data = "Internal Error";
            byte[] dataOut = data.getBytes(StandardCharsets.UTF_8);
            String[] uriSplit = fullUri.split("/");
            if(!uriSplit[0].equals("geli")){
                return response;
            }
            response = this.hlp.routeDefaultHandler(request);
            if (response.getStatusCodeValue() == 200) {
                return response;
            } else {
                String controller = uriSplit[1];
                String methode = uriSplit[2];

                //using spring dependency injection
                Object controllerObject = this.applicationContext.getBean(controller);
                //make sure class is statefull not singleton
                //Method getUniqueId = controllerObject.getClass().getMethod("getUniqueId");
                //System.out.println(methode + " Unique ID: " + getUniqueId.invoke(controllerObject));
                Class<?>[] paramTypes = {HttpServletRequest.class};
                Method callControllerMethod = controllerObject.getClass().getMethod(methode, paramTypes);
                response = (ResponseEntity<byte[]>) callControllerMethod.invoke(controllerObject, request);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
