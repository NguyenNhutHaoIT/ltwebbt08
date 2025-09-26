package vn.iotstar.controller.api;

import vn.iotstar.entity.Category;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.storage.IStorageService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@RestController
@RequestMapping("/api/category")
public class CategoryApiController {

	private final ICategoryService categoryService;
	private final IStorageService storage;

	public CategoryApiController(ICategoryService s, IStorageService st) {
		this.categoryService = s;
		this.storage = st;
	}

	@GetMapping
	public List<Category> all() {
		return categoryService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> one(@PathVariable Long id) {
		return categoryService.findById(id).<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found"));
	}

	@PostMapping(path = "/addCategory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> add(@RequestParam String categoryName,
			@RequestParam(required = false) MultipartFile icon) {
		Category c = new Category();
		c.setCategoryName(categoryName);
		if (icon != null && !icon.isEmpty()) {
			String fn = storage.getStorageFilename(icon, UUID.randomUUID().toString());
			storage.store(icon, fn);
			c.setIcon(fn);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(c));
	}

	@PutMapping(path = "/updateCategory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> update(@RequestParam Long categoryId, @RequestParam String categoryName,
			@RequestParam(required = false) MultipartFile icon) {
		var opt = categoryService.findById(categoryId);
		if (opt.isEmpty())
			return ResponseEntity.badRequest().body("Không tìm thấy Category");
		Category c = opt.get();
		c.setCategoryName(categoryName);
		if (icon != null && !icon.isEmpty()) {
			String fn = storage.getStorageFilename(icon, UUID.randomUUID().toString());
			storage.store(icon, fn);
			c.setIcon(fn);
		}
		return ResponseEntity.ok(categoryService.save(c));
	}

	@DeleteMapping("/deleteCategory")
	public ResponseEntity<?> delete(@RequestParam Long categoryId) {
		if (categoryService.findById(categoryId).isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tồn tại");
		categoryService.deleteById(categoryId);
		return ResponseEntity.noContent().build();
	}
}
