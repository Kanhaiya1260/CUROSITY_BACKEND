package com.app.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.app.Entities.ProductVariant;
import com.app.Entities.Size;
import com.app.dto.ApiResponse;

import java.lang.String;
import java.util.List;
import java.lang.Long;

public interface ProductVariantDao extends JpaRepository<ProductVariant, Long> {
	
	List<ProductVariant> findByColor(String color);
	
	@Query("SELECT p FROM ProductVariant p WHERE p.product.pid = :pid")
    List<ProductVariant> findBypid(@Param("pid") Long pid);

	@Query("SELECT p FROM ProductVariant p WHERE p.imgid IN :imgids")
	List<ProductVariant> findByImgid(@Param("imgids") List<Long> prods);
	
	@Query("SELECT p FROM ProductVariant p WHERE p.user.uid = :uid")
    List<ProductVariant> findAllProductVariant(@Param("uid") Long uid);
	

}
