package com.algrince.finaltask.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    @NotEmpty(message = "Category name cannot be empty")
    @Length(max = 45, message = "The category name cannot be longer than 45 characters")
    private String name;

    private boolean isDeleted;

}
