package com.falcao.cordstore.services;

import com.falcao.cordstore.execeptions.NotFoundException;
import com.falcao.cordstore.models.Product;
import com.falcao.cordstore.models.enums.Catogories;
import com.falcao.cordstore.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * This method saves a product
     *
     * @param product
     * @return
     * @throws Exception
     */
    public String saveProduct(Product product) throws Exception {
        if (saveConstraints(product)) {
            productRepository.save(product);

            return "Product saved successfully";
        }
        throw new Exception("Error saving product");
    }


    /**
     * This method searches for the id of the received product and updates it
     *
     * @param product
     * @return
     */
    public String updateProduct(Product product) throws Exception {
        Optional<Product> productById = findProductById(product.getId());
        if (productById.isPresent()) {
            fieldsUpdate(product, productById);
            productRepository.save(product);

            return "Product updated successfully";
        }

        throw new Exception("Error updating product");
    }

    /**
     * This method receive a String and delete logically the product
     *
     * @param id
     * @return
     */
    public String setProductVisibilityToFalse(String id) {
        Optional<Product> product = findProductById(id);
        if (product.isPresent()) {
            product.get().setVisibility(false);

            productRepository.save(product.get());

            return "Visibility set as false successfully";
        }
        return null;
    }

    /**
     * This method finds all the products, regardless its visibility is true or false
     *
     * @return
     */
    public List<Product> findAllProducts() {
        List<Product> findAll = productRepository.findAll();
        isEmpty(findAll);

        return findAll;
    }

    /**
     * This method finds a specific product based on id
     *
     * @param id
     * @return
     */
    public Optional<Product> findProductById(String id) {
        return Optional.ofNullable(productRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Product not found")));
    }

    /**
     * This method search for all visible products
     *
     * @return
     */
    public List<Product> findAllVisibleProducts() {
        List<Product> allByVisibility = productRepository.findAllByVisibility();
        isEmpty(allByVisibility);

        return allByVisibility;
    }

    /**
     * This method search for all visible products of the specified brand
     *
     * @param brand
     * @return
     */
    public List<Product> findAllProductsByBrand(String brand) {
        List<Product> byBrand = productRepository.findByBrand(brand);
        isEmpty(byBrand);

        return byBrand;
    }
    public List<Product> findProductByName(String brand) {
        List<Product> byName = productRepository.findByProductName(brand);
        isEmpty(byName);

        return byName;
    }

    /**
     * This method find all products with the same category
     * @param category
     * @return
     */
    public List<Product> findProductByCategory(int category) {
        Catogories cat = Catogories.getCategory(category);
        List<Product> byCategory = productRepository.findByCategory(cat.toString());
        isEmpty(byCategory);

        return byCategory;
    }

    private void fieldsUpdate(Product product, Optional<Product> optionalProduct) {
        optionalProduct.get().setProductName(product.getProductName());
        optionalProduct.get().setBrand(product.getBrand());
        optionalProduct.get().setDescription(product.getDescription());
        optionalProduct.get().setValue(product.getValue());
        optionalProduct.get().setCategory(product.getCategory());
        optionalProduct.get().setVisibility(product.getVisibility());
        optionalProduct.get().setQuantity(product.getQuantity());
    }

    private static boolean saveConstraints(Product product) {
        return product != null && product.getId() == null;
    }

    private void isEmpty(List<Product> products) {
        if (products.isEmpty()) throw new NotFoundException("No products found");
    }
}
