package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.CategoryDTO;
import com.algrince.finaltask.dto.ProductDTO;
import com.algrince.finaltask.exceptions.InvalidFormException;
import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.models.Product;
import com.algrince.finaltask.models.ProductProperty;
import com.algrince.finaltask.services.ProductsService;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.validators.AccessValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;
    private final AccessValidator accessValidator;
    private final DTOMapper dtoMapper;

    @GetMapping
    public Page<ProductDTO> getProductsList(
            @RequestParam(required = false) Long category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) List<Long> prValues) {

        boolean isAdmin = accessValidator.authUserIsAdmin();
        Page<Product> products = productsService.selectProducts(
                category,
                page, size,
                sortField, sortDir,
                minPrice, maxPrice,
                prValues, isAdmin);

        return dtoMapper.mapPage(products, ProductDTO.class);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Object> addProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }

        Product product = dtoMapper.mapClass(productDTO, Product.class);
        productsService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProduct(
            @PathVariable("id") Long id) {
        boolean isAdmin = accessValidator.authUserIsAdmin();

        Product foundProduct = productsService.findById(id, isAdmin);
        ProductDTO foundProductDTO = dtoMapper.mapClass(foundProduct, ProductDTO.class);

        Category foundProductCategory = foundProduct.getCategory();
        foundProductDTO.setCategory(dtoMapper.mapClass(foundProductCategory, CategoryDTO.class));
        return ResponseEntity.ok().body(foundProductDTO);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<String> updateProduct(
            @PathVariable(value = "id") Long productId,
            @Valid @RequestBody ProductDTO productDTO) {
        Product foundProduct = productsService.findById(productId);

        // For correct insertion to the DB new Category and ProductProperties must be added
        foundProduct.setCategory(new Category());
        foundProduct.setPropertyValues(new ArrayList<ProductProperty>());

        dtoMapper.mapProperties(productDTO, foundProduct);
        productsService.save(foundProduct);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<String> deleteProduct(
            @PathVariable(value = "id") Long productId) {
        Product productToDelete = productsService.findById(productId);
        productsService.softDelete(productToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> restoreProduct(
            @PathVariable(value = "id") Long productId) {
        // Adds possibility to restore soft-deleted product

        Product productToRestore = productsService.findById(productId);
        productsService.restore(productToRestore);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
