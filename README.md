# Context-Free Grammar Optimizer API 🚀

A Spring Boot REST API that takes a Context-Free Grammar (CFG) and optimizes it by removing useless symbols and non-accessible variables. 

*Note: This project was originally developed as a CLI tool and has been migrated to a robust RESTful API architecture.*

## 📖 What it does

When working with Context-Free Grammars, it is common to end up with rules that serve no purpose. This API cleans up grammars by performing two primary optimizations:
1. **Removing Useless Variables:** Eliminates variables that cannot ultimately derive a string consisting entirely of terminal symbols.
2. **Removing Non-Accessible Variables:** Eliminates variables (and their associated rules) that can never be reached from the starting symbol of the grammar.

## 🛠️ Technologies Used
* **Java** * **Spring Boot**
* **Maven**

---

## 🚀 Getting Started

### Prerequisites
To run this project locally, you will need:
* Java Development Kit (JDK) 17 or higher
* Git

### Installation & Running Locally

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/Suarzz/Context-Free-Grammar-Optimizer.git](https://github.com/Suarzz/Context-Free-Grammar-Optimizer.git)
   cd Context-Free-Grammar-Optimizer
   
2. **Run the Spring Boot application:**
You don't need Maven installed on your machine, as the project includes the Maven Wrapper (mvnw). Run the following command in your terminal:

On Windows:
	```bash
	mvnw.cmd spring-boot:run

On Mac/Linux:
	```bash
	./mvnw spring-boot:run

3. **Access the API**
The server will start on http://localhost:8080.

## API usage

**Optimize Grammar**
Endpoint: POST /optimize 

Accepts a JSON representation of a Context-Free Grammar and returns the optimized version.

**Example Request**

POST http://localhost:8080/optimize
Content-Type: application/json

	{
		"variables": ["S", "A", "B", "C"],
		"productions": {
			"S": "A|B",
			"A": "a",
			"B": "b",
	    "C": "B"
		}
	}

*In this example, C is useless because it is impossible to access, so it gets removed*

**Example response**
	{
	    "variables": [
	        "S",
	        "A",
	        "B"
	    ],
	    "productions": {
	        "A": "a",
	        "B": "b",
	        "S": "A | B"
	    }
	}

## 📝 License
This project is licensed under the MIT License - see the LICENSE file for details.
