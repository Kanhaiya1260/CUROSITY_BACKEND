package com.app.dto;

import java.util.List;

import com.app.Entities.Product;
import com.app.Entities.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantDTO {

    private Long imgid;
    private String color;
    private int stock;
    private Product product;
<<<<<<< HEAD
    private List<Size> sizes;
=======
    private Size size;
    private int rating;
>>>>>>> 5a9512f43265e7350189b2d5ecdd5c1ff8223684
}