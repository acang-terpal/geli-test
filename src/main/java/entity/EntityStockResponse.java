package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class EntityStockResponse {
    @Getter
    private Long stockId;
    @Getter
    private String value;

    public EntityStockResponse(Long stockId, String value){
        this.stockId = stockId;
        this.value = value;
    }
}