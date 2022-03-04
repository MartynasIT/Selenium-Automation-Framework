package com.automation.legoProject.pageObjects;

import com.automation.legoProject.base.BasePage;
import com.framework.utils.CoreSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class ProductSearchResultPage extends BasePage {

    private String PAGE_NAME = "Product Result Page";
    private String FILTER_MENU = "//div[@div='facet-navigation']";
    private final By SORT_BUTTON = By.id("sortBy");
    private final By PRODUCT_TYPE = By.xpath(FILTER_MENU + "//div[@data-test='facet-navigation__list-type']");
    private final By LISTING_SUMMARY_TEXT = By.xpath("//div[@data-test='listing-summary']");
    private final By SHOW_ALL_BUTTON = By.linkText("Show All");
    private final By PRODUCT_ITEM = By.xpath("//li[@data-test='product-item']");

    public ProductSearchResultPage(CoreSelenium selenium) {
        super(selenium);
        if (!selenium.isElementFound(SORT_BUTTON))
            throw new RuntimeException("Failed to load " + PAGE_NAME);
        else
            logger.info(PAGE_NAME + " was loaded successfully");
    }

    public void selectProductType(String type, String log) {
        selenium.jsClick(getCheckBoxElement(type), log);
    }

    public void selectAge(String age, String log) {
        selenium.jsClick(getCheckBoxElement(age), log);
    }

    public void selectPrice(String price, String log) {
        selenium.jsClick(getCheckBoxElement(price), log);
    }

    public String getResultCount() {
        String text = selenium.getText(LISTING_SUMMARY_TEXT, 2, 1);
        text = text.substring((text.indexOf("of") + 1), text.indexOf("results")).replace("f", "").trim();
        return text;
    }

    public void loadProducts(Integer toLoad) {
        selenium.click(SHOW_ALL_BUTTON, "Clicking show all");
        selenium.scrollToBottom();
        while (!selenium.getText(By.xpath("//div[@data-test='listing-show-of']"), 5, 1).
                equals("Showing " + toLoad + " of " + toLoad)) {
            selenium.scrollToBottom();
            selenium.waitTwoSeconds();
        }
    }

    private WebElement getCheckBoxElement(String filter) {
        return selenium.findElement(By.xpath("//label[@data-test='checkbox-label']/child::" +
                "div/following-sibling::" + "span[contains(text()," + "'" + filter + "'" + ")]" +
                "/preceding-sibling::div/child::input"));
    }

    public Integer getCountOfActualLoadedItems() {
        return selenium.findElements(PRODUCT_ITEM).size();
    }

    public String[][] getAllProductData() {
        String[][] products = new String[getCountOfActualLoadedItems()][2];
        int i = 0;
        for (WebElement product : selenium.findElements(PRODUCT_ITEM)) {
            products[i][0] = product.findElement(By.xpath(".//h2[@data-test='product-leaf-title']")).getText();
            try {
                products[i][1] = product.findElement(By.xpath(".//span[@data-test='product-price-sale']")).getText();
            } catch (NoSuchElementException e) {
                products[i][1] = product.findElement(By.xpath(".//span[@data-test='product-price']")).getText();
            }
            i++;
        }
        return products;
    }

    public String[][] addToBasket(int howManyToAdd) {
        logger.info("Adding products to basket");
        String[][] products = new String[howManyToAdd][2];
        int i = 0;
        for (WebElement product : selenium.findElements(PRODUCT_ITEM)) {
            if (i == howManyToAdd)
                return products;
            WebElement addToBag = product.findElement(By.xpath(".//button[@data-test='product-leaf-cta-add-to-cart']"));
            selenium.jsClick(addToBag);
            products[i][0] = selenium.findElement(By.xpath("//div[@data-test='add-to-bag-modal']//p[1]"),
                    2, 1).getText();
            products[i][1] = selenium.findElement(By.xpath("//div[@data-test='add-to-bag-modal']//p[2]"),
                    2, 1).getText();
            selenium.click(By.xpath("//button[@data-test='continue-shopping-button']"));
            i++;
        }
        logger.info("Products added to basket");
        return products;
    }

    private Integer getAddableCount() {
        return selenium.findElements(By.xpath("//div[contains(text(),'Add to Bag')]")).size();

    }
}
