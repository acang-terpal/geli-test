package configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.EntityItem;
import entity.EntityItemResponse;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import service.GeliItemService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration("transactionalPayment")
@Scope("singleton")
public class TransactionalPayment {

    private final Environment env;
    private final Cache cache;
    private final GeliItemService geliItemService;

    @Autowired
    public TransactionalPayment(Environment env, DependencyConfig dependencyConfig, GeliItemService geliItemService) {
        this.cache = dependencyConfig.getCache();
        this.geliItemService = geliItemService;
        this.env = env;
    }

    public synchronized boolean doPayment(){
        Element cacheElement = this.cache.get("cart");
        HashMap<String, String> hashMapCart = (HashMap<String, String>) cacheElement.getObjectValue();

        //check first if stock is sufficient
        for (Map.Entry<String, String> entry : hashMapCart.entrySet()) {
            String itemId_ = entry.getKey();
            String amount_ = entry.getValue();
            EntityItem itemEntity = new EntityItem();
            itemEntity.setItemId(Long.parseLong(itemId_));
            Optional<EntityItemResponse> itemEntityResponseOptional = this.geliItemService.getItemById(itemEntity);
            if (itemEntityResponseOptional.isPresent()) {
                EntityItemResponse itemEntityResponse = itemEntityResponseOptional.get();
                if (itemEntityResponse.getStock() - Long.parseLong(amount_) < 0) {
                    return false;
                }
            }
        }

        //if sufficient stock
        for (Map.Entry<String, String> entry : hashMapCart.entrySet()) {
            String itemId_ = entry.getKey();
            String amount_ = entry.getValue();
            EntityItem itemEntity = new EntityItem();
            itemEntity.setItemId(Long.parseLong(itemId_));
            Optional<EntityItemResponse> itemEntityResponseOptional = this.geliItemService.getItemById(itemEntity);
            if (itemEntityResponseOptional.isPresent()) {
                EntityItemResponse itemEntityResponse = itemEntityResponseOptional.get();
                if(itemEntityResponse.getStock() - Long.parseLong(amount_) < 0){
                    break;
                }
                EntityItem entityItem = new EntityItem();
                entityItem.setItemId(itemEntityResponse.getItemId());
                entityItem.setStock(itemEntityResponse.getStock() - Long.parseLong(amount_));
                HashMap<String, String> objParams = new HashMap<>();
                objParams.put("itemId", itemEntityResponse.getItemId().toString());
                objParams.put("colourId", itemEntityResponse.getColourId().toString());
                objParams.put("sizeId", itemEntityResponse.getSizeId().toString());
                objParams.put("stockId", itemEntityResponse.getStockId().toString());
                objParams.put("name", itemEntityResponse.getName().toString());
                objParams.put("price", itemEntityResponse.getPrice().toString());
                Long lastAmount = itemEntityResponse.getStock() - Long.parseLong(amount_);
                objParams.put("stock", String.valueOf(lastAmount));
                this.geliItemService.updateItem(objParams);
            }
        }
        hashMapCart = new HashMap<>();
        this.cache.put(new Element("cart", hashMapCart));
        return true;
    }
}
