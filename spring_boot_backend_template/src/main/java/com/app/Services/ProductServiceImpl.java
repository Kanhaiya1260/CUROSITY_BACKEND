package com.app.Services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Dao.FilterDao;
import com.app.Dao.ProductDao;
import com.app.Dao.ProductVariantDao;
import com.app.Entities.Product;
import com.app.Entities.ProductVariant;
import com.app.dto.ProdFilterReqDTO;
import com.app.dto.ProductReqDTO;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
    private ModelMapper mapper;

    @Override
    public List<ProductVariant> getProductsByFilter(ProdFilterReqDTO product) {
        List<Long> imgids = filter.findByColorAndSize(product.getColor(), product.getSize());
        System.out.println(product);
        List<Product> allProducts = (product.getCat() == null) ? prod.findAll() : prod.findByCategoryName(product.getCat());

        List<Product> filteredProducts = allProducts.stream()
            .filter(p -> product.getPrice() == null ||
                         (p.getPrice() >= product.getPrice()[0] && p.getPrice() <= product.getPrice()[1]))
            .collect(Collectors.toList());

        List<ProductVariant> result = new ArrayList<>();

        for (Product p : filteredProducts) {
            List<ProductVariant> variants = variant.findBypid(p.getPid());

            List<ProductVariant> filteredVariants = (product.getColor()==null && product.getSize()==null) ? variants : 
                variants.stream()
                    .filter(v -> imgids.contains(v.getImgid()))
                    .collect(Collectors.toList());
            
            for(ProductVariant fv : filteredVariants) {
            	result.add(fv);
            }
        }

        return result;
    }

    private List<Long> getImgids(ProdFilterReqDTO product) {
        if (product.getColor() != null && product.getSize() != null) {
            System.out.println("Filtering by color and size: " + product.getColor() + ", " + product.getSize());
            return filter.findByColorAndSize(product.getColor(), product.getSize());
        } else if (product.getColor() != null) {
            System.out.println("Filtering by color: " + product.getColor());
            return filter.findByColor(product.getColor());
        } else if (product.getSize() != null) {
            System.out.println("Filtering by size: " + product.getSize());
            return filter.findBySize(product.getSize());
        }
        return new ArrayList<>();
    }
    
    @Override
    public String addProduct(ProductReqDTO prods) {
        Product newProduct = prod.save(prods.getProduct());
        
        if (prods.getVariants() != null && !prods.getVariants().isEmpty()) {
            List<ProductVariant> productVariants = prods.getVariants().stream().map(variantDTO -> {
                ProductVariant variant = new ProductVariant();
                variant.setColor(variantDTO.getColor());
                variant.setStock(variantDTO.getStock());
                variant.setImgid(variantDTO.getImgid());
                variant.setProduct(newProduct);  
                return variant;
            }).collect(Collectors.toList());

            variant.saveAll(productVariants);
        }
        
        return "Product added successfully!";
    }
}
