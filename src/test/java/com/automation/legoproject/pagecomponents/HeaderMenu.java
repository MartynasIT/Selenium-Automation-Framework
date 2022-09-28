package com.automation.legoproject.pagecomponents;

import com.automation.framework.utils.CoreSelenium;
import com.automation.legoproject.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HeaderMenu extends BasePage {

    private final String HEADER_MENU_MAIN_NAVIGATION = "//nav[@data-test='main-navigation']";
    private final String NAVIGATION_SUB_MENU = "//ul[@data-test='navigation-submenu']";
    private final By SHOP_BUTTON = By.xpath(HEADER_MENU_MAIN_NAVIGATION + "//button[@data-analytics-title='shop']");
    private final By LEGO_MERCHANDISE_BUTTON = By.xpath(NAVIGATION_SUB_MENU + "//button[@data-analytics-title='lego-merchandise']");
    private final By KEYCHAINS_LINK = By.xpath("//a[@data-analytics-title='keyrings']");
    private final By MY_BAG = By.xpath("//a[@aria-label='My Bag']");
    private final By SEARCH_ICON = By.xpath("//button[@data-test='search-input-button']");
    private final By SEARCH_INPUT = By.xpath("//input[@data-test='search-input']");

    public HeaderMenu(CoreSelenium selenium) {
        super(selenium);
    }

    public void clickShop() {
        selenium.click(SHOP_BUTTON, "Clicking shop button");
    }

    public void clickMerchandise() {
        selenium.click(LEGO_MERCHANDISE_BUTTON, "Clicking LEGO Merchandise button");
    }

    public void clickKeychains() {
        selenium.click(KEYCHAINS_LINK, "Clicking keychains link");
    }

    public void clickBag() {
        selenium.jsClick(selenium.findElement(MY_BAG), "Clicking my bag");
    }

    public void enterSearch(String text) {
        selenium.jsClick(selenium.findElement(SEARCH_ICON), "Clicking on search icon");
        selenium.sendKeys(SEARCH_INPUT, text, "Searching for " + text);
    }

    public List<WebElement> getSuggestions() {
        return selenium.findElements(By.xpath("//p[@data-test='product-suggestion-name']"));

    }
}
