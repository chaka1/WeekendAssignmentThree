package com.example.chaka.weekendassignmentthree.interfaces;

import com.example.chaka.weekendassignmentthree.models.Product;

import java.util.List;

/**
 * Created by Chaka on 09/06/2015.
 */
public interface iBasketCallback {

    void addProduct(Product product);

    List<Product> getBasket();


}
