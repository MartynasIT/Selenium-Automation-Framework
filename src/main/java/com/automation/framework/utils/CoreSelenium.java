package com.automation.framework.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import com.automation.framework.loging.Log4jLogger;
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
    @Setter @Getter
    private static Integer maxWaitTime = 20;
    @Setter @Getter
    private static Integer pollTime = 2;
    private Log4jLogger logger;

    public CoreSelenium(WebDriver driver) {
        setDriver(driver);
    }

    private void log(String logMessage) {
        if (logMessage != null)
            logger.log(logMessage);
    }

    private synchronized void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

    /**
     * Get driver for correct thread
     *
     * @return - WebDriver
     */
    public synchronized WebDriver getDriver() {
        return this.driver.get();
    }

    /**
     * To launch the given URL
     *
     * @param url        - URL string
     * @param logMessage - Message for logging
     */
    public void get(String url, String logMessage) {
        getDriver().get(url);
        log(logMessage + " " + url);
    }

    /**
     * To click the locator on the page
     *
     * @param locator - By element locator
     */
    public void click(By locator) {
        click(locator, null, null, null);
    }

    /**
     * To click the locator on the page
     *
     * @param locator    - By element locator
     * @param logMessage - Message for logging
     */
    public void click(By locator, String logMessage) {
        click(locator, logMessage, null, null);
    }

    /**
     * To click the locator on the page
     *
     * @param locator     - By element locator
     * @param logMessage  - Message for logging
     * @param maxWaitTime - The Max wait time
     */
    public void click(By locator, String logMessage, Integer maxWaitTime) {
        click(locator, logMessage, maxWaitTime, null);
    }

    /**
     * To click the locator on the page
     *
     * @param locator     - By element locator
     * @param logMessage  - Message for logging
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     */
    public void click(By locator, String logMessage, Integer maxWaitTime, Integer pollTime) {
        this.waitUntilElementToBeClickable(locator, maxWaitTime, pollTime, logMessage).click();
        log(logMessage);
    }

    /**
     * To click the locator on the page
     *
     * @param locator    - By element locator
     * @param logMessage - Message for logging
     */
    public void clickRadioButton(By locator, String logMessage) {
        clickRadioButton(locator, logMessage, null, null);
    }

    /**
     * To click a radion button by the locator on the page
     *
     * @param locator     - By element locator
     * @param logMessage  - Message for logging
     * @param maxWaitTime - The Max wait time
     */
    public void clickRadioButton(By locator, String logMessage, Integer maxWaitTime) {
        clickRadioButton(locator, logMessage, maxWaitTime, null);
    }

    /**
     * To click a radion button by the locator on the page
     *
     * @param locator     - By element locator
     * @param logMessage  - Message for logging
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     */
    public void clickRadioButton(By locator, String logMessage, Integer maxWaitTime, Integer pollTime) {
        Actions action = new Actions(getDriver());
        WebElement radioBtn = this.findElement(locator, maxWaitTime, pollTime, logMessage);
        action.click(radioBtn).build().perform();
        log(logMessage);
    }

    /**
     * To enter value in the text field
     *
     * @param locator      - By element locator
     * @param valueToEnter - Text to enter
     * @param clear        - To clear field
     * @param logMessage   - Message for logging
     */
    public void sendKeys(By locator, String valueToEnter, boolean clear, String logStep, String logMessage) {
        sendKeys(locator, valueToEnter, clear, logMessage, null, null);
    }

    /**
     * To enter value in the text field
     *
     * @param locator      - By element locator
     * @param valueToEnter - Text to enter
     * @param logMessage   - Message for logging
     */
    public void sendKeys(By locator, String valueToEnter, String logMessage) {
        sendKeys(locator, valueToEnter, true, logMessage, null, null);
    }

    /**
     * To enter value in the text field
     *
     * @param locator      - By element locator
     * @param valueToEnter - Text to enter
     * @param logMessage   - Message for logging
     * @param maxWaitTime  - The Max wait time
     * @param pollTime     - Poll interval
     */
    public void sendKeys(By locator, String valueToEnter, String logMessage,
                         Integer maxWaitTime, Integer pollTime) {
        sendKeys(locator, valueToEnter, true, logMessage, maxWaitTime, pollTime);
    }

    /**
     * To enter value in the text field
     *
     * @param locator      - By element locator
     * @param valueToEnter - Text to enter
     * @param clear        - To clear field
     * @param logMessage   - Message for logging
     * @param maxWaitTime  - The Max wait time
     * @param pollTime     - Poll interval
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
     * @param locator     - By element locator
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
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
     * @param locator - By element locator
     * @return - true - if visible, false - if not visible
     */
    public boolean isElementVisible(By locator) {
        return isElementVisible(locator, null, null);
    }

    /**
     * To check if Element is found in the DOM
     *
     * @param locator - By element locator
     * @return - true - if found, false - if not found
     */
    public boolean isElementFound(By locator) {
        return isElementFound(locator, maxWaitTime, pollTime);
    }

    /**
     * To check if Element is visible or not
     *
     * @param locator     - By element locator
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - true - if visible, false - if not visible
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
     * @param locator     - By element locator
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - true - if clickable, false - if not clickable
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
     * @param locator - By element locator
     * @return - true - if clickable, false - if not clickable
     */
    public WebElement waitUntilElementToBeClickable(By locator) {
        return waitUntilElementToBeClickable(locator, null, null, "");
    }

    /**
     * To wait until element to be clickable on the page
     *
     * @param locator     - By element locator
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - Clickable WebElement
     */
    public WebElement waitUntilElementToBeClickable(By locator, Integer maxWaitTime, Integer pollTime, String logMessage) {
        this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.elementToBeClickable(locator));
        return this.findElement(locator, maxWaitTime, pollTime, logMessage);
    }

    /**
     * To find the element on the page
     *
     * @param locator - By element locator
     * @return - WebElement
     */
    public WebElement findElement(By locator) {
        return getDriver().findElement(locator);
    }

    /**
     * To find the elements on the page
     *
     * @param locator - By element locator
     * @return - List of WebElements
     */
    public List<WebElement> findElements(By locator) {
        return getDriver().findElements(locator);
    }

    /**
     * To find the element on the page
     *
     * @param locator     - By element locator
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - WebElement
     */
    public WebElement findElement(By locator, Integer maxWaitTime, Integer pollTime, String logMessage) {
        if (isElementFound(locator, maxWaitTime, pollTime)) {
            return getDriver().findElement(locator);
        } else {
            throw new RuntimeException(logMessage + " And It Was Not Found!");
        }
    }

    /**
     * To get the 'Wait' object with the given Maximum Wait Time and Poll Time.
     *
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
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

    /**
     * Click element using javascript
     *
     * @param element    - Web element
     * @param logMessage - Message for logging
     */
    public void jsClick(WebElement element, String logMessage) {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) getDriver();
        javaScriptExecutor.executeScript("arguments[0].click();", element);
        log(logMessage);
    }

    /**
     * Click element using javascript
     *
     * @param element - Web element
     */
    public void jsClick(WebElement element) {
        jsClick(element, null);
    }

    /**
     * Wait till element is clickable and return it
     *
     * @param maxWaitTime - The Max wait time -
     * @param pollTime    - Poll interval
     * @return - Clickable WebElement
     */

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
     * @param locator     - By element locator
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - WebElement
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
     * @param locator - By element locator
     * @return - WebElement
     */
    public WebElement waitForElementToBeFound(By locator) {
        return waitForElementToBeFound(locator, null, null);
    }

    /**
     * Wait for element to be visible on the page
     *
     * @param locator     - By element locator
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - Visible WebElement
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
     * @param element     - By element locator
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - Clickable WebElement
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
     * @param locator     - By element locator
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - Invisible WebElement
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
     * @param element     - WebElement
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - Invisible WebElement
     */
    public WebElement waitForElementToBeInVisible(WebElement element, Integer maxWaitTime, Integer pollTime) {
        try {
            this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.invisibilityOf(element));
            return element;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Wait for element to be invisible and return it
     *
     * @param locator - By element locator
     * @return - Invisible WebElement
     */
    public WebElement waitForElementToBeInVisible(By locator) {
        return waitForElementToBeInVisible(locator, null, null);
    }

    /**
     * Wait for element to be invisible and return it
     *
     * @param element - WebElement
     * @return - Invisible WebElement
     */
    public WebElement waitForElementToBeInVisible(WebElement element) {
        return waitForElementToBeInVisible(element, null, null);
    }

    /**
     * Wait for element to be invisible and return it
     *
     * @param locator - By element locator
     * @return - Clickable WebElement
     */
    public WebElement waitForElementToBeClickable(By locator) {
        return waitForElementToBeClickable(locator, null, null);
    }

    /**
     * Wait for element to be visible on the page and return it
     *
     * @param locator - By element locator
     * @return - Visible WebElement
     */
    public WebElement waitForElementToBeVisible(By locator) {
        return waitForElementToBeVisible(locator, null, null);
    }

    /**
     * Wait for element to be clickable on the page
     *
     * @param element - WebElement
     * @return - Clickable WebElement
     */
    public WebElement waitForElementToBeClickable(WebElement element) {
        return waitForElementToBeClickable(element, null, null);
    }

    /**
     * To return dropdown (select)
     *
     * @param locator     - By element locator
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - Selenium's select object
     */
    private Select select(By locator, Integer maxWaitTime, Integer pollTime) {
        return new Select(this.waitForElementToBeVisible(locator, maxWaitTime, pollTime));
    }

    /**
     * Select dropdown by value
     *
     * @param locator     - By element locator
     * @param value       - Value to select
     * @param logMessage  - Message for logging
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     */
    public void selectByValue(By locator, String value, String logMessage, Integer maxWaitTime, Integer pollTime) {
        this.select(locator, maxWaitTime, pollTime).selectByValue(value);
        log(logMessage);
    }

    /**
     * Select drop down by visible text
     *
     * @param locator     - By element locator
     * @param visibleText - Text to select
     * @param logMessage  - Message for logging
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     */
    public void selectByVisibleText(By locator, String visibleText, String logMessage, Integer maxWaitTime, Integer pollTime) {
        this.select(locator, maxWaitTime, pollTime).selectByVisibleText(visibleText);
        log(logMessage);
    }

    /**
     * Select drop down by given index
     *
     * @param locator     - By element locator
     * @param index       - Index in dropdown
     * @param logMessage  - Message for logging
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     */
    public void selectByIndex(By locator, Integer index, String logMessage, Integer maxWaitTime, Integer pollTime) {
        this.select(locator, maxWaitTime, pollTime).selectByIndex(index);
        log(logMessage);
    }

    /**
     * Get text from WebElement
     *
     * @param locator     - By element locator
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - String present in the element
     */
    public String getText(By locator, Integer maxWaitTime, Integer pollTime) {
        return this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.presenceOfElementLocated(locator)).getText();
    }

    /**
     * Get text from WebElement
     *
     * @param locator - By element locator
     * @return - String present in the element
     */
    public String getText(By locator) {
        return getText(locator, null, null);
    }

    /**
     * Get text from HTML attribute
     *
     * @param locator       - By element locator
     * @param attributeName - Name of HTML attribute
     * @return - String present in the attribute
     */
    public String getAttribute(By locator, String attributeName) {
        return getAttribute(locator, attributeName, null, null);
    }

    /**
     * Get text from HTML attribute
     *
     * @param locator       - By element locator
     * @param attributeName - Name of HTML attribute
     * @param maxWaitTime   - The Max wait time
     * @param pollTime      - Poll interval
     * @return - String present in the attribute
     */
    public String getAttribute(By locator, String attributeName, Integer maxWaitTime, Integer pollTime) {
        return this.getWait(maxWaitTime, pollTime).until(ExpectedConditions.presenceOfElementLocated(locator)).getAttribute(attributeName);
    }

    /**
     * Switch WebDriver focus to the frame by locator
     *
     * @param locator     - By element locator
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - WebDriver with focus to the frame
     */
    public WebDriver switchToFrame(By locator, Integer maxWaitTime, Integer pollTime) {
        return getDriver().switchTo().frame(this.waitForElementToBeFound(locator, maxWaitTime, pollTime));
    }

    /**
     * Switch WebDriver focus to the frame by locator
     *
     * @param locator - By element locator
     * @return - WebDriver with focus to the frame
     */
    public WebDriver switchToFrame(By locator) {
        return switchToFrame(locator, null, null);
    }

    /**
     * Switch WebDriver focus to the frame by name
     *
     * @param name - Name of the frame
     * @return - WebDriver with focus to the frame
     */
    public WebDriver switchToFrame(String name) {
        return getDriver().switchTo().frame(name);
    }

    /**
     * Switch WebDriver focus to the parent frame
     *
     * @return - WebDriver with focus to the parent frame
     */
    public WebDriver switchToParentFrame() {
        return getDriver().switchTo().parentFrame();
    }

    /**
     * Switch WebDriver focus to the top (main) frame
     *
     * @return - WebDriver with focus to top (main) frame
     */
    public WebDriver switchToDefaultContent() {
        return getDriver().switchTo().defaultContent();
    }

    /**
     * Switch WebDriver focus to the frame by ID
     *
     * @param id - ID of the frame
     * @return - WebDriver with focus the frame
     */
    public WebDriver switchFrameById(String id) {
        return getDriver().switchTo().frame(id);
    }

    /**
     * Switch WebDriver focus to the frame by index
     *
     * @param index - index of the frame
     * @return - WebDriver with focus the frame
     */
    public WebDriver switchFrameByIndex(int index) {
        return getDriver().switchTo().frame(index);
    }

    /**
     * Close window (tab)
     */
    public synchronized void closeDriver() {
        getDriver().close();
    }

    /**
     * Close the browser
     */
    public synchronized void quitDriver() {
        getDriver().quit();
    }

    /**
     * Scroll to page top
     */
    public void scrollTop() {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) getDriver();
        javaScriptExecutor.executeScript("window.scrollTo(0,0)");
    }

    /**
     * Scroll down 250 pixels
     */
    public void scrollDown() {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) getDriver();
        javaScriptExecutor.executeScript("window.scrollTo(0,250)");
    }

    /**
     * Zoom out to 67%
     */
    public void zoomOut() {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("document.body.style.zoom = '67%'");
    }

    /**
     * Wait for file to be downloaded
     *
     * @param downloadDir - Directory to download
     * @param fileName    - Name of the file
     */
    public void waitDownloading(String downloadDir, String fileName) {
        Path dowloadFilePath = Paths.get(downloadDir, fileName);
        new WebDriverWait(getDriver(), 100).until(d -> dowloadFilePath.toFile().exists());
    }

    /**
     * Scroll to the complete bottom of the page
     */
    public void scrollToBottom() {
        ((JavascriptExecutor) driver.get()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * To return the UI attribute on an element on UI
     *
     * @param element       - By element locator
     * @param attributeName - Name of the attribute
     * @param logMessage    - Message for logging
     * @return UI attribute - UI attribute text
     */
    public String getUiAttribute(By element, String attributeName, String logMessage) {
        waitForElementToBeFound(element);
        return getUiAttribute(element, attributeName, logMessage, null);
    }

    /**
     * To return the UI attribute on an element on UI
     *
     * @param element       - By element locator
     * @param attributeName - Name of the attribute
     * @param logMessage    - Message for logging
     * @param pollTime      - Poll interval
     * @return - UI attribute text
     */
    public String getUiAttribute(By element, String attributeName, String logMessage, Integer pollTime) {
        String attributeProperty = getDriver().findElement(element).getCssValue(attributeName);
        log(logMessage);
        if (attributeName.contains("color")) {
            return Color.fromString(attributeProperty).asHex();
        } else {
            return attributeProperty;
        }
    }

    /**
     * To check if element has some text
     *
     * @param locator     - By element locator
     * @param text        - Text to look for
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - true if element contains text, false if not
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
     * @param locator     - By element locator
     * @param text        - Text to look for
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - true if html contains text, false if not
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
     * @param text        - Text to look for
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - true if url contains text, false if not
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
     * @param text        - Text to look for
     * @param maxWaitTime - The Max wait time
     * @param pollTime    - Poll interval
     * @return - true if page contains text, false if not
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

    /**
     * Scroll till the element is in the view
     *
     * @param webElement - WebElement
     */
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

    /**
     * Wait for two seconds
     */
    public void waitTwoSeconds() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
