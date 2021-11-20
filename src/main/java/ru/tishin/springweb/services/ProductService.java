package ru.tishin.springweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.model.Product;
import ru.tishin.springweb.repository.ProductDao;

import java.util.List;

@Service
public class ProductService {

    private ProductDao productDao;

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getCatalog() {
        return productDao.findAll();
    }

    public void deleteById(Long id) {
        productDao.deleteById(id);
    }

    public void changeCostById(Long id, Integer delta) {
        Product product = productDao.findById(id);
        product.setCost(product.getCost() + delta);
        productDao.saveOrUpdate(product);
    }
}
