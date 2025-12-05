package configuration;

import entity.EntityColour;
import entity.EntityItem;
import entity.EntitySize;
import entity.EntityStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import repository.GeliColourRepo;
import repository.GeliItemRepo;
import repository.GeliSizeRepo;
import repository.GeliStockRepo;

import java.math.BigDecimal;

@Configuration("dataSeeder")
public class DataSeeder {

    @Autowired
    public DataSeeder(GeliItemRepo geliItemRepo, GeliSizeRepo geliSizeRepo, GeliColourRepo geliColourRepo, GeliStockRepo geliStockRepo){
        if (geliSizeRepo.count() == 0) {
            EntitySize entitySize = new EntitySize();
            entitySize.setValue("S");
            geliSizeRepo.save(entitySize);

            entitySize = new EntitySize();
            entitySize.setValue("M");
            geliSizeRepo.save(entitySize);

            entitySize = new EntitySize();
            entitySize.setValue("L");
            geliSizeRepo.save(entitySize);

            entitySize = new EntitySize();
            entitySize.setValue("XL");
            geliSizeRepo.save(entitySize);
        }
        if (geliColourRepo.count() == 0) {
            EntityColour entityColour = new EntityColour();
            entityColour.setValue("red");
            geliColourRepo.save(entityColour);

            entityColour = new EntityColour();
            entityColour.setValue("black");
            geliColourRepo.save(entityColour);

            entityColour = new EntityColour();
            entityColour.setValue("green");
            geliColourRepo.save(entityColour);

            entityColour = new EntityColour();
            entityColour.setValue("yellow");
            geliColourRepo.save(entityColour);
        }
        if (geliStockRepo.count() == 0) {
            EntityStock entityStock = new EntityStock();
            entityStock.setValue("pcs");
            geliStockRepo.save(entityStock);

            entityStock = new EntityStock();
            entityStock.setValue("cartoon");
            geliStockRepo.save(entityStock);

            entityStock = new EntityStock();
            entityStock.setValue("quintal");
            geliStockRepo.save(entityStock);

            entityStock = new EntityStock();
            entityStock.setValue("ton");
            geliStockRepo.save(entityStock);
        }
        if (geliItemRepo.count() == 0) {
            EntityItem entityItem = new EntityItem();
            entityItem.setColourId(Long.parseLong("1"));
            entityItem.setSizeId(Long.parseLong("1"));
            entityItem.setStockId(Long.parseLong("1"));
            entityItem.setName("balenciaga");
            entityItem.setPrice(BigDecimal.valueOf(Double.parseDouble("10000")));
            entityItem.setStock(Long.parseLong("10"));
            geliItemRepo.save(entityItem);

            entityItem = new EntityItem();
            entityItem.setColourId(Long.parseLong("2"));
            entityItem.setSizeId(Long.parseLong("2"));
            entityItem.setStockId(Long.parseLong("2"));
            entityItem.setName("balenciaga-2");
            entityItem.setPrice(BigDecimal.valueOf(Double.parseDouble("15000")));
            entityItem.setStock(Long.parseLong("20"));
            geliItemRepo.save(entityItem);

            entityItem = new EntityItem();
            entityItem.setColourId(Long.parseLong("3"));
            entityItem.setSizeId(Long.parseLong("3"));
            entityItem.setStockId(Long.parseLong("3"));
            entityItem.setName("balenciaga-3");
            entityItem.setPrice(BigDecimal.valueOf(Double.parseDouble("20000")));
            entityItem.setStock(Long.parseLong("30"));
            geliItemRepo.save(entityItem);

            entityItem = new EntityItem();
            entityItem.setColourId(Long.parseLong("4"));
            entityItem.setSizeId(Long.parseLong("4"));
            entityItem.setStockId(Long.parseLong("4"));
            entityItem.setName("balenciaga-4");
            entityItem.setPrice(BigDecimal.valueOf(Double.parseDouble("25000")));
            entityItem.setStock(Long.parseLong("30"));
            geliItemRepo.save(entityItem);
        }
    }


}
