package vn.iotstar.service;

import vn.iotstar.entity.Product;
import java.util.List;
import java.util.Optional;

public interface IProductService {
  List<Product> findAll();
  Optional<Product> findById(Long id);
  Product save(Product p);
  void deleteById(Long id);
}
