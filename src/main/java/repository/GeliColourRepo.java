package repository;

import entity.EntityColour;
import entity.EntityColourResponse;
import entity.EntityItemResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeliColourRepo extends JpaRepository<EntityColour, Long> {

    @Query("SELECT new entity.EntityColourResponse(a.colourId, a.value) " +
            "FROM entity.EntityColour a ")
    List<EntityColourResponse> getColour(Pageable pageable);

    @Query("SELECT new entity.EntityColourResponse(a.colourId, a.value) " +
            "FROM entity.EntityColour a " +
            "WHERE a.colourId = ?1")
    EntityColourResponse getColourById(Long colourId);
}
