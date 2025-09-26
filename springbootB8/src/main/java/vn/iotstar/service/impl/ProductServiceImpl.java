package vn.iotstar.service.impl;



import vn.iotstar.entity.Product;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.service.IProductService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

  private final ProductRepository repo;
  public ProductServiceImpl(ProductRepository repo){ this.repo = repo; }

  public List<Product> findAll(){ return repo.findAll(); }
  public Optional<Product> findById(Long id){ return repo.findById(id); }
  public Product save(Product p){ return repo.save(p); }
  public void deleteById(Long id){ repo.deleteById(id); }
}
