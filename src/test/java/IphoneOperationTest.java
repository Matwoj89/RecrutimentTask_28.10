import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IphoneOperationTest extends BaseTest {

            /*wchodzimy na strone www.allegro.pl
        wpisujemy w wyszukiwarke Iphone 11
        wybieramy kolor czarny
        zliczamy ilość wyświetlonych telefonów na pierwszej stronie wyników i ilość prezentujemy w consoli
        szukamy największej ceny na liście i wyświetlamy w konsoli
        do największej ceny dodajemy 23% Jak będzie miał Pan/Pani gotowy skrypt,
        to proszę go wexportować lub wrzucić gdzieś do Gita i podać linka*/

    private By searchBarLocator = By.cssSelector("input[data-role='search-input']");
    private By submitButtonLocator = By.cssSelector("button[data-role='search-button']");
    private By searchTitleLocator = By.cssSelector("div[data-box-name='Listing title']>div>h1>span:last-child");
    private By blackColorLocator = By.xpath(".//label[contains(., 'czarny')]");
    private By smartphoneLocator = By.cssSelector("a[data-key='Elektronika'] + ul>li[data-role='LinkItem']>a");
    private By colorFilterLocator = By.cssSelector("h3[data-slot='Kolor']");
    private By maxPriceLocator = By.cssSelector("article[data-analytics-view-custom-index0='2']>div>div+div>div+div>div>div>span");


    @Test
    public void insertProductIntoSearchTest() {
        WebElement searchBar = driver.findElement(searchBarLocator);
        actions.sendKeys(searchBar, "Iphone 11").click().build().perform();
        driver.findElement(submitButtonLocator).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(searchTitleLocator));
        assertEquals("Iphone 11", driver.findElement(searchTitleLocator).getText(), "coś sie popsiuło");
    }

    @Test
    public void chooseColorTest() {

        WebElement searchBar = driver.findElement(searchBarLocator);
        actions.sendKeys(searchBar, "Iphone 11").click().build().perform();
        driver.findElement(submitButtonLocator).click();

        driver.findElement(smartphoneLocator).click();

        wait.until(ExpectedConditions.elementToBeClickable(blackColorLocator));

        WebElement colorFilterElement = driver.findElement(colorFilterLocator);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", colorFilterElement);

        driver.findElement(blackColorLocator).click();
        String URL = driver.getCurrentUrl();
        assertTrue(URL.contains("kolor=czarny"), "coś jest nie tak");
    }

    @Test
    public void productQuantity() {

        WebElement searchBar = driver.findElement(searchBarLocator);
        actions.sendKeys(searchBar, "Iphone 11").click().build().perform();
        driver.findElement(submitButtonLocator).click();

        driver.findElement(smartphoneLocator).click();

        wait.until(ExpectedConditions.elementToBeClickable(blackColorLocator));

        WebElement colorFilterElement = driver.findElement(colorFilterLocator);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", colorFilterElement);

        driver.findElement(blackColorLocator).click();

        List<WebElement> elements = driver.findElements(By.cssSelector("article[data-analytics-view-custom-index0]"));
        System.out.println("Quantity of visible products on page is: " + elements.size());
    }

    @Test
    public void maxPriceTest() {

        WebElement searchBar = driver.findElement(searchBarLocator);
        actions.sendKeys(searchBar, "Iphone 11").click().build().perform();
        driver.findElement(submitButtonLocator).click();

        driver.findElement(smartphoneLocator).click();

        wait.until(ExpectedConditions.elementToBeClickable(blackColorLocator));

        WebElement colorFilterElement = driver.findElement(colorFilterLocator);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", colorFilterElement);

        driver.findElement(blackColorLocator).click();

        List<WebElement> elements = driver.findElements(By.cssSelector("article[data-analytics-view-custom-index0]"));
        System.out.println("Quantity of visible products on page is: " + elements.size());

        WebElement dropdownList = driver.findElement(By.cssSelector("select[data-value='m']"));
        Select windsurfingDropdownList = new Select(dropdownList);

/*
        WebElement liczbaZBoxu = driver.findElement(By.cssSelector("div[data-box-name='sorting']"));
        String nowaLiczbaZboxu = liczbaZBoxu.getAttribute("data-visible-for");
*/


        windsurfingDropdownList.selectByIndex(2);

        WebElement allegroSpinner = driver.findElement(By.cssSelector("a[name='allegro-spinner']"));
        wait.until(ExpectedConditions.invisibilityOf(allegroSpinner));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement maxPriceElement = driver.findElement(maxPriceLocator);
        String kwota = maxPriceElement.getText();

        System.out.println(kwota);

        //    kwota.substring(0,4);

        String part1 = kwota.substring(0, 1);
        String part2 = kwota.substring(2, 5);

        String wynik = part1 + part2;

        System.out.println(wynik);

        double finitoNumber = Integer.valueOf(wynik);
        double liczba = finitoNumber * 1.23;

        System.out.printf("The total price is: %.2f\n", liczba);
    }
}


