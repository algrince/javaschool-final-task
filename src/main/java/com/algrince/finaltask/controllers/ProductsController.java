package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.ProductsDTO;
import com.algrince.finaltask.models.Product;
import com.algrince.finaltask.services.ProductsService;
import com.algrince.finaltask.utils.DTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductsController {

    private final ProductsService productsService;
    private final DTOMapper dtoMapper;

    @GetMapping
    public Page<ProductsDTO> getProducts(
            @RequestParam(required = false) Long category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) Double minprice,
            @RequestParam(required = false) Double maxprice) {
        Page<Product> products = productsService.selectProducts(
                category,
                page, size,
                sortField, sortDir,
                minprice, maxprice);
        return dtoMapper.mapPage(products, ProductsDTO.class);
    }

    @PostMapping
    public ResponseEntity<Object> addProduct(@Valid @RequestBody ProductsDTO productsDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }

        Product product = dtoMapper.mapClass(productsDTO, Product.class);
        productsService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductsDTO> getProduct (@PathVariable("id") Long id) {
        Product foundProduct = productsService.findById(id);
        ProductsDTO foundProductDTO = dtoMapper.mapClass(foundProduct, ProductsDTO.class);
        // ERROR if loading product without category
        foundProductDTO.setCategory(foundProduct.getCategory());
        return ResponseEntity.ok().body(foundProductDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductsDTO> updateProduct (
            @PathVariable(value = "id") Long productId,
            @Valid @RequestBody ProductsDTO productsDTO) {
        Product foundProduct = productsService.findById(productId);
        dtoMapper.mapProperties(productsDTO, foundProduct);
        productsService.save(foundProduct);
        ProductsDTO newProductDTO = dtoMapper.mapClass(foundProduct, ProductsDTO.class);
        return ResponseEntity.ok().body(newProductDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct (@PathVariable(value = "id") Long productId) {
        Product productToDelete = productsService.findById(productId);
        productsService.softDelete(productToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
