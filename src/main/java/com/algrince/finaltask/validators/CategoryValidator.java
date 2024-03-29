package com.algrince.finaltask.validators;

import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.services.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
 * Additional server-side validation for categories. Prevents
 * saving to the database of two categories with the same name
 */
@Component
@RequiredArgsConstructor
public class CategoryValidator implements Validator {

    private final CategoriesService categoriesService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Category.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Category category = (Category) target;
        Optional<Category> categoryFromDB = categoriesService.loadByName(category.getName());

        if (categoryFromDB.isPresent()) {
            errors.rejectValue(
                    "name",
                    "category.name.exists",
                    "The category with this name already exists");
        }
    }
}
