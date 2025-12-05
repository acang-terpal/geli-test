package repository;

import entity.EntityItem;
import entity.EntitySize;
import entity.EntitySizeResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeliSizeRepo extends JpaRepository<EntitySize, Long> {

    @Query("SELECT new entity.EntitySizeResponse(a.sizeId, a.value) " +
            "FROM entity.EntitySize a ")
    List<EntitySizeResponse> getSize(Pageable pageable);
}
