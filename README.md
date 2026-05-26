# Java Swing Apps

Collection of Java Swing desktop applications built for learning GUI development, custom visual components, and database integration.

---

## Projects

### [login-system](login-system/)

Login & Registration System with SQLite database.

**Features:**
- User registration with name, email and password validation
- User authentication against SQLite database
- Dark theme with black background and white text
- Ghost text placeholders in input fields
- Circular avatar photo (with drawn fallback)
- CardLayout navigation between login and register screens

**Tech:** Java Swing, SQLite (embedded), JDBC, SLF4J

**Run:**
```bash
cd login-system
chmod +x run.sh
./run.sh
```

> See [login-system/README.md](login-system/README.md) for full details.

---

### [calculator](calculator/)

Pink pastel themed calculator with custom display and styled buttons.

**Features:**
- Basic operations: add, subtract, multiply, divide
- Helper functions: percentage (`%`), sign toggle (`±`), backspace (`←`), clear (`C`)
- Large custom display with right alignment
- Color-coded buttons by type

**Tech:** Java Swing, GridLayout

**Run:**
```bash
cd calculator
javac Main.java
java Main
```

---

### [dialogs](dialogs/)

Quick dialog demonstrations with dark theme.

**Features:**
- Error message dialog
- Warning message dialog
- Yes/No confirmation dialog
- Dark themed buttons with hover effect

**Tech:** Java Swing, JOptionPane, GridBagLayout

**Run:**
```bash
cd dialogs
javac DialogApp.java
java DialogApp
```

---

## Repository Structure

```
java-swing-apps/
├── assets/                 # Shared visual resources (icons, images)
├── calculator/
│   ├── Main.java           # Pink calculator
│   └── README.md           # Calculator docs
├── dialogs/
│   └── DialogApp.java      # JOptionPane examples
├── login-system/
│   ├── MainApp.java        # Main JFrame
│   ├── LoginPanel.java     # Login screen
│   ├── RegisterPanel.java  # Registration screen
│   ├── DatabaseConnection.java
│   ├── DatabaseSetup.java
│   ├── UserDAO.java
│   ├── User.java
│   ├── UIUtils.java
│   ├── ValidationUtils.java
│   ├── GhostText.java
│   ├── run.sh              # Run script
│   ├── sqlite-jdbc.jar     # SQLite JDBC driver
│   ├── slf4j-api.jar       # Logging API
│   ├── slf4j-simple.jar    # Logging implementation
│   └── README.md           # Full documentation
└── README.md               # This file
```

---

## General Requirements

- Java JDK 8 or higher (JDK 17+ recommended)
- No database installation needed for `login-system` — SQLite is embedded

---

## Technologies Used

- Java Swing (GUI)
- CardLayout (screen navigation)
- SQLite + JDBC (persistence)
- SLF4J (logging)

---

## License

Free for study and modification.
