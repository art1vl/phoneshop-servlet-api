package com.es.phoneshop.service.product;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public class StockServiceImpl implements StockService {
    private static StockServiceImpl instance;

    private ProductService productService;

    private StockServiceImpl() {
        productService = DefaultProductService.getInstance();
    }

    synchronized public static StockServiceImpl getInstance() {
        if (instance == null) {
            instance = new StockServiceImpl();
        }
        return instance;
    }

    @Override
    synchronized public void changeProductsStock(List<CartItem> cartItems) throws OutOfStockException{
        for (CartItem item: cartItems) {
            Long productId = item.getProduct().getId();
            Product productFromDao = productService.getProduct(productId);
            int newStock = productFromDao.getStock() - item.getQuantity();
            if (newStock >= 0) {
                productService.changeProductStock(productId, newStock);
            }
            else {
                throw new OutOfStockException(productFromDao.getStock(), "Sorry, but " + productFromDao.getDescription()
                        + " doesn't have enough stock. Please, return to cartPage and select another amount" +
                        " of this product. Now max stock is ");
            }
        }
    }
}
