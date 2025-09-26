package vn.iotstar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.iotstar.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByProductNameContaining(String name);

	Page<Product> findByProductNameContaining(String name, Pageable pageable);
}
