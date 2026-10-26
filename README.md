# Noon Egypt – Automation Testing Project

## Project Overview

This project is an automated testing framework for the **Noon Egypt e-commerce website**, developed using Java, Selenium WebDriver, TestNG, and Maven.

The framework automates essential product-shopping workflows, including product search, price filtering, cart management, invalid search validation, and free-shipping product selection.

It follows a Page Object Model (POM) structure and uses external test data to support maintainable, reusable, and data-driven test execution. It also supports browser selection through TestNG configuration and generates test execution reports using Allure.

## Project Demo

**Test Execution Recording:** [Watch the Automation Demo](https://drive.google.com/file/d/1pQKOQy38bsYEslyzmh_CSxG-0HzJv_2e/view?usp=sharing)

The recording should demonstrate the automated test execution and the resulting behavior of the test suite.

## Key Features

- **Page Object Model (POM):** Separates page interactions from test logic.
- **Data-Driven Testing (DDT):** Executes test scenarios using external CSV and JSON data.
- **Multi-Browser Testing:** Supports browser selection through TestNG configuration and browser-related test data.
- **CSV File Handling:** Reads test data and exports selected product information using OpenCSV.
- **JSON File Handling:** Manages structured test data using JSON files.
- **Product Search and Filtering:** Automates product discovery and price-filter validation.
- **Shopping Cart Validation:** Verifies selected products and relevant cart information.
- **TestNG Integration:** Organizes test execution, assertions, and data providers.
- **Allure Reporting:** Generates test execution reports for analysis.
- **Maven Build Management:** Manages project dependencies and test execution.

## Technology Stack

| Technology                | Purpose                             |
| ------------------------- | ----------------------------------- |
| Java 17                   | Programming language                |
| Selenium WebDriver 4.49.0 | Browser automation                  |
| TestNG 7.10.2             | Test execution and assertions       |
| Maven                     | Dependency and build management     |
| Allure Reports 3.0.0      | Test reporting                      |
| OpenCSV                   | CSV data reading and writing        |
| JSON                      | External test data management       |
| Git and GitHub            | Version control and project hosting |
| IntelliJ IDEA             | Development environment             |

## Project Architecture

The project uses the Page Object Model to organize browser interactions, reusable components, test data, and test cases into separate layers.

### Project Structure

```text
Noon-Automation-Testing/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/example/
│   │           ├── base/
│   │           │   └── BasePage.java
│   │           │
│   │           └── pages/
│   │               ├── CartPage.java
│   │               ├── InvalidSearchPage.java
│   │               ├── ProductListingPage.java
│   │               └── SearchResultsPage.java
│   │
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   └── BaseTest.java
│       │   │
│       │   ├── data/
│       │   │   ├── BrowserDataProvider.java
│       │   │   └── SearchDataProvider.java
│       │   │
│       │   ├── tests/
│       │   │   ├── AddMultipleFreeShippingProductsTest.java
│       │   │   ├── AddMultipleProductsToCartTest.java
│       │   │   ├── BrowseAndFilterProductsTest.java
│       │   │   └── InvalidSearchTest.java
│       │   │
│       │   └── utilities/
│       │       ├── CSVFileManager.java
│       │       └── JSONFileManager.java
│       │
│       └── resources/
│           ├── budgetData.json
│           ├── filterPrice.csv
│           ├── freeShippingProducts.csv
│           ├── invalidSearchKeywords.json
│           └── products.csv
│
├── pom.xml
├── testng.xml
└── README.md
```

## Component Responsibilities

### 1. Base Classes

| File            | Responsibility                                                                    |
| --------------- | --------------------------------------------------------------------------------- |
| `BasePage.java` | Provides reusable page-level functionality for interacting with web elements.     |
| `BaseTest.java` | Manages common test setup and teardown, including WebDriver lifecycle management. |

### 2. Page Objects

| File                      | Responsibility                                                                 |
| ------------------------- | ------------------------------------------------------------------------------ |
| `CartPage.java`           | Encapsulates interactions with the shopping cart and cart-related information. |
| `InvalidSearchPage.java`  | Encapsulates page interactions required for invalid-search scenarios.          |
| `ProductListingPage.java` | Handles interactions with product listing and filtering functionality.         |
| `SearchResultsPage.java`  | Handles interactions with product search results.                              |

### 3. Test Classes

| File                                       | Responsibility                                                              |
| ------------------------------------------ | --------------------------------------------------------------------------- |
| `BrowseAndFilterProductsTest.java`         | Tests product browsing and price-filtering behavior.                        |
| `AddMultipleProductsToCartTest.java`       | Tests selecting multiple products and validating cart-related requirements. |
| `AddMultipleFreeShippingProductsTest.java` | Tests selecting products that meet the configured free-shipping criteria.   |
| `InvalidSearchTest.java`                   | Tests search behavior for invalid or unmatched keywords.                    |

### 4. Test Data Providers

| File                       | Responsibility                                                     |
| -------------------------- | ------------------------------------------------------------------ |
| `BrowserDataProvider.java` | Supplies browser-related parameters for configured test execution. |
| `SearchDataProvider.java`  | Supplies search inputs to data-driven test methods.                |

### 5. Utilities

| File                   | Responsibility                                                     |
| ---------------------- | ------------------------------------------------------------------ |
| `CSVFileManager.java`  | Handles CSV data access and file operations used by the framework. |
| `JSONFileManager.java` | Reads and manages JSON-based test data.                            |

### 6. Configuration and Resources

| File                         | Responsibility                                                        |
| ---------------------------- | --------------------------------------------------------------------- |
| `pom.xml`                    | Defines project dependencies, build configuration, and Maven plugins. |
| `testng.xml`                 | Configures TestNG test execution and applicable parameters.           |
| `products.csv`               | Stores product-related test data.                                     |
| `filterPrice.csv`            | Provides data for price-filtering scenarios.                          |
| `freeShippingProducts.csv`   | Stores data associated with free-shipping product scenarios.          |
| `budgetData.json`            | Stores budget-related test configuration.                             |
| `invalidSearchKeywords.json` | Provides invalid or unmatched search keywords.                        |

## Automated Test Scenarios

### 1. Browse and Filter Products

- Browse product listings and search for products.
- Apply the configured price-filtering criteria.
- Validate that the displayed products satisfy the expected price conditions.

### 2. Add Multiple Products to Cart

- Search for products using configured search inputs.
- Retrieve relevant product information, including names and prices.
- Select multiple products according to the configured budget requirements.
- Add selected products to the shopping cart.
- Validate applicable product-count and total-price conditions.

### 3. Add Multiple Free-Shipping Products

- Identify products that meet the configured free-shipping criteria.
- Select multiple eligible products.
- Add selected products to the shopping cart.
- Validate the selected products against the scenario requirements.

### 4. Invalid Product Search

- Load invalid search keywords from an external JSON file.
- Execute searches using the provided inputs.
- Validate the application's response against the expected behavior for unmatched searches.

### 5. Data-Driven Test Execution

- Supply multiple test inputs through TestNG DataProviders.
- Separate test data from test logic using external CSV and JSON files.
- Execute supported scenarios with different input values.

### 6. Multi-Browser Test Execution

- Supply browser-related parameters through the configured test data and TestNG setup.
- Initialize the browser according to the supported configuration.
- Execute applicable test scenarios in the selected browser environment.

### 7. CSV Reading and Writing

- Read product and filtering data from CSV files.
- Use external CSV data in automated test scenarios.
- Write selected product information to CSV output where implemented.
- Keep test inputs and exported results organized separately from test logic.

## Framework Design Principles

The framework is organized around the following principles:

- **Separation of concerns:** Page interactions, test execution, test data, and utility operations are organized into separate components.
- **Reusability:** Shared browser and page-level operations can be reused across test cases.
- **Maintainability:** Page objects and utility classes reduce duplication and simplify updates.
- **Data-driven execution:** External data files and TestNG DataProviders allow test scenarios to use multiple inputs.
- **Validation:** TestNG assertions verify expected behavior and test outcomes.
- **Reporting:** Allure reports provide a structured view of test execution results.

## Prerequisites

Ensure the following tools are installed and configured:

- Java Development Kit (JDK) 17
- Apache Maven
- Google Chrome or another browser supported by the project configuration
- A compatible WebDriver setup
- Allure command-line tool
- Git
- IntelliJ IDEA or another Java IDE

## Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/dinaahmed13/Noon-Automation-Testing.git
```

### 2. Navigate to the Project Directory

```bash
cd Noon-Automation-Testing
```

### 3. Install Dependencies and Build the Project

```bash
mvn clean install
```

This command cleans previous Maven build output, resolves dependencies, compiles the project, and executes the tests configured for the Maven lifecycle.

## Running the Tests

Execute the test suite using Maven:

```bash
mvn test
```

To execute the suite using the TestNG XML configuration, use:

```bash
mvn test -Dsurefire.suiteXmlFiles=testng.xml
```

The exact execution behavior depends on the Maven Surefire configuration in `pom.xml`.

## Allure Test Reports

Allure is used to generate reports that help review test execution results.

### 1. Generate the Report

After executing the tests and confirming that Allure result files have been generated, run:

```bash
allure generate allure-results --clean -o allure-report
```

### 2. Open the Report

```bash
allure open allure-report
```

The report provides a visual overview of recorded test results and any test failures captured by the configured reporting integration.

## Clean Execution Workflow

For a fresh test run on Windows PowerShell, remove previous Allure results and reports before executing the suite:

```powershell
Remove-Item -Recurse -Force allure-results, allure-report -ErrorAction SilentlyContinue
```

Then run:

```bash
mvn clean install
```

Generate and open the new report:

```bash
allure generate allure-results --clean -o allure-report
allure open allure-report
```

**Note:** If the project stores additional execution logs in another directory, clean that directory separately when required. The commands above remove the specified Allure directories; they do not remove every possible log file.

## Expected Results

The test suite is designed to validate the following behaviors:

- Product search and browsing work according to the configured scenarios.
- Displayed products satisfy the specified price-filtering conditions.
- Selected products satisfy the applicable budget and cart requirements.
- Free-shipping scenarios select products according to the configured criteria.
- Invalid search inputs produce the expected application behavior.
- Configured browser parameters are applied during test execution.
- CSV and JSON test data are consumed by the relevant test scenarios.
- Test execution results are available through the configured Allure reporting workflow.

Actual outcomes depend on the current website behavior, test data, browser configuration, and execution environment.


## GitHub Repository

[Noon Egypt – Automation Testing Project](https://github.com/dinaahmed13/Noon-Automation-Testing)

## Author

**Dina Ahmed**

