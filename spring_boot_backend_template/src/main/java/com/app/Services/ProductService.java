package com.app.Services;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.app.Entities.ProductVariant;
import com.app.dto.ApiResponse;
import com.app.dto.ProdFilterReqDTO;
import com.app.dto.ProductDTO;
import com.app.dto.ProductVariantDTO;

@Service
@Transactional
public interface ProductService {
	
	public List<ProductVariantDTO> getProductsByFilter(ProdFilterReqDTO prod);

	public String addProduct(ProductDTO prods);
	
	public ApiResponse deleteProductById(Long productId);
	
	public List<ProductVariantDTO> getAllProductsOfUser(Long uid);
	
	public ApiResponse updateProductQuantity(Long imgId,int stock);
	
}
