package com.es.phoneshop.model.cart;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.HttpSessionProductService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = "cart";

    private static HttpSessionCartService instance;

    private ProductService productService;

    private HttpSessionCartService() {
        productService = HttpSessionProductService.getInstance();
    }

    synchronized public static HttpSessionCartService getInstance() {
        if (instance == null){
            instance = new HttpSessionCartService();
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        Cart result = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        return result.clone();
    }

    @Override
    public void add(HttpServletRequest request, Long id, Long quantity) throws OutOfStockException, ProductNotFoundException {
        HttpSession session = request.getSession();
        if (id == null) {
            throw new IllegalArgumentException("productId is required");
        }
        synchronized (session) {
            Product product = productService.getProduct(id);
            if (quantity > product.getStock() || quantity < 1) {
                throw new OutOfStockException(product.getStock(), "Not enough stock or incorrect stock input. Max product stock is ");
            }
            Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
            Optional<CartItem> itemOptional = cart.getCartItems().stream().filter(p -> p.getProduct().equals(product)).findAny();
            if (itemOptional.isPresent()) {
                CartItem itemFromTheList = itemOptional.get();
                Long newQuantity = itemFromTheList.getQuantity() + quantity;
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
            cart.setTotalCost(cart.getTotalCost().add(product.getPrice().multiply(new BigDecimal(quantity))));
        }
    }

    @Override
    public void update(HttpServletRequest request, Long id, Long quantity) throws OutOfStockException, ProductNotFoundException {
        HttpSession session = request.getSession();
        if (id == null) {
            throw new IllegalArgumentException("productId is required");
        }
        synchronized (session) {
            Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
            Product product = productService.getProduct(id);
            if (quantity > product.getStock() || quantity < 1) {
                throw new OutOfStockException(product.getStock(), "Not enough stock or incorrect stock input. Max product stock is ");
            }
            CartItem itemFromTheList = cart.getCartItems().stream().filter(p -> p.getProduct().equals(product)).findAny().get();
            cart.getCartItems().removeIf(p -> p.getProduct().equals(product));
            cart.getCartItems().add(new CartItem(product, quantity));
            Long newQuantity = cart.getTotalQuantity() - itemFromTheList.getQuantity() + quantity;
            BigDecimal newTotalCost = cart.getTotalCost().subtract(itemFromTheList.getTotalCost());
            newTotalCost = newTotalCost.add(product.getPrice().multiply(new BigDecimal(quantity)));
            cart.setTotalQuantity(newQuantity);
            cart.setTotalCost(newTotalCost);
        }
    }

    @Override
    public void deleteCartItem(HttpServletRequest request, Long id) throws ProductNotFoundException {
        HttpSession session = request.getSession();
        if (id == null) {
            throw new IllegalArgumentException("productId is required");
        }
        synchronized (session) {
            Product product = productService.getProduct(id);
            Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
            CartItem itemFromTheList = cart.getCartItems().stream().filter(p -> p.getProduct().equals(product)).findAny().get();
            cart.getCartItems().removeIf(p -> p.getProduct().equals(product));
            Long newQuantity = cart.getTotalQuantity() - itemFromTheList.getQuantity();
            BigDecimal newTotalCost = cart.getTotalCost().subtract(itemFromTheList.getTotalCost());
            cart.setTotalQuantity(newQuantity);
            cart.setTotalCost(newTotalCost);
        }
    }
}
