package com.app.Dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.app.Entities.Filter;
import com.app.Entities.ProductVariant;
import com.app.Entities.Size;

public interface FilterDao extends JpaRepository<Filter, Long> {

	@Query("SELECT f.imgid FROM Filter f WHERE (:color IS NULL OR f.color = :color) AND (:size IS NULL OR f.size = :size)")
	List<Long> findByColorAndSize(@Param("color") String color, @Param("size") Size size);
	
	@Query("SELECT f.size FROM Filter f WHERE f.color = :color AND f.imgid = :imgid")
	List<Size> findByColorAndimgid(String color,Long imgid);
		
	}
