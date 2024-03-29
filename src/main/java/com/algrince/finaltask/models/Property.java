package com.algrince.finaltask.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
@FilterDef(name = "deletedPropertyFilter", parameters = @ParamDef(
        name = "isDeleted",
        type = org.hibernate.type.descriptor.java.BooleanJavaType.class))
@Filter(name = "deletedPropertyFilter", condition = "deleted = :isDeleted")
public class Property {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    @NotEmpty
    @Length(max = 45)
    private String name;

    @Column(name = "deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "property")
    private List<ProductProperty> propertyValues;
}
