package com.automation.legoproject.testcases;

import com.automation.framework.loging.Log4jLogger;
import com.automation.legoproject.base.BaseTest;
import com.automation.legoproject.pagecomponents.HeaderMenu;
import com.automation.legoproject.pageobjects.CartPage;
import com.automation.legoproject.pageobjects.MainPage;
import com.automation.legoproject.pageobjects.ProductInfoPage;
import com.fasterxml.jackson.databind.JsonNode;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class TestSearch extends BaseTest {
    MainPage mainPage;
    HeaderMenu header;
    ProductInfoPage product;

    @Test
    public void testSearchSuggestion() {
        mainPage = new MainPage(selenium, true);
        header = new HeaderMenu(selenium);
        for (JsonNode node : getJsonReader().getJsonMapper("Search")) {
            String item = node.get("Item").asText();
        header.enterSearch(item);
        selenium.waitTwoSeconds();
        List<WebElement> suggestions = header.getSuggestions();
        Assert.assertTrue(suggestions.get(0).getText().contains(item), "Suggestion should have contained " + item);
        Log4jLogger.log("Suggestion does contain " + item);
        suggestions.get(0).click();
        product = new ProductInfoPage(selenium);
        Assert.assertTrue(product.getProductName().contains(item), "Product name should have contained " + item);
        Log4jLogger.log("Product page does contain " + item);
        product.addItemToCart(true);
        new CartPage(selenium);
        }
    }
}
