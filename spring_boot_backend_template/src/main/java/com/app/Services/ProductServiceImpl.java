package com.app.Services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.Dao.FilterDao;
import com.app.Dao.ProductDao;
import com.app.Dao.ProductVariantDao;
import com.app.Dao.UserDao;
import com.app.Entities.Product;
import com.app.Entities.ProductVariant;
import com.app.Entities.User;
import com.app.customException.ResourceNotFoundException;
import com.app.dto.ApiResponse;
import com.app.dto.ProdFilterReqDTO;
import com.app.dto.ProductDTO;
import com.app.dto.ProductVariantDTO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    @Autowired 
    private ProductDao prod;

    @Autowired 
    private ProductVariantDao variant;

    @Autowired
    private FilterDao filter;
    
    @Autowired 
    private UserDao userDao;
    
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ProductVariantDTO> getProductsByFilter(ProdFilterReqDTO product) {
        List<Long> imgids = filter.findByColorAndSize(product.getColor(), product.getSize());
        List<Product> allProducts = (product.getCat() == null) ? prod.findAll() : prod.findByCategoryName(product.getCat());

        List<Product> filteredProducts = allProducts.stream()
            .filter(p -> product.getPrice() == null ||
                         (p.getPrice() >= product.getPrice()[0] && p.getPrice() <= product.getPrice()[1]))
            .collect(Collectors.toList());

        List<ProductVariantDTO> result = new ArrayList<>();

        for (Product p : filteredProducts) {
            List<ProductVariant> variants = variant.findBypid(p.getPid());

            List<ProductVariant> filteredVariants = (product.getColor()==null && product.getSize()==null) ? variants : 
                variants.stream()
                    .filter(v -> imgids.contains(v.getImgid()))
                    .collect(Collectors.toList());

            for(ProductVariant fv : filteredVariants) {
            	
            	result.add(mapper.map(fv,ProductVariantDTO.class));
            }
        }

        return result;
       }
    @Override
    public String addProduct(ProductDTO prods) {
        Product newProduct = prod.save(prods.getProduct());
        User currentUser = userDao.findById(prods.getUid())
        		.orElseThrow(() -> new ResourceNotFoundException("User dosn't exist"));
        if (prods.getVariants() != null && !prods.getVariants().isEmpty()) {
            List<ProductVariant> productVariants = prods.getVariants().stream().map(variantDTO -> {
                ProductVariant variant = new ProductVariant();
                variant.setColor(variantDTO.getColor());
                variant.setStock(variantDTO.getStock());
                variant.setImgid(variantDTO.getImgid());
                variant.setUser(currentUser);
                variant.setProduct(newProduct);  
                return variant;
            }).collect(Collectors.toList());

            variant.saveAll(productVariants);
        }

        return "Product added successfully!";
    }
	@Override
	public ApiResponse deleteProductById(Long imgId) {
		ProductVariant currentVariant = variant.findById(imgId).orElseThrow();
		currentVariant.setProduct(null);
		variant.delete(currentVariant);
		return new ApiResponse("Product and its All Variants Are Deleted Successfully");
	}
	@Override
	public List<ProductVariantDTO> getAllProductsOfUser(Long uid) {
		List<ProductVariantDTO> result = new ArrayList<>();
		List<ProductVariant> currentResult = variant.findAllProductVariant(uid);
		for(ProductVariant  p : currentResult ) {
			ProductVariantDTO newDto = mapper.map(p,ProductVariantDTO.class);
			result.add(newDto);
		}
        return result;
	}
	
	@Override
	public ApiResponse updateProductQuantity(Long imgId,int stock) {
		// TODO Auto-generated method stub
		ProductVariant currentProduct = variant.findById(imgId).orElseThrow();
		currentProduct.setStock(currentProduct.getStock()+stock);
		variant.save(currentProduct);
		return new ApiResponse("Stock Updated Successfully");
	}
}