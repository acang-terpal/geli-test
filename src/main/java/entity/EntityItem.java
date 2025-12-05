package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "item")
public class EntityItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Getter @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter @Setter
    @Column(name = "size_id", nullable = false)
    private Long sizeId;

    @Getter @Setter
    @Column(name = "colour_id", nullable = false)
    private Long colourId;

    @Getter @Setter
    @Column(name = "stock_id", nullable = false)
    private Long stockId;

    @Getter @Setter
    @Column(name = "stock", nullable = false)
    private Long stock;

    @Getter @Setter
    @Column(name = "price", nullable = false)
    private BigDecimal price;
}