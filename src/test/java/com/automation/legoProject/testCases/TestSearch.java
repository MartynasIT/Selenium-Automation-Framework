package com.automation.legoProject.testCases;

import com.automation.legoProject.base.BaseTest;
import com.automation.legoProject.pageComponents.HeaderMenu;
import com.automation.legoProject.pageObjects.CartPage;
import com.automation.legoProject.pageObjects.MainPage;
import com.automation.legoProject.pageObjects.ProductInfoPage;
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
        String item = jsonReader.getValue("Search.Item");
        header.enterSearch(item);
        selenium.waitTwoSeconds();
        List<WebElement> suggestions = header.getSuggestions();
        Assert.assertTrue(suggestions.get(0).getText().contains(item), "Suggestion should have contained " + item);
        logger.info("Suggestion does contain " + item);
        suggestions.get(0).click();
        product = new ProductInfoPage(selenium);
        Assert.assertTrue(product.getProductName().contains(item), "Product name should have contained " + item);
        logger.info("Product page does contain " + item);
        product.addItemToCart(true);
        new CartPage(selenium);
    }

}
