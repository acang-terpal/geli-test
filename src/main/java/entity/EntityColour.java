package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}