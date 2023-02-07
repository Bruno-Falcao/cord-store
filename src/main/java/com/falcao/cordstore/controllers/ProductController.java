package com.falcao.cordstore.controllers;

import com.falcao.cordstore.models.Product;
import com.falcao.cordstore.services.ProductService;
import com.falcao.cordstore.utils.ResponseAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("peripheral")
public class ProductController {


    private final ProductService periphericalService;

    public ProductController(ProductService pheriphericalService) {
        this.periphericalService = pheriphericalService;
    }

    @PostMapping("/save_product")
    public ResponseEntity<Object> saveProduct(@RequestBody Product peripheral) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(periphericalService.saveProduct(peripheral))));

        } catch (Exception ex) {
            return errorResponse("Error saving product", ex);
        }
    }

    @PutMapping("/update_product")
    public ResponseEntity<Object> uptdateProduct(@RequestBody Product peripheral) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(periphericalService.updateProduct(peripheral))));

        } catch (Exception ex) {
            return errorResponse("Error updating product", ex);
        }
    }


    @GetMapping("/find_all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Object> findAllProducts() {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(periphericalService.findAllProducts())));

        } catch (Exception ex) {
            return errorResponse("Error finding products",ex);
        }
    }

    @GetMapping("/find_product")
    public ResponseEntity<Object> findProductById(@RequestParam(name = "id") String id) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(periphericalService.findProductById(id))));

        } catch (Exception ex) {
            return errorResponse("Error finding product",ex);
        }
    }

    @GetMapping("/find_by_visibility")
    public ResponseEntity<Object> findProductByVisibility() {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(periphericalService.findAllVisibleProducts())));

        } catch (Exception ex) {
            return errorResponse("Error finding products",ex);
        }
    }

    @GetMapping("/find_by_brand")
    public ResponseEntity<Object> findProductByBrand(@RequestParam(name = "brand") String brand) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(periphericalService.findAllProductsByBrand(brand))));

        } catch (Exception ex) {
            return errorResponse("Error finding products",ex);
        }
    }

    @GetMapping("/find_by_name")
    public ResponseEntity<Object> findProductByName(@RequestParam(name = "name") String name) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(periphericalService.findProductByName(name))));

        } catch (Exception ex) {
            return errorResponse("Error finding products",ex);
        }
    }

    @GetMapping("/find_by_category")
    public ResponseEntity<Object> findProductByCategories(@RequestParam(name = "category") int category) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(periphericalService.findProductByCategory(category))));

        } catch (Exception ex) {
            return errorResponse("Error finding products",ex);
        }
    }

    @PatchMapping("/delete_product")
    public ResponseEntity<Object> deleteProduct(@RequestParam(name = "id") String id) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(periphericalService.setProductVisibilityToFalse(id))));

        } catch (Exception ex) {
            return errorResponse("Error finding product",ex);
        }
    }

    private ResponseEntity<Object> errorResponse(String errorMsg, Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseAPI.getInstance(String.format(errorMsg, ex.getMessage()),
                        Arrays.stream(ex.getSuppressed()).map(Throwable::getMessage)
                                .toArray(String[]::new)));
    }
}
