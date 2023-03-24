package com.algrince.finaltask.validators;

import com.algrince.finaltask.models.Product;
import com.algrince.finaltask.models.ProductProperty;
import com.algrince.finaltask.services.ProductPropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductPropertyValidator implements Validator {

    private final ProductPropertiesService productPropertiesService;

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductProperty.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductProperty productProperty = (ProductProperty) target;
        Optional<ProductProperty> productPropertyFromDB =
                productPropertiesService.loadByValue(productProperty.getPropertyValue());

        if (productPropertyFromDB.isPresent()) {
            errors.rejectValue(
                    "productProperty",
                    "productProperty.propertyValue.exists",
                    "The property value with this value already exists");
        }

    }
}