package rest_resource;

import configuration.DependencyConfig;
import jakarta.servlet.http.HttpServletRequest;
import net.sf.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import router.RouterGeli;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
//@RequestMapping("/")
public class ResourceGeli {
    private final ExecutorService nonBlockingService = Executors.newCachedThreadPool();
    private final Cache cacheCookies;
    private final Cache cacheMaster;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public ResourceGeli(DependencyConfig dependencyConfig) {
        this.cacheCookies = dependencyConfig.getCacheCookies();
        this.cacheMaster = dependencyConfig.getCacheMaster();
    }

    @RequestMapping(path = "/geli/**")
    public ResponseEntity<byte[]> index(HttpServletRequest request) {
        ResponseEntity<byte[]> response = ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        try {
            RouterGeli routerGeli = this.applicationContext.getBean(RouterGeli.class);
            //System.out.println("routerGeli Unique ID: " + routerGeli.getUniqueId());
            response = routerGeli.route(request);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
        return response;
    }
}
