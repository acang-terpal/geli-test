package entity;

import jakarta.persistence.Column;
import lombok.Getter;

public class EntityColourResponse {
    @Getter
    private Long colourId;

    @Getter
    @Column(name = "value", nullable = false)
    private String value;

    public EntityColourResponse(Long colourId, String value){
        this.colourId = colourId;
        this.value = value;
    }
}