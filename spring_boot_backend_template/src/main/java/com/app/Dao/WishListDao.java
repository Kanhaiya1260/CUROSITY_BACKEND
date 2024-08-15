package com.app.Dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.Entities.WishList;

public interface WishListDao extends JpaRepository<WishList, Long> {
	
	public List<WishList> findByuid(Long uid); 
	
	@Modifying
	@Query("delete from WishList p where p.imgid = :imgid and p.uid = :uid")
	int deleteByImgIdAndUid(@Param("imgid") Long imgid, @Param("uid") Long uid);

}
