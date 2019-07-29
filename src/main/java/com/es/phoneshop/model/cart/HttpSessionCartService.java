package com.es.phoneshop.model.cart;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class HttpSessionCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = "cart";

    private static HttpSessionCartService instance;

    private HttpSessionCartService() {}

    synchronized public static HttpSessionCartService getInstance() {
        if (instance == null){
            instance = new HttpSessionCartService();
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        Cart result = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if (result == null){
            result = new Cart();
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public void add(Cart cart, Product product, Long quantity) throws OutOfStockException{
        if (quantity > product.getStock()){
            throw new OutOfStockException(product.getStock());
        }
        cart.getCartItems().add(new CartItem(product.getId(), quantity));
        recalculateCart(cart, product, quantity);
    }

    private void recalculateCart(Cart cart, Product product, Long quantity){
        cart.setTotalCost(cart.getTotalCost().add(product.getPrice().multiply(new BigDecimal(quantity))));
        cart.setTotalQuantity(cart.getTotalQuantity() + quantity);
        cart.setProductQuantity(cart.getProductQuantity() + 1);
    }
}
