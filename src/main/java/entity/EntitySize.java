package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "size")
public class EntitySize {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "size_id", nullable = false)
    private Long sizeId;

    @Getter @Setter
    @Column(name = "value", nullable = false)
    private String value;
}