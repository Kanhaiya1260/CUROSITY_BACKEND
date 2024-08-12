package com.app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.Dao.FilterDao;
import com.app.Dao.ProductDao;
import com.app.Dao.ProductVariantDao;
import com.app.Entities.Product;
import com.app.Entities.ProductVariant;
import com.app.dto.ProdFilterReqDTO;
import com.app.dto.ProductDTO;
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

    @Override
    public String addProduct(ProductDTO prods) {
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