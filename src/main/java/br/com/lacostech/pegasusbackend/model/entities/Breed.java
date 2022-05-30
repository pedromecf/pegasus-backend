package br.com.lacostech.pegasusbackend.model.entities;

import br.com.lacostech.pegasusbackend.model.enums.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_breed")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Breed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Breed breed = (Breed) o;
        return id.equals(breed.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
