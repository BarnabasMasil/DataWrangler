import java.io.File;
import java.util.Scanner;

public class PasswordManager {

  private FileUtility utility;
  private Scanner scan;
  private HashTableMap<String, User> users;
  private boolean isRunning = true;

  public PasswordManager() {
    utility = new FileUtility(new File("Data.txt"));
    users = new HashTableMap<>();
    utility.loadData(users);
    scan = new Scanner(System.in);
  }

  public void addNewUser(String username, String password) {
    users.put(username, new User(username, password));

  }

  public void initialise() {

  }

  public void UserInterface() {
    String tempName, tempPass, tempUrl;
    User tempUser = null;
    Data tempData = null;
    boolean loginSuccess = false;

    // Login interface to login into the password manager
    while (isRunning) {
      System.out.print("Enter your username:");
      tempName = scan.nextLine();

      if (users.containskey(tempName)) {
        tempUser = users.get(tempName);

        boolean incorrectPass = true;

        while (incorrectPass) {
          System.out.print("Enter password:");
          tempPass = scan.nextLine();
          if (tempUser.getLoginPassword().equals(tempPass)) {
            loginSuccess = true;
            incorrectPass = false;
          } else {
            System.out.println("Incorrect password");
            System.out.print("Enter any key to retry or 'q' to exit:");
            String input = scan.nextLine();

            if (input.trim().equals("quit")) {
              isRunning = false;
              break;
            }
          }
        }

      } else {
        System.out.println("No such username exists");
        continue;
      }

      // User Interface where user is in the actual password manager
      if (loginSuccess == true) {

        boolean isRunning = true;

        System.out.println("Login successful");

        while (isRunning) {
          System.out.println("Enter a Url(website) to retrieve password and username");
          tempUrl = scan.nextLine().trim();

          if (tempUser.getCredentials().containskey(tempUrl)) {
            tempData = tempUser.getCredentials().get(tempUrl);
            tempName = tempData.getUsername();
            tempPass = tempData.getPassword();

            System.out.println("Obtaining username/password for " + tempUrl);
            System.out.println("Username: " + tempName);
            System.out.println("Password: " + tempPass + "\n");

            System.out.print("Press y(search new Url), q(exit app), c(change user)");
            String input = scan.nextLine().toLowerCase();

            if (input.equals("y")) {
              continue;
            } else if (input.contentEquals("q")) {
              this.isRunning = false;
              isRunning = false;
              break;
            } else if (input.equals("c")) {
              isRunning = false;
              break;
            }

          } else {
            System.out.println("Url does not exist");
          }
        }
      }
    }
    System.out.println("Exiting application");
  }


  public static void main(String[] args) {
    PasswordManager pm = new PasswordManager();
    pm.UserInterface();
  }

}
