package com.app.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.app.Entities.ProductVariant;
import com.app.dto.ProdFilterReqDTO;
import com.app.dto.ProductReqDTO;
import com.app.dto.ProductResDTO;

@Service
@Transactional
public interface ProductService {
	
	public List<ProductVariant> getProductsByFilter(ProdFilterReqDTO prod);

	String addProduct(ProductReqDTO prods);
}
