package com.teste.DesafioBento.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@Getter
@Setter
public class Product extends AbstractEntity {
    @NotBlank(message = "Nome não pode estar vazio")
    @Length(max = 45, message = "Nome deve conter no máximo 45 caracteres.")
    private String name;
    @NotBlank(message = "Código de barras não pode estar vazio")
    @Length(max = 43, message = "Código de barras deve conter no máximo 43 caracteres.")
    private String barCode;
    @Length(max = 75, message = "Código de barras deve conter no máximo 75 caracteres.")
    private String category;
    @NotNull(message = "Preço é obrigatório para o produto")
    private BigDecimal price;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_store")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Store store;
}
