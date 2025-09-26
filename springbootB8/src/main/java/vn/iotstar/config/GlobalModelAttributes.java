package vn.iotstar.config;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

  private final SiteInfoProperties owner;

  public GlobalModelAttributes(SiteInfoProperties owner) {
    this.owner = owner;
  }

  @ModelAttribute("owner")
  public SiteInfoProperties owner() {
    // Đưa biến 'owner' vào Model cho TẤT CẢ các view Thymeleaf
    return owner;
  }
}
