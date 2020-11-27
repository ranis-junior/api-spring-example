package com.teste.DesafioBento.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "stores")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Getter
@Setter
public class Store extends AbstractEntity {

    @NotBlank(message = "Nome não pode estar vazio.")
    @Length(max = 100, message = "Nome deve conter no máximo 100 caracteres.")
    @Column(unique = true)
    private String name;
    @Length(min = 8, max = 15, message = "Telefone deve conter entre 8 e 15 caracteres.")
    private String telephone;
    private String address;
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Product> products;
}
