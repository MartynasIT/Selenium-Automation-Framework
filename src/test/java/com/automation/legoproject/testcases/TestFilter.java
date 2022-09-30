package com.automation.legoproject.testcases;

import com.automation.framework.loging.Log4jLogger;
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
        results.ExpandFilters();
        results.selectAge(age, "Filtering by " + age);
        results.selectPrice(price, "Filtering by " + price);
        selenium.waitTwoSeconds();
        LegoOrderInfo order = new LegoOrderInfo();
        Integer resultCount = Integer.parseInt(results.getResultCount());
        results.loadProducts(resultCount);
        Assert.assertEquals(resultCount, results.getCountOfActualLoadedItems(),
                "Page should have loaded exact amount of products");
        Log4jLogger.log("Correct amount of products was loaded");
        Assert.assertTrue(order.isFilterSatisfied(getJsonReader().getValue("Search.Max_Price"),
                results.getAllProductData()), "Filter did not work correctly");
        Log4jLogger.log("All prices are below filter and products are " + item);
        String[][] addToBagProducts = results.addToBasket(3);
        cart = navigator.navigateToCart();
        Assert.assertTrue(ArrayWorker.are2DArraysSame(cart.getAllProductData(), addToBagProducts), "Items " +
                "should have been the same in the cart compared to what was added to cart");
        Log4jLogger.log("Correct items were added to cart");
        Assert.assertTrue(order.areAllProductsAddedOnce(cart.getAllQuantitiesInCart()), "Items" +
                "should have been only once");
        Log4jLogger.log("All items were added once");
        Assert.assertEquals(cart.getTotal(), order.calculateOrderTotalOfItemsAdded(addToBagProducts,
                cart.getShippingPrice()), "Total of order is not correct");
        Log4jLogger.log("Total of order is correct");
    }


    public class LegoOrderInfo {
        public boolean isFilterSatisfied(String price, String[][] products) {
            for (String[] product : products) {
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
