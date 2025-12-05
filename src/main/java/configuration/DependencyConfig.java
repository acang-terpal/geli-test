package configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.DiskStoreConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration("dependencyConfig")
@Scope("singleton")
//@Scope("prototype") it will create new object every bean requested
@Order(1)
public class DependencyConfig {

    private final Environment env;
    private Cache cache;
    private Cache cacheCookies;
    private Cache cacheMaster;
    private String workDir;

    @Autowired
    public DependencyConfig(Environment env) {
        this.env = env;
        this.initCache();
    }

    public void initCache() {
        try {
            //--------------------------ehcache init--------------------------------------
            this.workDir = env.getProperty("workdir");
            DiskStoreConfiguration diskStoreConfiguration = new DiskStoreConfiguration();
            diskStoreConfiguration.setPath(workDir + File.separator + "cache");
            // Already created a configuration object ...
            net.sf.ehcache.config.Configuration cacheConfiguration = new net.sf.ehcache.config.Configuration();
            cacheConfiguration.addDiskStore(diskStoreConfiguration);
            //Create a CacheManager using custom configuration
            CacheManager cacheManager = CacheManager.create(cacheConfiguration);
            //--------------------------ehcache save the config entity------------------------
            this.cache = new Cache(" GeliCacheApp", 1, false, true, 0, 0);
            cacheManager.addCache(this.cache);
            HashMap<String, String> hashMapCart = new HashMap<>();
            this.cache.put(new Element("cart", hashMapCart));
            //--------------------------get cache and put new element-------------------------------------
//            this.cache = cacheManager.getCache(" GeliCacheApp");
//            this.cache.put(new Element("config", Main.conf));
//            Element config = this.cache.get("config");
//            this.entityConfig = (EntityConfig) config.getObjectValue();
            //-------------------------ehcache for cookies--------------------------
            //new Cache(workDir, maxElementsInMemory, overflow to disk, eternal, timeToLiveSeconds, timeToIdleSeconds)
            this.cacheCookies = new Cache(" GeliCacheAppCookies", 1000, true, false, 86400, 0);
            cacheManager.addCache(this.cacheCookies);

            //-------------------------ehcache for dataMaster--------------------------
            //new Cache(workDir, maxElementsInMemory, overflow to disk, eternal, timeToLiveSeconds, timeToIdleSeconds)
            this.cacheMaster = new Cache(" GeliCacheAppMaster", 1000, true, true, 0, 0);
            cacheManager.addCache(this.cacheMaster);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("driverClassName"));
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("user"));
        dataSource.setPassword(env.getProperty("password"));
        return dataSource;
    }

    @Bean("cache")
    public Cache getCache() {
        return this.cache;
    }

    @Bean("cacheCookies")
    public Cache getCacheCookies() {
        return this.cacheCookies;
    }

    @Bean("cacheMaster")
    public Cache getCacheMaster() {
        return this.cacheMaster;
    }

}
