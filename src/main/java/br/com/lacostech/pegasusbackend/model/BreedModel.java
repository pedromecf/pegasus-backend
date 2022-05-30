package br.com.lacostech.pegasusbackend.model;

import br.com.lacostech.pegasusbackend.model.entities.Breed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BreedModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String petType;

    public BreedModel(final Breed entity) {
        if (Objects.nonNull(entity)) {
            BeanUtils.copyProperties(entity, this);

            this.petType = entity.getPetType().getDescription();
        }
    }

}
