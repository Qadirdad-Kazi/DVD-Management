# DVD Library Management System

## Project Overview
This DVD Library Management System is a Java application that implements the Model-View-Controller (MVC) architectural pattern. It allows library staff to manage a collection of DVDs, members, and the loan process. The system provides a graphical user interface built with Java Swing and separates the UI (View) from the core system (Model) with a Controller mediating between them.

## Features

The DVD Library System supports the following use cases:

1. **Get Number Available**: Staff can check how many copies of a specific film are available for loan.
2. **List Films**: Staff can view all films borrowed by a specific member, including return dates.
3. **Borrow DVD**: Staff can record when a member borrows a DVD, updating availability status.
4. **Return DVD**: Staff can process DVD returns, making them available for other members.

## System Design

### Architecture
The system follows the MVC (Model-View-Controller) design pattern:

- **Model**: `librarycore` package - Contains the business logic and data structures
- **View**: `librarygui` package - Contains the user interface components
- **Controller**: Mediates between the View and Model

### Key Design Decisions

1. **Separation of Concerns**: The user interface and core system are separate packages that communicate through interfaces, allowing either to be modified without affecting the other.

2. **Data Structure**:
   - Films have unique titles and can have multiple DVD copies
   - Each DVD has a unique identifier
   - Members have unique membership numbers 
   - Members can borrow up to 6 DVDs at a time
   - DVDs must be returned within 3 days

3. **In-Memory Data Store**: The system uses an in-memory data store implementation for simplicity, but the architecture allows for different storage implementations (database, file-based, etc.).

## Running the Application

### Prerequisites
- Java Development Kit (JDK) 11 or higher

### Running in Replit
1. Simply press the "Run" button in the Replit interface
2. The application will compile and launch automatically

### Running on a Local PC (Command Prompt)
1. Clone or download the project files
2. Open a terminal/command prompt
3. Navigate to the project directory
4. Create the bin directory (if it doesn't exist):
   ```
   mkdir bin
   ```
5. Compile the source files:
   ```
   javac -d bin src/edu/dvdlibrary/Main.java src/edu/dvdlibrary/librarycore/*/*.java src/edu/dvdlibrary/librarygui/*/*.java
   ```
6. Run the application:
   ```
   java -cp bin edu.dvdlibrary.Main
   ```

### Running on Windows with PowerShell
1. Clone or download the project files
2. Open PowerShell
3. Navigate to the project directory
4. Create the bin directory (if it doesn't exist):
   ```
   mkdir -Force bin
   ```
5. Compile all Java files (this handles the recursive compilation of all source files):
   ```
   javac -d bin $(Get-ChildItem -Path src -Filter "*.java" -Recurse | Select-Object -ExpandProperty FullName)
   ```
6. Run the application:
   ```
   java -cp bin edu.dvdlibrary.Main
   ```

### Troubleshooting
- If you encounter path-related issues, ensure you're using the correct path separator for your operating system (forward slashes `/` for Unix-based systems, backslashes `\` for Windows)
- If you get a "class not found" error, verify that your compilation step completed successfully and that the bin directory contains the compiled .class files
- For Windows users, if wildcard patterns don't work in Command Prompt, use the PowerShell method instead

## User Guide

### Films Tab
- **Add Film**: Enter a film title and click "Add Film"
- **Add DVD Copy**: Select a film, enter a DVD ID, and click "Add DVD Copy"
- **View Availability**: Select a film to see how many copies are available

### Members Tab
- **Add Member**: Enter a membership number and name, then click "Add Member"
- **View Borrowed Films**: Select a member to view their current loans

### Borrow Tab
- **Borrow a DVD**: Select a film, DVD, and member, then click "Borrow DVD"
- **View Active Loans**: The table shows all current loans in the system

### Return Tab
- **Return a DVD**: Enter a DVD ID or select from the table, then click "Return DVD"
- **View Active Loans**: The table shows all current loans in the system

## Potential Questions and Answers

### Q: What design pattern did you use for the overall architecture and why?
A: I used the Model-View-Controller (MVC) pattern because it provides clear separation of concerns between the data model (librarycore), user interface (librarygui), and the logic that connects them (controller). This allows changes to the UI to be made without affecting the core system, and vice versa.

### Q: How does your system handle concurrency?
A: The current implementation does not include specific concurrency controls since it's designed for a single-user environment. In a multi-user scenario, we would need to add synchronization mechanisms to the data store and service methods.

### Q: Could this system be extended to a web application?
A: Yes, the architecture facilitates this. The UI (View) layer could be replaced with a web interface while keeping the core business logic intact. The Controller would need to be adapted to handle HTTP requests instead of direct method calls.

### Q: How would you implement persistent storage?
A: We would create a new implementation of the DataStore interface (e.g., DatabaseDataStore) that connects to a database. The code is designed so that changing the data store implementation doesn't affect the rest of the system.

### Q: What constraints were implemented in the system?
A: Key constraints include:
1. Film titles must be unique
2. DVD IDs must be unique
3. Membership numbers must be unique
4. Members cannot borrow more than 6 DVDs at once
5. DVDs cannot be borrowed if already on loan
6. DVDs have a 3-day loan period

### Q: How did you test the application?
A: The application can be tested through unit tests for each component (services, models) and through integration tests that verify the interaction between components. The modular design makes it easier to test individual components in isolation.

### Q: What enhancements could be made to this system?
A: Potential enhancements include:
1. Implementing persistent storage (database)
2. Adding user authentication
3. Supporting different member types with different borrowing limits
4. Adding email notifications for due dates
5. Implementing a reservation system for DVDs
6. Adding a fine system for overdue returns

## Class Structure

### Model Classes
- **Film**: Represents a film with a unique title and multiple DVD copies
- **DVD**: Represents a physical DVD with a unique ID
- **Member**: Represents a library member with a unique membership number
- **Loan**: Represents a loan transaction between a member and a DVD

### Service Classes
- **LibraryService**: Interface defining the core operations
- **LibraryServiceImpl**: Implementation of the library service

### Data Access Classes
- **DataStore**: Interface for data persistence
- **InMemoryDataStore**: In-memory implementation of the data store

### GUI Classes
- **MainFrame**: Main application window
- **FilmPanel**: Panel for managing films
- **MemberPanel**: Panel for managing members
- **BorrowPanel**: Panel for borrowing DVDs
- **ReturnPanel**: Panel for returning DVDs

## Technical Implementation Notes

The system uses Java's built-in collections for data management:
- HashMap for storing films, DVDs, and members by their unique identifiers
- ArrayList for storing loans and maintaining relationships

The GUI is built with Java Swing for cross-platform compatibility and follows a tab-based interface pattern for easy navigation between functions.