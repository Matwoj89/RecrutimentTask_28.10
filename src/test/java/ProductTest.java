import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.text.DecimalFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest extends BaseTest {

    private By searchBarLocator = By.cssSelector("input[data-role='search-input']");
    private By submitButtonLocator = By.cssSelector("button[data-role='search-button']");
    private By searchTitleLocator = By.cssSelector("div[data-box-name='Listing title']>div>h1>span:last-child");
    private By blackColorLocator = By.xpath(".//label[contains(., 'czarny')]");
    private By smartphoneLocator = By.cssSelector("a[data-key='Elektronika'] + ul>li[data-role='LinkItem']>a");
    private By colorFilterLocator = By.cssSelector("h3[data-slot='Kolor']");
    private By maxPriceLocator = By.cssSelector("article[data-analytics-view-custom-index0='2']>div>div+div>div+div>div>div>span");
    private By productsLocators = By.cssSelector("article[data-analytics-view-custom-index0]");

    @Test
    public void searchingProductTest() {
        insertProductIntoSearchBar();

        wait.until(ExpectedConditions.visibilityOfElementLocated(searchTitleLocator));
        assertEquals("Iphone 11", driver.findElement(searchTitleLocator).getText(),
                "Do you enter correctly name of expected product?");
    }

    @Test
    public void selectColorTest() {
        insertProductIntoSearchBar();
        selectBlackColor();

        String URL = driver.getCurrentUrl();
        assertTrue(URL.contains("kolor=czarny"),
                "Please make sure the black color checkbox is selcted");
    }

    @Test
    public void maxPriceTest() {
        insertProductIntoSearchBar();
        selectBlackColor();
        productQuantity();
        chooseMaxPriceFromDropdownList();
        getAndFormatPrice();
        String URL = driver.getCurrentUrl();
        assertTrue(URL.contains("&order=pd"),
                "Please make sure the dropbox is selected to maximum price sorting");

    }

    public void insertProductIntoSearchBar() {
        WebElement searchBar = driver.findElement(searchBarLocator);
        actions.sendKeys(searchBar, "Iphone 11").click().build().perform();
        driver.findElement(submitButtonLocator).click();
    }

    public void selectBlackColor() {
        driver.findElement(smartphoneLocator).click();
        wait.until(ExpectedConditions.elementToBeClickable(blackColorLocator));
        WebElement colorFilterElement = driver.findElement(colorFilterLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", colorFilterElement);
        driver.findElement(blackColorLocator).click();
    }

    public void productQuantity() {
        List<WebElement> listOfVisibleProducts = driver.findElements(productsLocators);
        System.out.println("Quantity of visible products on page is: " + listOfVisibleProducts.size());
    }

    public void chooseMaxPriceFromDropdownList() {
        WebElement dropdownList = driver.findElement(By.cssSelector("select[data-value='m']"));
        Select windsurfingDropdownList = new Select(dropdownList);
        windsurfingDropdownList.selectByIndex(2);
        WebElement allegroSpinner = driver.findElement(By.cssSelector("a[name='allegro-spinner']"));
        wait.until(ExpectedConditions.invisibilityOf(allegroSpinner));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getAndFormatPrice() {
        WebElement maxPriceElement = driver.findElement(maxPriceLocator);
        String priceFromPage = maxPriceElement.getText();

        String part1 = priceFromPage.substring(0, 1);
        String part2 = priceFromPage.substring(2, 5);
        String price = part1 + part2;

        double priceWithoutVat = Integer.valueOf(price);
        double finallyPrice = priceWithoutVat * 1.23;
        DecimalFormat priceFormat = new DecimalFormat("#.00");

        System.out.printf("The total price with added tax is: " + priceFormat.format(finallyPrice) + " zł");

    }
}


