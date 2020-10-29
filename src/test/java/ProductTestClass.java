import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTestClass extends BaseClass {

    private By searchBarLocator = By.cssSelector("input[data-role='search-input']");
    private By submitButtonLocator = By.cssSelector("button[data-role='search-button']");
    private By searchTitleLocator = By.cssSelector("div[data-box-name='Listing title']>div>h1>span:last-child");
    private By blackColorLocator = By.xpath(".//label[contains(., 'czarny')]");
    private By smartphoneLocator = By.cssSelector("a[data-key='Elektronika'] + ul>li[data-role='LinkItem']>a");
    private By colorFilterLocator = By.cssSelector("h3[data-slot='Kolor']");
    private By maxPriceLocator = By.cssSelector("article[data-analytics-view-custom-index0='2']>div>div+div>div+div>div>div>span");
    private By productsLocators = By.cssSelector("article[data-analytics-view-custom-index0]");
    private By spinnerLoaderLocator = By.cssSelector("a[name='allegro-spinner']");
    private By dropdownListLocator = By.cssSelector("select[data-value='m']");

    @Test
    public void searchingProductTest() {
        insertProductIntoSearchBar();

        String productName = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTitleLocator)).getText();
        assertEquals("Iphone 11", productName, "Did you enter correctly name of expected product?");
    }

    @Test
    public void selectColorTest() {
        insertProductIntoSearchBar();
        selectBlackColor();

        String Url = driver.getCurrentUrl();
        assertTrue(Url.contains("kolor=czarny"),
                "Please make sure the black color checkbox is selected");
    }

    @Test
    public void maxPriceTest() {
        insertProductIntoSearchBar();
        selectBlackColor();
        getProductQuantity();
        chooseMaxPrice();
        getAndFormatPrice();
        String Url = driver.getCurrentUrl();
        assertTrue(Url.contains("&order=pd"),
                "Please make sure the dropbox is selected to sort by maximum price");

    }

    private void insertProductIntoSearchBar() {
        WebElement searchBar = driver.findElement(searchBarLocator);
        actions.sendKeys(searchBar, "Iphone 11").click().build().perform();
        driver.findElement(submitButtonLocator).click();
    }

    private void selectBlackColor() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(smartphoneLocator));
        driver.findElement(smartphoneLocator).click();
        wait.until(ExpectedConditions.elementToBeClickable(blackColorLocator));
        WebElement colorFilterElement = driver.findElement(colorFilterLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", colorFilterElement);
        driver.findElement(blackColorLocator).click();
    }

    private void getProductQuantity() {
        List<WebElement> listOfVisibleProducts = driver.findElements(productsLocators);
        System.out.println("Quantity of visible products on page is: " + listOfVisibleProducts.size());
    }

    private void chooseMaxPrice() {
        WebElement dropdownList = driver.findElement(dropdownListLocator);
        Select sortDropdownList = new Select(dropdownList);
        sortDropdownList.selectByIndex(2);
        WebElement allegroSpinnerLoader = driver.findElement(spinnerLoaderLocator);
        wait.until(ExpectedConditions.invisibilityOf(allegroSpinnerLoader));
        wait.withTimeout(Duration.ofSeconds(2));
    }

    private void getAndFormatPrice() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(maxPriceLocator));
        WebElement maxPriceElement = driver.findElement(maxPriceLocator);
        String priceFromPage = maxPriceElement.getText();

        String part1 = priceFromPage.substring(0, 1);
        String part2 = priceFromPage.substring(2, 5);
        String price = part1 + part2;

        double priceWithoutVat = Integer.valueOf(price);
        double finallyPrice = priceWithoutVat * 1.23;
        DecimalFormat priceFormat = new DecimalFormat("#.00");

        System.out.println("The price without 0.23 tax is: " + priceWithoutVat + " zł");
        System.out.printf("The total price with added tax is: " + priceFormat.format(finallyPrice) + " zł");

    }
}


