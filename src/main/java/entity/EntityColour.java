package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "colour")
public class EntityColour {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "colour_id", nullable = false)
    private Long colourId;

    @Getter @Setter
    @Column(name = "value", nullable = false)
    private String value;

    @OneToMany(mappedBy = "colourId")
    private List<EntityItem> entityItemSet = new ArrayList<>();;
}