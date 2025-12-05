package repository;

import entity.EntityItem;
import entity.EntityItemResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeliItemRepo extends JpaRepository<EntityItem, Long> {

    @Query("SELECT new entity.EntityItemResponse(a.itemId, a.name, a.price, a.stock, a.colourId, a.sizeId, a.stockId, b.value as size, c.value as colour, d.value as unit) " +
            "FROM entity.EntityItem a " +
            "LEFT JOIN entity.EntitySize b on a.sizeId = b.sizeId " +
            "LEFT JOIN entity.EntityColour c on a.colourId = c.colourId " +
            "LEFT JOIN entity.EntityStock d on a.stockId = d.stockId ")
    List<EntityItemResponse> getItem(Pageable pageable);

    @Query("SELECT new entity.EntityItemResponse(a.itemId, a.name, a.price, a.stock, a.colourId, a.sizeId, a.stockId, b.value as size, c.value as colour, d.value as unit) " +
            "FROM entity.EntityItem a " +
            "LEFT JOIN entity.EntitySize b on a.sizeId = b.sizeId " +
            "LEFT JOIN entity.EntityColour c on a.colourId = c.colourId " +
            "LEFT JOIN entity.EntityStock d on a.stockId = d.stockId " +
            "WHERE a.itemId = ?1 ")
    Optional<EntityItemResponse> getItemById(Long itemId);
}
