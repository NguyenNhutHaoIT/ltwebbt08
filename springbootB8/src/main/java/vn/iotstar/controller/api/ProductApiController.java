package vn.iotstar.controller.api;

import vn.iotstar.dto.ProductModel;
import vn.iotstar.entity.Product;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;
import vn.iotstar.storage.IStorageService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/api/product")
public class ProductApiController {

	private final IProductService productService;
	private final ICategoryService categoryService;
	private final IStorageService storage;

	public ProductApiController(IProductService ps, ICategoryService cs, IStorageService st) {
		this.productService = ps;
		this.categoryService = cs;
		this.storage = st;
	}

	@GetMapping
	public List<Product> all() {
		return productService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> one(@PathVariable Long id) {
		return productService.findById(id).<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found"));
	}

	@PostMapping(path = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> add(ProductModel m) {
		var cat = categoryService.findById(m.getCategoryId());
		if (cat.isEmpty())
			return ResponseEntity.badRequest().body("Category không tồn tại");

		Product p = new Product();
		p.setProductName(m.getProductName());
		p.setQuantity(m.getQuantity());
		p.setUnitPrice(m.getUnitPrice());
		p.setDescription(m.getDescription());
		p.setDiscount(m.getDiscount());
		p.setStatus(m.getStatus());
		p.setCategory(cat.get());
		p.setCreateDate(new Timestamp(System.currentTimeMillis()));

		if (m.getImageFile() != null && !m.getImageFile().isEmpty()) {
			String fn = storage.getStorageFilename(m.getImageFile(), UUID.randomUUID().toString());
			storage.store(m.getImageFile(), fn);
			p.setImages(fn);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(p));
	}

	@PutMapping(path = "/updateProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> update(ProductModel m) {
		if (m.getProductId() == null)
			return ResponseEntity.badRequest().body("Thiếu productId");
		var opt = productService.findById(m.getProductId());
		if (opt.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tồn tại");
		Product p = opt.get();

		if (m.getProductName() != null)
			p.setProductName(m.getProductName());
		if (m.getQuantity() != null)
			p.setQuantity(m.getQuantity());
		if (m.getUnitPrice() != null)
			p.setUnitPrice(m.getUnitPrice());
		if (m.getDescription() != null)
			p.setDescription(m.getDescription());
		if (m.getDiscount() != null)
			p.setDiscount(m.getDiscount());
		if (m.getStatus() != null)
			p.setStatus(m.getStatus());
		if (m.getCategoryId() != null) {
			var cat = categoryService.findById(m.getCategoryId());
			if (cat.isEmpty())
				return ResponseEntity.badRequest().body("Category không tồn tại");
			p.setCategory(cat.get());
		}
		if (m.getImageFile() != null && !m.getImageFile().isEmpty()) {
			String fn = storage.getStorageFilename(m.getImageFile(), UUID.randomUUID().toString());
			storage.store(m.getImageFile(), fn);
			p.setImages(fn);
		}
		return ResponseEntity.ok(productService.save(p));
	}

	@DeleteMapping("/deleteProduct")
	public ResponseEntity<?> delete(@RequestParam Long productId) {
		if (productService.findById(productId).isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tồn tại");
		productService.deleteById(productId);
		return ResponseEntity.noContent().build();
	}
}
