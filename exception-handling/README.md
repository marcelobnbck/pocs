# Exception Handling in Java

This project demonstrates **exception handling** in Java using:  
Reading numbers from a file, parsing them, and calculating their sum — while handling multiple possible errors.

## Features
- Reads numbers from a file.
- Skips empty lines.
- Calculates the total sum of all valid numbers.
- Handles:
    - `IOException`
    - `NumberFormatException`
    - `NullPointerException`
    - `Exception`
- Uses **try-with-resources** to automatically close file resources.

## How It Works
1. **File Input**  
   The program attempts to open `numbers.txt` and read it line by line.

2. **Parsing**  
   Each non-empty line is trimmed and converted to an integer using `Integer.parseInt()`.

3. **Summation**  
   All valid integers are summed and the total is displayed.

4. **Error Handling**
    - Different `catch` blocks handle specific exceptions first.
    - A `finally` block is used to print a completion message.
    - `try-with-resources` ensures the file is closed automatically.

## Example `numbers.txt` File
```txt
10
20
30
abc <-- Will cause NumberFormatException
40
50
```

## Example Output
```terminaloutput
Invalid number format: For input string: "abc"
Execution completed.
```

Or, if the file is valid:
```terminaloutput
Total sum: 100
Execution completed.
```

## Running the Program
### Prerequisites
- Java 8 or higher installed.

### Steps
1. Save the Java code as `ExceptionHandling.java`.
2. Create a file named `numbers.txt` in the same directory as the `.java` file.
3. Compile the code:
```bash
javac ComplexExceptionHandlingExample.java
```

Run the program:

```bash
java ComplexExceptionHandlingExample
```