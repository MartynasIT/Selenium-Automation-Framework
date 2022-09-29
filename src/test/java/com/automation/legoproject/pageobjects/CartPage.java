package com.automation.legoproject.pageobjects;

import com.automation.framework.loging.Log4jLogger;
import com.automation.legoproject.base.BasePage;
import com.automation.framework.utils.CoreSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {

    private static final String PAGE_NAME = "My bag";
    private final By MY_BAG_LABEL = By.xpath("//h1[contains(text(),'My Bag')]");
    private final By CART_ITEM = By.xpath("//div[@data-test='cart-item']");
    private final By TOTAL_AMOUNT = By.xpath("//main[@id='main-content']/div[1]/div[2]/div[2]/div[3]/div[1]/div[2]/" +
            "div[1]/div[1]/div[1]/div[5]/div[1]/span[1]/span[1]");
    private final By SHIPPING_AMOUNT = By.xpath("//main[1]/div[1]/div[2]/div[2]/div[3]/div[1]/div[2]/div[1]/div[1]/" +
            "div[1]/div[3]/span[2]/span[1]");

    public CartPage(CoreSelenium selenium) {
        super(selenium);
        if (!selenium.isElementFound(MY_BAG_LABEL))
            throw new RuntimeException("Failed to load " + PAGE_NAME);
        else
            Log4jLogger.log(PAGE_NAME + " was loaded successfully");
    }

    public Integer getAmountOfItemsInCart() {
        String result = selenium.getText(MY_BAG_LABEL);
        return Integer.valueOf(result.substring((result.indexOf("(") + 1), result.indexOf(")")).trim());
    }


    public String[][] getAllProductData() {
        String[][] products = new String[getAmountOfItemsInCart()][2];
        int i = 0;
        for (WebElement product : selenium.findElements(CART_ITEM)) {
            products[i][0] = product.findElement(By.xpath(".//span[@data-test='product-title']")).getText();
            products[i][1] = product.findElement(By.xpath(".//span[@data-test='product-price']")).getText().
                    replace("Price", "").
                    trim();
            i++;
        }

        return products;
    }

    public Integer[] getAllQuantitiesInCart() {
        Integer[] amounts = new Integer[getAmountOfItemsInCart()];
        int i = 0;
        for (WebElement product : selenium.findElements(CART_ITEM)) {
            amounts[i] = Integer.valueOf(product.findElement(By.xpath(".//input[@data-test='quantity-value']"))
                    .getAttribute("value"));
            i++;
        }
        return amounts;
    }

    public double getTotal() {
        return Double.parseDouble(selenium.getText(TOTAL_AMOUNT).replace("$", ""));
    }

    public double getShippingPrice() {
        String shipping = selenium.getText(SHIPPING_AMOUNT);
        if (shipping.equals("Free"))
            return 0;
        else
            return Double.parseDouble(shipping.replace("$", ""));
    }

}
