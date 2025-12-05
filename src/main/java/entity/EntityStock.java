package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stock")
public class EntityStock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stock_id", nullable = false)
    private Long stockId;

    @Getter @Setter
    @Column(name = "value", nullable = false)
    private String value;
}