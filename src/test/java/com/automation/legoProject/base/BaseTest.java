package com.automation.legoProject.base;

import com.automation.legoProject.pageObjects.CartPage;
import com.automation.legoProject.pageObjects.ProductSearchResultPage;
import com.framework.driver.DriverFactory;
import com.framework.driver.DriverManager;
import com.framework.utils.CoreSelenium;
import com.framework.utils.JsonReader;
import com.automation.legoProject.pageComponents.HeaderMenu;
import com.automation.legoProject.pageObjects.MainPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected CoreSelenium selenium;
    protected DriverManager driverManager;
    protected JsonReader jsonReader;
    private static final String ENVIRONMENTS = "src/test/resources/env.json";
    protected Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeTest
    @Parameters("testDataPath")
    public void setup(@Optional String testDataPath) {
        if (testDataPath != null)
            jsonReader = new JsonReader(testDataPath);
        driverManager = DriverFactory.getDriverManager();
        selenium = new CoreSelenium(driverManager.getWebDriver());
        logger.info("Test Started");
        selenium.get(new JsonReader(ENVIRONMENTS).getValue("Lego.URL"), "Launching website");
    }

    @AfterTest
    public void quitDriver() {
        selenium.quitDriver();
    }

    public class LegoNavigator {
        MainPage mainPage;
        HeaderMenu header;

        private final CoreSelenium selenium;

        public LegoNavigator(CoreSelenium selenium) {
            this.selenium = selenium;
            header = new HeaderMenu(selenium);
        }

        public ProductSearchResultPage navigateToProductsKeychains() {
            logger.info("Navigating to keychains");
            header.clickShop();
            header.clickMerchandise();
            header.clickKeychains();
            return new ProductSearchResultPage(selenium);
        }

        public CartPage navigateToCart() {
            logger.info("Navigating to Cart");
            header.clickBag();
            return new CartPage(selenium);
        }
    }

}
