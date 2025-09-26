package vn.iotstar.service.impl;

import vn.iotstar.entity.Category;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.service.ICategoryService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

  private final CategoryRepository repo;
  public CategoryServiceImpl(CategoryRepository repo){ this.repo = repo; }

  public List<Category> findAll(){ return repo.findAll(); }
  public Optional<Category> findById(Long id){ return repo.findById(id); }
  public Category save(Category c){ return repo.save(c); }
  public void deleteById(Long id){ repo.deleteById(id); }
}

