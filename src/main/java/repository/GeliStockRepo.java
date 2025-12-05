package repository;

import entity.EntityStock;
import entity.EntityStockResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeliStockRepo extends JpaRepository<EntityStock, Long> {

    @Query("SELECT new entity.EntityStockResponse(a.stockId, a.value) " +
            "FROM entity.EntityStock a ")
    List<EntityStockResponse> getStock(Pageable pageable);

}
