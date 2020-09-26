import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class is the main class where Userinterface occurs and where altertion or changes are stored
 * or loaded
 * 
 * @author barna
 *
 */
public class PasswordManager {

  private FileUtility utility;
  private Scanner scan;
  private HashTableMap<String, User> users;
  private LinkedList<String> listOfUsernames;
  private boolean isRunning = true;

  public PasswordManager() {
    utility = new FileUtility(new File("Data.txt"));
    users = new HashTableMap<>();
    listOfUsernames = new LinkedList<>();
    utility.loadData(users, listOfUsernames);
    scan = new Scanner(System.in);
  }

  /**
   * This method adds new user with a new username and password String into the users hashTable.
   * duplicates of usernames are not allowed
   * 
   * @param username The String that contains a chosen username
   * @param password The String that contains a chosen password
   */
  public void addNewUser(String username, String password) {
    for (int i = 0; i < listOfUsernames.size(); i++) {
      if (username.equals(listOfUsernames.get(i))) {
        System.out.println("Username already taken");
        return;
      }
    }

    users.put(username, new User(username, password));
    listOfUsernames.add(username);
  }

  /**
   * This method adds a chosen Url, username and a password and associate it with a User object
   * 
   * @param loginUsername The String of the initial login username to associate the url, username,
   *                      and password to
   * @param url           The String that contains a chosen url
   * @param username      The String that contains a chosen username
   * @param password      The String that contains a chosen password
   */
  public void addNewCredential(String loginUsername, String url, String username, String password) {
    users.get(loginUsername).addCredential(url, username, password);
    users.get(loginUsername).getListOfUrls().add(url);
  }

  /**
   * This method is the userInterface where the user can interact with the program
   */
  public void userInterface() {
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
    pm.userInterface();
  }

}
