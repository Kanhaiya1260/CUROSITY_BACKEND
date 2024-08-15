package com.app.Controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.Entities.Category;
import com.app.Entities.ProductVariant;
import com.app.Entities.Size;
import com.app.Services.ProductService;
import com.app.dto.ApiResponse;
import com.app.dto.ProdFilterReqDTO;
import com.app.dto.ProductDTO;
import com.app.dto.ProductVariantDTO;
import com.app.dto.QuantityDto;

@RestController
@RequestMapping("/product")
@Validated
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
	
	@Autowired
	ProductService ps;
	
	@GetMapping("/filter")
	public ResponseEntity<?> getProductByFilter(
	        @RequestParam(required = false) String color,
	        @RequestParam(required = false) Size size,
	        @RequestParam(required = false) Category category,
	        @RequestParam(required = false) int[] price) {

	    // Create and populate the filter object
	    ProdFilterReqDTO filter = new ProdFilterReqDTO();
	    filter.setColor(color);
	    filter.setSize(size);
	    filter.setCategory(category);
	    filter.setPrice(price);

	    List<ProductVariantDTO> products = ps.getProductsByFilter(filter);
	    return ResponseEntity.ok(products);
	}
	@PostMapping("/addproduct")
	public ResponseEntity<?> login(@RequestBody @Valid ProductDTO p){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(ps.addProduct(p));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ApiResponse(e.getMessage()));
		}
	}
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@RequestParam Long ProductId){
		return ResponseEntity.ok(ps.deleteProductById(ProductId));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?>getProductByUserId(@RequestParam Long userId){
    	return  ResponseEntity.ok(ps.getAllProductsOfUser(userId));
    }
    @PatchMapping("/update")
    public ResponseEntity<?>updateProductQuantity(@RequestBody QuantityDto quantity){
    	return ResponseEntity.ok(ps.updateProductQuantity(quantity.imgid,quantity.stock));
    }
    
}
