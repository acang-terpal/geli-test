package repository;

import entity.EntityColour;
import entity.EntityItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeliColourRepo extends JpaRepository<EntityColour, Long> {

    @Query(value = "SELECT   " +
            " *    " +
            "FROM   " +
            " ms_account A " +
            "WHERE   " +
            " A.username = ?1  " +
            " AND A.password = ?2 ;",
            nativeQuery = true)
    List<EntityItem> getAccount(String userName, String password);

}
