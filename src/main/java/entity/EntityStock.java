package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "stockId")
    private List<EntityItem> entityItemSet = new ArrayList<>();;
}