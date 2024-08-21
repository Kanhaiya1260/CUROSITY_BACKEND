package com.app.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Dao.FilterDao;
import com.app.Dao.ProductDao;
import com.app.Dao.ProductVariantDao;
import com.app.Entities.Filter;
import com.app.Entities.Product;
import com.app.Entities.ProductVariant;
import com.app.Entities.Size;
import com.app.customException.ResourceNotFoundException;
import com.app.dto.ProductDescDTO;

@Service
@Transactional
public class ProductDescServiceImpl implements ProductDescService {
	@Autowired 
    private ProductDao prodao;

    @Autowired 
    private ProductVariantDao variant;
    
    @Autowired
    private FilterDao filter;
    
	@Override
public ProductDescDTO getProductDetails(Long imgid) {		
	    
        ProductVariant prodvar = variant.findById(imgid).orElseThrow(() -> new ResourceNotFoundException("Product Doesn't Exist"));
        Product prod = prodvar.getProduct();
        ProductDescDTO prodDetails = new ProductDescDTO();
		prodDetails.setProd(prod); 
		
            List<ProductVariant> variants = variant.findBypid(prod.getPid());
            List<Size> sizes = filter.findByColorAndimgid(prodvar.getColor(),imgid);
            List<Long> imgids = new ArrayList<Long>();
            for(ProductVariant fv : variants) {
            	imgids.add(fv.getImgid());
            }		
            prodDetails.setRating(prodvar.getRating());
            prodDetails.setImgIds(imgids);
            prodDetails.setSizes(sizes);
            prodDetails.setDate(LocalDate.now().plusDays(7));
            return prodDetails;
	}
	
	}


