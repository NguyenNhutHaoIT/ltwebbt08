package vn.iotstar.service;

import vn.iotstar.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
  List<Category> findAll();
  Optional<Category> findById(Long id);
  Category save(Category c);
  void deleteById(Long id);
}
