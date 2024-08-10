package com.app.Services;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.app.Entities.ProductVariant;
import com.app.dto.ProdFilterReqDTO;
import com.app.dto.ProductDTO;

@Service
@Transactional
public interface ProductService {
	
	public List<ProductVariant> getProductsByFilter(ProdFilterReqDTO prod);

	public String addProduct(ProductDTO prods);
	
}
