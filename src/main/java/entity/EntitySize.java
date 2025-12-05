package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "size")
public class EntitySize {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "size_id", nullable = false, updatable = false)
    private Long sizeId;

    @Getter @Setter
    @Column(name = "value", nullable = false)
    private String value;

    @OneToMany(mappedBy = "sizeId")
    private List<EntityItem> entityItemSet = new ArrayList<>();;
}