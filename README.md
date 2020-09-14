# Bank Application using [JavaFX](https://github.com/jpildush/BankApplication/tree/master/JavaFXApp)
Application designed to replicate an internal banking system using employee defined interest rates. Bank employees are able to register new clients by creating their profile. Bank employees are also able to view a clients account through the Client portal. The Client portal communicates through the local network to the Server portal, which holds all data. A Bank employee will only use the Client portal, ensuring that they do not have direct access to the Server portal and any sensitive data.

## Executing Proogram
**In order to execute the application, a Java IDE is needed - such as IntelliJ IDEA or Eclipse**

**[Java 14](https://jdk.java.net/14/) was used - using 11 or higher should be fine but it is recomended to update to 14**

**[OpenJFX](https://wiki.openjdk.java.net/display/OpenJFX/Download) was used as JavaFX was not supported on newer versions of Java**

1. Load the application source by adding the folder ```app/``` and its content into the new project folder's source folder.
2. Set the Java and JavaFX libraries
  * Go into project settings/project structure and find the libraries sub-category.
  * Add Java by adding the ```/bin``` directory that resides in the Java JDK folder.
  * Add JavaFX by adding the ```/lib``` directory that resides in the JavaFX folder.
  * Finally, edit the run configuration of RemoteBankClient, file located [here](https://github.com/jpildush/BankApplication/tree/master/JavaFXApp/app/client), by adding the following to VM arguments (be sure to replace %Path-to-JavaFX% with the path to the lib of JavaFX):
  ```--module-path %Path-to-JavaFX% --add-modules=javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.web```
3. Run the RemoteBankServer first, followed by the RemoteBankClient once the server has initialized.
4. Enjoy the application! Make your first client credentials and make your client a few accounts! 

# Bank Application using [Terminal](https://github.com/jpildush/BankApplication/tree/master/JavaApp)
Application designed to replicate an internal banking system using user defined interest rates. A user would beable to register new accounts, view, and adjust the accounts themselves.

## Executing Proogram
**In order to execute the application, terminal can be used or a Java IDE is needed - such as IntelliJ IDEA or Eclipse**

**[Java 14](https://jdk.java.net/14/) was used - using 11 or higher should be fine but it is recomended to update to 14**

### Executing in IDE
1. Load the application source by adding the folder ```app/```, located [here](https://github.com/jpildush/BankApplication/tree/master/JavaApp/app) and its contents into the new project folder's source folder.
2. Set the Java libraries
  * Go into project settings/project structure and find the libraries sub-category.
  * Add Java by adding the ```/bin``` directory that resides in the Java JDK folder.
3. Run the FinancialApp!

### Executing in Terminal and running Application with JAR file
**Make sure to point to the correct Java JDK in the PATH of your system**
1. Run the application by entering this command in the same folder that the JAR file is located.
```java -cp BankApp.jar app.main.FinancialApp```

**Enjoy the application!**
