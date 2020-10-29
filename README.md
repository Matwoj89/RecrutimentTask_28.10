# Product Name
> Task prepared for the interview

Instructions:
* wchodzimy na strone www.allegro.pl
* wpisujemy w wyszukiwarke Iphone 11
* wybieramy kolor czarny
* zliczamy ilość wyświetlonych telefonów na pierwszej stronie wyników i ilość prezentujemy w consoli
* szukamy największej ceny na liście i wyświetlamy w konsoli
* do największej ceny dodajemy 23% 

## About
Scrypt was written with Intelij IDEA and uses:
* Selenium 3.14
* Junit 5.2.0
* webdriverManager 4.2.2
> Has 2 classes:
* BaseClass - abstract class dedicated to configuration
* ProductTestClass - with 3 test methods and additional methods supporting them

> By running the maxPriceTest() test method, you can see all the job requirements are met

## Development setup

With WebdriverManger you don't need to store the driver locally on disk. Webdrivermanager allows the user to change the browser as he needs. To do this you must in BaseClass, in lines 27 and 28, change parameters to the following:
* Chrome
- WebDriverManager.chromedriver().setup();
- driver = new ChromeDriver();
* Firefox
- WebDriverManager.firefoxdriver().setup();
- driver = new FirefoxDriver();
* Opera
- WebDriverManager.operadriver().setup();
- driver = new OperaDriver();
* Edge
- WebDriverManager.edgedriver().setup();
- driver = new EdgeDriver();
