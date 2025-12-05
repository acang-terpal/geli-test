package entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class EntityItemResponse {

    @Getter
    private Long itemId;
    @Getter
    private String name;
    @Getter
    private Long sizeId;
    @Getter
    private Long colourId;
    @Getter
    private Long stockId;
    @Getter @Setter
    private Long stock;
    @Getter
    private BigDecimal price;
    @Getter
    private String size;
    @Getter
    private String colour;
    @Getter
    private String unit;

    public EntityItemResponse(Long itemId, String name, BigDecimal price, Long stock, Long colourId, Long sizeId, Long stockId, String size, String colour, String unit) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.colourId = colourId;
        this.sizeId = sizeId;
        this.stockId = stockId;
        this.size = size;
        this.colour = colour;
        this.unit = unit;
    }
}