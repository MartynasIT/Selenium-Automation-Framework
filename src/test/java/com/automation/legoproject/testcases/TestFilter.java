package com.automation.legoproject.testcases;

import com.automation.legoproject.base.BaseTest;
import com.automation.legoproject.base.PageNavigator;
import com.automation.legoproject.pageobjects.CartPage;
import com.automation.legoproject.pageobjects.MainPage;
import com.automation.legoproject.pageobjects.ProductSearchResultPage;
import com.automation.framework.utils.ArrayWorker;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;


public class TestFilter extends BaseTest {
    PageNavigator navigator;
    ProductSearchResultPage results;
    MainPage mainPage;
    CartPage cart;

    @Test
    public void testFilterAndBasket() {
        mainPage = new MainPage(selenium, true);
        navigator = new PageNavigator(selenium);
        results = navigator.navigateToProductsKeychains();
        String item = getJsonReader().getValue("Search.Item");
        String age = getJsonReader().getValue("Search.Age");
        String price = getJsonReader().getValue("Search.Money");
        results.selectProductType(item, "Filtering by " + item);
        results.selectAge(age, "Filtering by " + age);
        results.selectPrice(price, "Filtering by " + price);
        selenium.waitTwoSeconds();
        LegoOrderInfo order = new LegoOrderInfo();
        Integer resultCount = Integer.parseInt(results.getResultCount());
        results.loadProducts(resultCount);
        Assert.assertEquals(resultCount, results.getCountOfActualLoadedItems(),
                "Page should have loaded exact amount of products");
        logger.info("Correct amount of products was loaded");
        Assert.assertTrue(order.isFilterSatisfied(item, getJsonReader().getValue("Search.Max_Price"),
                results.getAllProductData()), "Filter did not work correctly");
        logger.info("All prices are below filter and products are " + item);
        String[][] addToBagProducts = results.addToBasket(3);
        cart = navigator.navigateToCart();
        Assert.assertTrue(new ArrayWorker().are2DArraysSame(cart.getAllProductData(), addToBagProducts), "Items " +
                "should have been the same in the cart compared to what was added to cart");
        logger.info("Correct items were added to cart");
        Assert.assertTrue(order.areAllProductsAddedOnce(cart.getAllQuantitiesInCart()), "Items" +
                "should have been only once");
        logger.info("All items were added once");
        Assert.assertEquals(cart.getTotal(), order.calculateOrderTotalOfItemsAdded(addToBagProducts,
                cart.getShippingPrice()), "Total of order is not correct");
        logger.info("Total of order is correct");
    }


    public class LegoOrderInfo {
        public boolean isFilterSatisfied(String name, String price, String[][] products) {
            for (String[] product : products) {
                String productName = product[0].trim();
                String productPrice = product[1].
                        replace("Price", "").
                        replace("Sale Price", "").
                        replace("Sale", "").
                        replace("$", "").
                        trim();
                if (!(Double.parseDouble(productPrice) <= Double.parseDouble(price)))
                    return false;
            }
            return true;
        }

        private boolean areAllProductsAddedOnce(Integer[] data) {
            for (Integer datum : data) {
                if (datum > 1)
                    return false;
            }
            return true;
        }

        private double calculateOrderTotalOfItemsAdded(String[][] products, double shipping) {
            double total = 0;
            for (String[] product : products) {
                DecimalFormat df = new DecimalFormat("#.#####");
                String productPrice = product[1].
                        replace("Price", "").
                        replace("Sale Price", "").
                        replace("Sale", "").
                        replace("$", "").
                        trim();
                total += Double.parseDouble(productPrice);
            }
            total = BigDecimal.valueOf(total + shipping)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
            return total;
        }
    }
}