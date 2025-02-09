# UI Test Cucumber Appium

This project is a UI test automation framework built using **Cucumber** and **Appium** for testing Android applications. The framework allows for running tests on either an Android emulator or a physical device.

## Prerequisites

Before getting started, make sure you have the following installed on your system:

- **Java 8** or higher
- **Maven**
- **Android SDK**
- **Appium Server** (can be installed via npm)
- An **Android emulator** or physical device

## Setup

1. Clone this repository to your local machine.

2. Ensure all dependencies are installed by running:
   ```bash
   mvn install
   ```

3. Start the Appium server:
   ```bash
   appium --relaxed-security    
   ```

4. Make sure your Android emulator or physical device is connected and running, for easy and smooth running please install the .apk into the emulator e.g emulator-5554 this happen since this code base not spawning the emulator so you need to manualy to prepare. 

## How to Run Tests

### Running All Tests
To run all test scenarios and test cases, use the following command:

```bash
mvn clean test
```
Result via terminal:
![Alt Text](https://raw.githubusercontent.com/rizkafbrl/ui-test-cucumber-appium/refs/heads/main/images/viaterminal.png)

Result:
![Alt Text](https://raw.githubusercontent.com/rizkafbrl/ui-test-cucumber-appium/refs/heads/main/images/viarunner.png)

### Running Tests with a Specific Scenario Tag
To run tests with a specific scenario or tag, use the following command:

```bash
mvn test -Dcucumber.filter.tags="@yourScenarioTag"
```
Replace @yourScenarioTag with the tag you want to target.

# Clean the project
```bash
mvn clean
```

# Re-install all dependencies
```bash
mvn install
```

# Build the project
```bash
mvn package
```

# Run the tests and generate the report
```bash
mvn verify -Dcucumber.filter.tags="@TC_HG_SRT_01"
```

## Cucumber Tags

You can organize your tests using Cucumber tags in your feature files. Some example tags are:

- @homepage: Tests related to the homepage.
- @login: Tests related to the login functionality.

## Project Structure

- src/test/java: Contains the test step definitions and Appium configuration.
- src/test/resources: Contains the Cucumber feature files.
- pom.xml: Maven configuration file with dependencies.

## Maven Dependencies

Here's the list of libraries included in the pom.xml file:

- Cucumber: For BDD-style testing
  - cucumber-java (version 6.10.4)
  - cucumber-junit (version 6.10.4)
- Appium: For interacting with Android devices
  - java-client (version 7.3.0)
- Selenium: For automating web elements within the app
  - selenium-java (version 3.141.59)
- JUnit: For running tests
  - junit (version 4.13.1)
- AssertJ: For fluent assertions in tests
  - assertj-core (version 3.24.2)

## Sample pom.xml

```xml
<dependencies>
    <!-- Cucumber dependencies -->
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <version>${cucumber.version}</version>
    </dependency>
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-junit</artifactId>
        <version>${cucumber.version}</version>
        <scope>test</scope>
    </dependency>

    <!-- Appium dependencies -->
    <dependency>
        <groupId>io.appium</groupId>
        <artifactId>java-client</artifactId>
        <version>${appium.version}</version>
    </dependency>

    <!-- Selenium dependencies -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>3.141.59</version>
    </dependency>

    <!-- AssertJ dependencies -->
    <dependency>
        <groupId>org.assertj</groupId>
        <artifactId>assertj-core</artifactId>
        <version>${assertj.version}</version>
        <scope>test</scope>
    </dependency>

    <!-- JUnit dependencies -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>${junit.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Reporting

- The framework generates Cucumber reports after test execution.
- The reports will be saved in the target/cucumber-reports directory.

## Troubleshooting

### Appium Server Not Starting
Ensure that you have installed Appium globally via npm:
```bash
npm install -g appium
```

### Device Not Detected
Make sure the Android emulator or physical device is properly connected and recognized by Appium. You can verify device connection by running:
```bash
adb devices
```
### Configuration for Appium Inspector
```
Remote host: 127.0.0.1
Remote port: 4723
```

### Configuration for Appium Inspector
```json
{
  "platformName": "Android",
  "appium:deviceName": "emulator-5555",
  "appium:automationName": "UiAutomator2",
  "appium:appPackage": "com.saucelabs.mydemoapp.android",
  "appium:appActivity": "com.saucelabs.mydemoapp.android.view.activities.SplashActivity",
  "appium:appWaitActivity": "com.saucelabs.mydemoapp.android.view.activities.MainActivity",
  "appium:noReset": true,
  "appium:fullReset": false,
  "appium:newCommandTimeout": 120,
  "appium:adbExecTimeout": 20000,
  "appium:uiautomator2ServerInstallTimeout": 60000,
  "appium:autoGrantPermissions": true
}
```

## License
This project is licensed under the MIT License - see the LICENSE file for details.
Created by Rizka Febrila Sari / rizkafbrl@gmail.com 
