package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class EntitySizeResponse {
    @Getter
    private Long sizeId;

    @Getter
    private String value;

    public EntitySizeResponse(Long sizeId, String value){
        this.sizeId = sizeId;
        this.value = value;
    }
}