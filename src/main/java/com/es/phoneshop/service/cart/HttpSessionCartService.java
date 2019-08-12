package com.es.phoneshop.service.cart;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.service.product.DefaultProductService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.product.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = "cart";

    private static HttpSessionCartService instance;

    private ProductService productService;

    private HttpSessionCartService() {
        productService = DefaultProductService.getInstance();
    }

    synchronized public static HttpSessionCartService getInstance() {
        if (instance == null){
            instance = new HttpSessionCartService();
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        return (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
    }

    @Override
    public void add(HttpServletRequest request, Long id, int quantity) throws OutOfStockException, ProductNotFoundException {
        HttpSession session = request.getSession();
        if (id == null) {
            throw new IllegalArgumentException("productId is required");
        }
        Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
        synchronized (cart) {
            Product product = productService.getProduct(id);
            if (quantity > product.getStock() || quantity < 1) {
                throw new OutOfStockException(product.getStock(), "Not enough stock or incorrect stock input. Max product stock is ");
            }
            Optional<CartItem> itemOptional = cart.getCartItems().stream().filter(p -> p.getProduct().equals(product)).findAny();
            if (itemOptional.isPresent()) {
                CartItem itemFromTheList = itemOptional.get();
                int newQuantity = itemFromTheList.getQuantity() + quantity;
                if (newQuantity > product.getStock()) {
                    throw new OutOfStockException(product.getStock(), "After adding this quantity the total amount of this " +
                            "product in the cart will be " + newQuantity + ", but max stock is ");
                }
                cart.getCartItems().removeIf(p -> p.getProduct().equals(product));
                cart.getCartItems().add(new CartItem(product, newQuantity));
            }
            else {
                cart.getCartItems().add(new CartItem(product, quantity));
            }
            cart.setTotalQuantity(cart.getTotalQuantity() + quantity);
            cart.setSubCost(cart.getSubCost().add(product.getPrice().multiply(new BigDecimal(quantity))));
        }
    }

    @Override
    public void update(HttpServletRequest request, Long id, int quantity) throws OutOfStockException, ProductNotFoundException {
        HttpSession session = request.getSession();
        if (id == null) {
            throw new IllegalArgumentException("productId is required");
        }
        Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
        synchronized (cart) {
            Product product = productService.getProduct(id);
            if (quantity > product.getStock() || quantity < 1) {
                throw new OutOfStockException(product.getStock(), "Not enough stock or incorrect stock input. Max product stock is ");
            }
            Optional<CartItem> itemFromTheListOptional = cart.getCartItems().stream().filter(p -> p.getProduct().equals(product)).findAny();
            if (itemFromTheListOptional.isPresent()) {
                CartItem itemFromTheList = itemFromTheListOptional.get();
                cart.getCartItems().removeIf(p -> p.getProduct().equals(product));
                cart.getCartItems().add(new CartItem(product, quantity));
                Long newQuantity = cart.getTotalQuantity() - itemFromTheList.getQuantity() + quantity;
                BigDecimal newTotalCost = cart.getSubCost().subtract(itemFromTheList.getTotalCost());
                newTotalCost = newTotalCost.add(product.getPrice().multiply(new BigDecimal(quantity)));
                cart.setTotalQuantity(newQuantity);
                cart.setSubCost(newTotalCost);
            }
        }
    }

    @Override
    public void deleteCartItem(HttpServletRequest request, Long id) throws ProductNotFoundException {
        HttpSession session = request.getSession();
        if (id == null) {
            throw new IllegalArgumentException("productId is required");
        }
        Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
        synchronized (cart) {
            Product product = productService.getProduct(id);
            Optional<CartItem> itemFromTheListOptional = cart.getCartItems().stream().filter(p -> p.getProduct().equals(product)).findAny();
            if (itemFromTheListOptional.isPresent()) {
                CartItem itemFromTheList = itemFromTheListOptional.get();
                cart.getCartItems().removeIf(p -> p.getProduct().equals(product));
                Long newQuantity = cart.getTotalQuantity() - itemFromTheList.getQuantity();
                BigDecimal newTotalCost = cart.getSubCost().subtract(itemFromTheList.getTotalCost());
                cart.setTotalQuantity(newQuantity);
                cart.setSubCost(newTotalCost);
            }
        }
    }

    @Override
    public void cleanCart(Cart cart) {
        cart.getCartItems().clear();
        cart.setTotalQuantity(0L);
        cart.setSubCost(new BigDecimal(0));
    }
}
