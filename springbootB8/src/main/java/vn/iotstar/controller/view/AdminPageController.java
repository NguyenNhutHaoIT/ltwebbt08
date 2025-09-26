package vn.iotstar.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

  @GetMapping("/categories")
  public String categories() { return "category/list"; }

  @GetMapping("/categories/new")
  public String categoryForm() { return "category/form"; }

  @GetMapping("/products")
  public String products() { return "product/list"; }

  @GetMapping("/products/new")
  public String productForm() { return "product/form"; }
}
