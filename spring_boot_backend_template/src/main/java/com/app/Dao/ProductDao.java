package com.app.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.app.Entities.Product;
import com.app.dto.ApiResponse;
import com.app.Entities.Category;
import java.util.List;
import java.util.Optional;

public interface ProductDao extends JpaRepository<Product, Long> {

     List<Product> findByCategoryName(Category categoryname);
     
     @Query("select p from Product p where p.price between :lowerprice and :upperprice")
     List<Product> findByPrice(@Param("lowerprice") int low,@Param("upperprice") int high );     
}
