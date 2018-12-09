# BookingAutoTest
A automation test exercise for booking.com. 
It developed by TestNG+Selenium WebDriver on Eclipse IDE.

  - Search a hotel in Los Angeles on [booking.com](https://www.booking.com)
  - Select a hotel in the results (1st one is fine)
  - Select a room
  - Get to checkout screen

## Table of Contents

* [To get started](#to-get-started)
* [Eclipse IDE settings](#eclipse-ide-settings)
* [Run Tests in Eclipse](#run-tests-in-eclipse)
* [Tools](#tools)

## To get started

* You need a test enviornment with internet access.
* You need a Chrome browser and/or a Firefox browser installed in your test enviornment.
* You need to install JDK1.8.0 in your test enviornment. 
  * You can get Java from [Java downloads page](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
* You need to install the Eclipse IDE.
  * Download Eclipse for Java Developers, extract and save it in any drive. It is totally free. You can run ‘eclipse.exe’ directly so you do not need to install Eclipse in your system.
  * Go to http://www.eclipse.org/downloads to find out a proper install file for your test enviornment.
* You need to setup Webdriver Java Client
  * Go on [WebDriver Java client driver download page](https://docs.seleniumhq.org/download/) for WebDriver download file. On that page click on ‘Download‘ link of java client driver.
  * Once you have downloaded the archive you will need to Extract the zip file, which will create the unzipped Selenium Java folder.There will be ‘libs‘ folder, 2 jar files and change log in unzipped folder as shown in bellow figure. We will use all these files for configuring WebDriver in Eclipse.

## Eclipse IDE settings

* Configure Eclipse with Selenium WebDriver
  * Launch the Eclipse IDE & Create a Workspace
  * Create a new Java project
  * Create a new Package
  * Create a new Class
  * Add External Jars to Java build path - client-combined-3.141.59-sources.jar and client-combined-3.141.59.jar
  * Add all the jars from the libs folder as well.
* Install TestNG in Eclipse
  * Launch the Eclipse IDE and from Help menu, click “Install New Software”.
  * You will see a dialog window, click “Add” button.
  * Type name as you wish, lets take “TestNG” and type “http://beust.com/eclipse/” as location. Click OK.
  * Step by Step to install and you may or may not encounter a Security warning, if in case you do just click OK.
  * Restart the Eclipse.

## Run tests in Eclipse

* Run as TestNG Test
  * Select "Booking_SearchAndReserve.java' file, right click this file and select "Run As" -> "TestNG Test".
* Run as TestNG Suite
  * Select "testng.xml' file, right click this file and select "Run As" -> "TestNG Suite".

## Tools

* [chromedriver] - WebDriver for Chrome
* [geckodriver] - WebDriver for Firefox
