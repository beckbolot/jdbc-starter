package com.beck;

import com.beck.dao.CategoryDao;
import com.beck.dao.ProductDao;
import com.beck.model.Category;
import com.beck.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductRunner {

    public static ProductDao productDao = ProductDao.getInstance();

    public static void main(String[] args) {
//        saveProduct(new Product("Apple 15",new BigDecimal(1500.00),1));
//        saveProduct(new Product("Banana",new BigDecimal(1.15),3));
        CategoryDao categoryDao = CategoryDao.getInstance();
        Category clothes = categoryDao.findById(2).get();
//        saveProduct(new Product("Man Shoes",new BigDecimal(100.00), clothes.getId()));


//        Product product = productDao.findById(2).get();
//        System.out.println(product);
//        if (product != null) {
//            product.setPrice(BigDecimal.valueOf(1.00));
//            productDao.updateProduct(product);
//        } else {
//            throw new ProductException("Product does not exist");
//        }

//        deleteProductById(3);
//        joinSql();
        List<Product> allProducts = getAllProducts();
        allProducts.forEach(System.out::println);


    }

    public static Product saveProduct(Product product) {
        product.setName(product.getName());
        product.setPrice(product.getPrice());
        product.setCategory(product.getCategory());
        productDao.save(product);
        return product;
    }

    public static void updateProduct(Product product) {
        Optional<Product> maybeProduct = productDao.findById(product.getId());
        maybeProduct.ifPresent(product1 -> {
            product1.setPrice(maybeProduct.get().getPrice());
            productDao.update(product1);
        });
    }

    public static void deleteProductById(Integer id){
        productDao.deleteById(id);
    }

    public static void joinSql(){
        productDao.joinExample();
    }

    public static List<Product> getAllProducts(){
        List<Product> allProduct = productDao.findAll();
        return allProduct;
    }


}
