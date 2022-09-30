package com.automation.legoproject.pageobjects;

import com.automation.framework.loging.Log4jLogger;
import com.automation.legoproject.base.BasePage;
import com.automation.framework.utils.CoreSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class ProductSearchResultPage extends BasePage {

    private static final String PAGE_NAME = "Product Result Page";
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
            Log4jLogger.log(PAGE_NAME + " was loaded successfully");
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
        text = text.substring((text.indexOf("Showing")), text.indexOf("Products")).replace("Showing", "").trim();
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
                "/preceding-sibling::div/child::input"), 1,1, "Looking for filter option " + filter);
    }

    public void ExpandFilters() {
        selenium.click(By.id("product-facet-ageRange-accordion-title"));
        selenium.click(By.id("product-facet-prices-accordion-title"));
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
        String[][] products = new String[howManyToAdd][2];
        int i = 0;
        for (WebElement product : selenium.findElements(PRODUCT_ITEM)) {
            if (i == howManyToAdd)
                return products;
            WebElement addToBag = product.findElement(By.xpath(".//button[@data-test='product-leaf-cta-add-to-cart']"));
            selenium.jsClick(addToBag, "Adding product to the basket");
            products[i][0] = selenium.findElement(By.xpath("//div[@data-test='add-to-bag-modal']//p[1]"),
                    2, 1, "Looking for add to bag").getText();
            products[i][1] = selenium.findElement(By.xpath("//div[@data-test='add-to-bag-modal']//p[2]"),
                    2, 1, "Looking for add to bag").getText();
            selenium.click(By.xpath("//button[@data-test='continue-shopping-button']"));
            i++;
        }
        return products;
    }

    private Integer getAddableCount() {
        return selenium.findElements(By.xpath("//div[contains(text(),'Add to Bag')]")).size();

    }
}
