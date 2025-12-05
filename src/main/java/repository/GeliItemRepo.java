package repository;

import entity.EntityItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GeliItemRepo extends JpaRepository<EntityItem, Long> {

    @Query(value = "select a.item_id, a.name, a.price, a.stock, a.colour_id, a.size_id, a.stock_id, b.value as size, c.value as colour, d.value as unit " +
            "from item a " +
            "left join size b on a.size_id = b.size_id " +
            "LEFT JOIN colour c on a.colour_id = c.colour_id " +
            "LEFT JOIN stock d on a.stock_id = d.stock_id " +
            "limit ?1 offset ?2 ;",
            nativeQuery = true)
    List<Map<String, Object>> getItem(String limit, String offset);

}
