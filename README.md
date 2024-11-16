## IMPORTANT INSTRUCTIONS
The program can only be run using terminal commands.

For regular use: `mvn clean javafx:run`

For development mode (hot reloading): 
- Windows: `dev.bat`
- macOS: `./dev.sh`

**Note:** You must set up the development environment before using development mode. Instructions below.

## Setup Instructions for Maven and JavaFX

**You must complete this setup regardless of whether you want to use development mode or not.**

### Windows

Install JavaFX: https://www.tutorialspoint.com/javafx/javafx_environment.htm

Download:
https://gluonhq.com/products/javafx/
(Windows 23.0.1 x64 SDK)

1. Create a folder in your C drive and name it "JavaFX"
    - C:\Program Files\JavaFX
2. Extract the downloaded folder into JavaFX
   - C:\Program Files\JavaFX\javafx-sdk-23.0.1
3. Search "Edit the system environment variables" on the search bar and click on it
4. Click on "Environment Variables..." located in the bottom left
5. Under "System variables" (Lower Rectangle), click "New..."
6. Enter:
   - Variable name: PATH_TO_FX
   - Variable value: C:\Program Files\JavaFX\javafx-sdk-23.0.1\lib
7. Click "OK" on every window to exit

Install Maven by following this guide: https://phoenixnap.com/kb/install-maven-windows#step-4-verify-maven-installation

Download:
https://maven.apache.org/download.cgi
(Binary zip archive apache-maven-3.9.9-bin.zip)

1. Create a folder in your C drive and name it "Maven"
   - C:\Program Files\Maven
2. Extract the downloaded folder into Maven
   - C:\Program Files\Maven\apache-maven-3.9.9
3. Search "Edit the system environment variables" on the search bar and click on it
4. Click on "Environment Variables..." located in the bottom left
5. Under "System variables" (Lower Rectangle), Scroll though and find "Path"
6. After selecting "Path" click "Edit..."
7. Click "New" on the top right
8. Enter:
   - C:\Program Files\Maven\apache-maven-3.9.9\bin
9. Click "OK" on every window to exit

### macOS (in terminal)

1. Install Homebrew: 
   ```bash
   sudo /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```
2. Install Java 23: 
   ```bash
   brew install openjdk@23
   ```
3. Add Java to PATH: 
   ```bash
   export PATH="/opt/homebrew/opt/openjdk@23/bin:$PATH"
   ```
4. Install Maven: 
   ```bash
   brew install maven
   ```
And that's it! You can now run the program using `mvn clean javafx:run`

## Development Mode (Hot Reloading)
1. Install Node and pnpm:
   ```bash
   # macOS
   brew install node
   brew install pnpm
   
   # Windows
   Download Node.js from https://nodejs.org/
   npm install -g pnpm
   ```

2. Install development dependencies:
   ```bash
   pnpm install
   ```

3. Start development mode:
   ```bash
   # macOS
   ./dev.sh
   
   # Windows
   dev.bat
   ```