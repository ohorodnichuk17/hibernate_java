package org.example.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="tbl_product_photos")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 200)
    private String image;
}
