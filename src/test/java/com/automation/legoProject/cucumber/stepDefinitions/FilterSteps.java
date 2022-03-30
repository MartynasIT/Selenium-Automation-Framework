package com.automation.legoProject.cucumber.stepDefinitions;

import com.automation.legoProject.base.BaseTest;
import com.automation.legoProject.pageObjects.MainPage;
import com.automation.legoProject.pageObjects.ProductSearchResultPage;
import com.automation.legoProject.testCases.TestFilter;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class FilterSteps extends BaseTest {
    BaseTest.LegoNavigator navigator;
    ProductSearchResultPage results;

    @Given("I am at {string} main page")
    public void i_am_at_main_page(String page) {
        super.setup(null);
        navigator = new LegoNavigator(selenium);
        new MainPage(selenium, true);
    }

    @When("I navigate to Lego Merchandise page and click on Keychains")
    public void i_navigate_to_lego_merchandise_page_and_click_on_keychains() {
        logger.info("Navigating to keychains");
        results = navigator.navigateToProductsKeychains();
    }

    @When("I select {string} in the Product type filter")
    public void i_select_in_the_product_type_filter(String type) {
        results.selectProductType(type, "Filtering by " + type);
    }

    @When("I select {string} in the Age filter")
    public void i_select_in_the_age_filter(String age) {
        results.selectAge(age, "Filtering by " + age);
    }

    @When("I select {string} in the price filter")
    public void i_select_in_the_price_filter(String price) {
        results.selectPrice(price, "Filtering by " + price);
    }

    @Then("I see keychains that were correctly filtered: Item is {string} max price {string}")
    public void iSeeKeychainsThatWereCorrectlyFilteredItemIsAgeMaxPrice(String item, String maxPrice) {
        selenium.waitTwoSeconds();
        TestFilter filterClass = new TestFilter();
        TestFilter.LegoOrderInfo orderInfo = filterClass.new LegoOrderInfo();
        Integer resultCount = Integer.parseInt(results.getResultCount());
        results.loadProducts(resultCount);
        Assert.assertEquals(resultCount, results.getCountOfActualLoadedItems(),
                "Page should have loaded exact amount of products");
        logger.info("Correct amount of products was loaded");
        Assert.assertTrue(orderInfo.isFilterSatisfied(item, maxPrice,
                results.getAllProductData()), "Filter did not work correctly");
        logger.info("All prices are below filter and products are " + item);
        quitDriver();
    }
}
