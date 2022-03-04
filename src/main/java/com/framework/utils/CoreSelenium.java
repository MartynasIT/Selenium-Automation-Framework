package com.framework.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;


public class CoreSelenium {

    private final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    @Setter
    @Getter
    private static Integer maxWaitTime = 20;
    @Setter
    @Getter
    private static Integer pollTime = 2;
    private Logger logger = LogManager.getLogger(CoreSelenium.class);

    public CoreSelenium(WebDriver driver) {
        setDriver(driver);
    }

    public synchronized WebDriver getDriver() {
        return this.driver.get();
    }

    private synchronized void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

    private void log(String logMessage) {
        if (logMessage != null)
            logger.info(logMessage);
    }


    /**
     * To launch the given URL
     *
     * @param url
     * @param logMessage
     */
    public void get(String url, String logMessage) {
        getDriver().get(url);
        log(logMessage + " " + url);
    }

    /**
     * To click the locator on the page
     *
     * @param locator - The locator By object
     */
    public void click(By locator) {
        click(locator, null, null, null);
    }

    /**
     * To click the locator on the page
     *
     * @param locator
     * @param logMessage
     */
    public void click(By locator, String logMessage) {
        click(locator, logMessage, null, null);
    }

    /**
     * To click the locator on the page
     *
     * @param locator
     * @param logMessage
     * @param maxWaitTime
     */
    public void click(By locator, String logMessage, Integer maxWaitTime) {
        click(locator, logMessage, maxWaitTime, null);
    }

    /**
     * To click the locator on the page
     *
     * @param locator
     * @param logMessage
     * @param maxWaitTime
     * @param pollTime
     */
    public void click(By locator, String logMessage, Integer maxWaitTime, Integer pollTime) {
        this.waitUntilElementToBeClickable(locator, maxWaitTime, pollTime).click();
        log(logMessage);
    }

    /**
     * To click the locator on the page
     *
     * @param locator
     * @param logMessage
     */
    public void clickRadioButton(By locator, String logMessage) {
        clickRadioButton(locator, logMessage, null, null);
    }

    /**
     * To click a radion button by the locator on the page
     *
     * @param locator
     * @param logMessage
     * @param maxWaitTime
     */
    public void clickRadioButton(By locator, String logMessage, Integer maxWaitTime) {
        clickRadioButton(locator, logMessage, maxWaitTime, null);
    }

    /**
     * To click a radion button by the locator on the page
     *
     * @param locator
     * @param logMessage
     * @param maxWaitTime
     * @param pollTime
     */
    public void clickRadioButton(By locator, String logMessage, Integer maxWaitTime, Integer pollTime) {
        Actions action = new Actions(getDriver());
        WebElement radioBtn = this.findElement(locator, maxWaitTime, pollTime);
        action.click(radioBtn).build().perform();
        log(logMessage);
    }

    /**
     * To enter value in the text field
     *
     * @param locator
     * @param valueToEnter
     * @param clear
     * @param logStep
     * @param logMessage
     */
    public void sendKeys(By locator, String valueToEnter, boolean clear, String logStep, String logMessage) {
        sendKeys(locator, valueToEnter, clear, logMessage, null, null);
    }

    public void sendKeys(By locator, String valueToEnter, String logMessage) {
        sendKeys(locator, valueToEnter, true, logMessage, null, null);
    }

    public void sendKeys(By locator, String valueToEnter, String logMessage,
                         Integer maxWaitTime, Integer pollTime) {
        sendKeys(locator, valueToEnter, true, logMessage, maxWaitTime, pollTime);
    }

    /**
     * To enter value in the text field
     *
     * @param locator
     * @param valueToEnter
     * @param clear
     * @param logMessage
     * @param maxWaitTime
     * @param pollTime
     */
    public void sendKeys(By locator, String valueToEnter, boolean clear,
                         String logMessage, Integer maxWaitTime, Integer pollTime) {
        WebElement ele = this
                .getWait(maxWaitTime, pollTime)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        if (clear) {
            ele.click();
            ele.clear();
        }
        ele.sendKeys(valueToEnter);
        log(logMessage);
    }

    /**
     * To check If Element is found on the page or not.
     *
     * @param locator     - The locator to search for
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - The poll time
     * @return - true-if element is found else false.
     */
    public boolean isElementFound(final By locator, Integer maxWaitTime, Integer pollTime) {
        try {
            Wait<WebDriver> wait = getWait(maxWaitTime, pollTime);
            WebElement ele = wait.until((WebDriver driver1) -> {
                try {
                    return driver1.findElement(locator);
                } catch (Exception e) {
                    return null;
                }
            });
            return ele != null;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * To check if Element is visible or not
     *
     * @param locator
     * @return
     */
    public boolean isElementVisible(By locator) {
        return isElementVisible(locator, null, null);
    }

    public boolean isElementFound(By locator) {
        return isElementFound(locator, maxWaitTime, pollTime);
    }

    /**
     * To check if Element is visible or not
     *
     * @param locator
     * @param maxWaitTime
     * @param pollTime
     * @return
     */
    public boolean isElementVisible(By locator, Integer maxWaitTime, Integer pollTime) {
        try {
            this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * To check if the Element is clickable or not
     *
     * @param locator
     * @param maxWaitTime
     * @param pollTime
     * @return
     */
    public boolean isElementClickable(By locator, Integer maxWaitTime, Integer pollTime) {
        try {
            this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * To wait until element to be clickable on the page
     *
     * @param locator - The Locator
     * @return - true - if clickable, false - if not clickable
     */
    public WebElement waitUntilElementToBeClickable(By locator) {
        return waitUntilElementToBeClickable(locator, null, null);
    }

    /**
     * To wait until element to be clickable on the page
     *
     * @param locator
     * @param maxWaitTime
     * @param pollTime
     * @return
     */
    public WebElement waitUntilElementToBeClickable(By locator, Integer maxWaitTime, Integer pollTime) {
        this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.elementToBeClickable(locator));
        return this.findElement(locator, maxWaitTime, pollTime);
    }

    /**
     * To find the element on the page
     *
     * @param locator
     * @return
     */
    public WebElement findElement(By locator) {
        return getDriver().findElement(locator);
    }

    /**
     * To find the elements on the page
     *
     * @param locator
     * @return
     */
    public List<WebElement> findElements(By locator) {
        return getDriver().findElements(locator);
    }

    /**
     * To find the element on the page
     *
     * @param locator
     * @param maxWaitTime
     * @param pollTime
     * @return
     */
    public WebElement findElement(By locator, Integer maxWaitTime, Integer pollTime) {
        if (isElementFound(locator, maxWaitTime, pollTime)) {
            return getDriver().findElement(locator);
        } else {
            return null;
        }
    }

    /**
     * To get the 'Wait' object with the given Maximum Wait Time and Poll Time.
     *
     * @param maxWaitTime - The Maximum Wait Time
     * @param pollTime    - The Poll Time
     * @return - The Wait object
     */
    private Wait<WebDriver> getWait(Integer maxWaitTime, Integer pollTime) {
        if (maxWaitTime == null) {
            maxWaitTime = CoreSelenium.maxWaitTime;
        }
        if (pollTime == null) {
            pollTime = CoreSelenium.pollTime;
        }
        return new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(maxWaitTime))
                .pollingEvery(Duration.ofSeconds(pollTime))
                .ignoring(NoSuchElementException.class);
    }

    public void jsClick(WebElement element, String logMessage) {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) getDriver();
        javaScriptExecutor.executeScript("arguments[0].click();", element);
        log(logMessage);
    }

    public void jsClick(WebElement element) {
        jsClick(element, null);
    }

    public WebElement waitForElementToBeClickable(By locator, Integer maxWaitTime, Integer pollTime) {
        try {
            this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.elementToBeClickable(locator));
            return this.findElement(locator);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Wait for element to be found on the page
     *
     * @param locator
     * @param maxWaitTime
     * @param pollTime
     * @return
     */
    public WebElement waitForElementToBeFound(By locator, Integer maxWaitTime, Integer pollTime) {
        try {
            this.getWait(maxWaitTime, pollTime).until((WebDriver t) -> getDriver().findElement(locator));
            return this.findElement(locator);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Wait for element to be found on the page
     *
     * @param locator
     * @return
     */
    public WebElement waitForElementToBeFound(By locator) {
        return waitForElementToBeFound(locator, null, null);
    }

    /**
     * Wait for element to be visible on the page
     *
     * @param locator
     * @param maxWaitTime
     * @param pollTime
     * @return
     */
    public WebElement waitForElementToBeVisible(By locator, Integer maxWaitTime, Integer pollTime) {
        try {
            this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.visibilityOfElementLocated(locator));
            return this.findElement(locator);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Wait for element to be clickable on the page
     *
     * @param element
     * @param maxWaitTime
     * @param pollTime
     * @return
     */
    public WebElement waitForElementToBeClickable(WebElement element, Integer maxWaitTime, Integer pollTime) {
        try {
            this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.elementToBeClickable(element));
            return element;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Wait for element to be inVisible on the page
     *
     * @param locator
     * @param maxWaitTime
     * @param pollTime
     * @return
     */
    public WebElement waitForElementToBeInVisible(By locator, Integer maxWaitTime, Integer pollTime) {
        try {
            this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return this.findElement(locator);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Wait for element to be inVisible on the page
     *
     * @param element
     * @param maxWaitTime
     * @param pollTime
     * @return
     */
    public WebElement waitForElementToBeInVisible(WebElement element, Integer maxWaitTime, Integer pollTime) {
        try {
            this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.invisibilityOf(element));
            return element;
        } catch (Exception e) {
            return null;
        }
    }

    public WebElement waitForElementToBeInVisible(By locator) {
        return waitForElementToBeInVisible(locator, null, null);
    }

    public WebElement waitForElementToBeInVisible(WebElement element) {
        return waitForElementToBeInVisible(element, null, null);
    }

    /*
     * Wait for element to be clickable
     */
    public WebElement waitForElementToBeClickable(By locator) {
        return waitForElementToBeClickable(locator, null, null);
    }

    /**
     * Wait for element to be visible on the page
     *
     * @param locator
     * @return
     */
    public WebElement waitForElementToBeVisible(By locator) {
        return waitForElementToBeVisible(locator, null, null);
    }

    /**
     * Wait for element to be visible on the page
     *
     * @param element
     * @return
     */
    public WebElement waitForElementToBeClickable(WebElement element) {
        return waitForElementToBeClickable(element, null, null);
    }

    /**
     * To select the drop down
     *
     * @param locator
     * @param maxWaitTime
     * @param pollTime
     * @return
     */
    private Select select(By locator, Integer maxWaitTime, Integer pollTime) {
        return new Select(this.waitForElementToBeVisible(locator, maxWaitTime, pollTime));
    }

    /**
     * Select drop down by value
     *
     * @param locator
     * @param value
     * @param logMessage
     * @param maxWaitTime
     * @param pollTime
     */
    public void selectByValue(By locator, String value, String logMessage, Integer maxWaitTime, Integer pollTime) {
        this.select(locator, maxWaitTime, pollTime).selectByValue(value);
        logger.info(logMessage);
    }

    /**
     * Select drop down by visible text
     *
     * @param locator
     * @param visibleText
     * @param logMessage
     * @param maxWaitTime
     * @param pollTime
     */
    public void selectByVisibleText(By locator, String visibleText, String logMessage, Integer maxWaitTime, Integer pollTime) {
        this.select(locator, maxWaitTime, pollTime).selectByVisibleText(visibleText);
        logger.info(logMessage);
    }

    /**
     * Select drop down by given index
     *
     * @param locator
     * @param index
     * @param logMessage
     * @param maxWaitTime
     * @param pollTime
     */
    public void selectByIndex(By locator, Integer index, String logMessage, Integer maxWaitTime, Integer pollTime) {
        this.select(locator, maxWaitTime, pollTime).selectByIndex(index);
        logger.info(logMessage);
    }

    public String getText(By locator, Integer maxWaitTime, Integer pollTime) {
        return this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.presenceOfElementLocated(locator)).getText();
    }

    public String getText(By locator) {
        return getText(locator, null, null);
    }

    public String getAttribute(By locator, String attributeName) {
        return getAttribute(locator, attributeName, null, null);
    }

    public String getAttribute(By locator, String attributeName, Integer maxWaitTime, Integer pollTime) {
        return this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.presenceOfElementLocated(locator)).getAttribute(attributeName);
    }

    public WebDriver switchToFrame(By locator, Integer maxWaitTime, Integer pollTime) {
        return getDriver().switchTo().frame(this.waitForElementToBeFound(locator, maxWaitTime, pollTime));
    }

    public WebDriver switchToFrame(By locator) {
        return switchToFrame(locator, null, null);
    }

    public WebDriver switchToFrame(String name) {
        return getDriver().switchTo().frame(name);
    }

    public WebDriver switchToParentFrame() {
        return getDriver().switchTo().parentFrame();
    }

    public WebDriver switchToDefaultContent() {
        return getDriver().switchTo().defaultContent();
    }

    public synchronized void closeDriver() {
        getDriver().close();
    }

    public synchronized void quitDriver() {
        getDriver().quit();
    }

    public void scrollTop() {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) getDriver();
        javaScriptExecutor.executeScript("window.scrollTo(0,0)");
    }

    public void scrollDown() {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) getDriver();
        javaScriptExecutor.executeScript("window.scrollTo(0,250)");
    }

    public void zoomOut() {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("document.body.style.zoom = '67%'");
    }

    public void waitDownloading(String downloadDir, String fileName) {
        Path dowloadFilePath = Paths.get(downloadDir, fileName);
        new WebDriverWait(getDriver(), 100).until(d -> dowloadFilePath.toFile().exists());
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver.get()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * To return the UI attribute on an element on UI
     *
     * @param element
     * @param attributeName
     * @param logMessage
     * @return UI attribute
     */
    public String getUiAttribute(By element, String attributeName, String logMessage) {
        waitForElementToBeFound(element);
        return getUiAttribute(element, attributeName, logMessage, null);
    }

    /**
     * To return the UI attribute on an element on UI
     *
     * @param element
     * @param attributeName
     * @param logMessage
     * @param pollTime
     * @return
     */
    public String getUiAttribute(By element, String attributeName, String logMessage, Integer pollTime) {
        String attributeProperty = getDriver().findElement(element).getCssValue(attributeName);
        logger.info(logMessage);
        if (attributeName.contains("color")) {
            return Color.fromString(attributeProperty).asHex();
        } else {
            return attributeProperty;
        }
    }

    public void switchFrameById(String id) {
        getDriver().switchTo().frame(id);
    }

    public void switchFrameByIndex(int index) {
        getDriver().switchTo().frame(index);
    }


    /**
     * To check if element has some text
     *
     * @param locator
     * @param text
     * @param maxWaitTime
     * @param pollTime
     */
    public boolean doesElementContainText(By locator, String text, Integer maxWaitTime, Integer pollTime) {
        try {
            if (isElementFound(locator, maxWaitTime, pollTime)) {
                this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * To check if element has some text in whole html
     *
     * @param locator
     * @param text
     * @param maxWaitTime
     * @param pollTime
     */

    public boolean doesElementContainTextHTML(By locator, String text, Integer maxWaitTime, Integer pollTime) {
        try {
            if (isElementFound(locator, maxWaitTime, pollTime)) {
                this.getWait(maxWaitTime, pollTime).until(new elementContainsText(text, findElement(locator)));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * To check if URL has some text
     *
     * @param text
     * @param maxWaitTime
     * @param pollTime
     */
    public boolean doesUrlContainText(String text, Integer maxWaitTime, Integer pollTime) {
        try {
            return this.getWait(maxWaitTime, pollTime).until((WebDriver t) -> {
                try {
                    String ulr = getDriver().getCurrentUrl().toLowerCase().replace("-", " ");
                    return ulr != null && ulr.contains(text.toLowerCase());
                } catch (Exception e) {
                    return false;
                }
            });

        } catch (
                Exception e) {
            return false;
        }
    }

    /**
     * To check if whole page has some text
     *
     * @param text
     * @param maxWaitTime
     * @param pollTime
     */
    public boolean doesPageContainText(String text, Integer maxWaitTime, Integer pollTime) {
        try {
            this.getWait(maxWaitTime, pollTime).until(new sourceContainsText(text));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get open browser tabs
     */
    public ArrayList<String> getTabs() {
        return new ArrayList<java.lang.String>(getDriver().getWindowHandles());
    }

    /**
     * Get JavascriptExecutor object
     */
    public JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) getDriver();
    }

    private class sourceContainsText implements ExpectedCondition<Boolean> {
        private final String text;

        public sourceContainsText(String text) {
            this.text = text.toLowerCase();
        }

        @Override
        public Boolean apply(WebDriver driver) {
            try {
                return driver.findElement(By.xpath("//html")).getAttribute("outerHTML").toLowerCase().contains(text);
            } catch (Exception e) {
                return false;
            }

        }
    }

    private class elementContainsText implements ExpectedCondition<Boolean> {
        private final String text;
        private final WebElement element;

        public elementContainsText(String text, WebElement element) {
            this.text = text.toLowerCase();
            this.element = element;
        }

        @Override
        public Boolean apply(WebDriver driver) {
            try {
                return element.getAttribute("outerHTML").toLowerCase().contains(text);
            } catch (Exception e) {
                return false;
            }

        }
    }

    public void scrollIntoView(WebElement webElement) {
        ((JavascriptExecutor) this.getDriver()).executeScript("arguments[0].scrollIntoView(true);", webElement);
        waitForElementToBeClickable(webElement);
    }

    public boolean isSelected(By element, int maxWaitTime, int pollTime) {
        try {
            this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.elementToBeSelected(element));
            return findElement(element).isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitTwoSeconds() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
