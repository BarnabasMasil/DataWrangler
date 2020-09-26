import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtility {

  private File file;
  private Scanner scan = null;
  private boolean isEmpty = true;

  public FileUtility(File file) {
    this.file = file;
    Scanner temp;
    try {
      temp = new Scanner(file);
      isEmpty = !temp.hasNext();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  public void writeFile(String input) {

    if (isEmpty) {
      try (FileWriter fw = new FileWriter(file.getPath())) {
        fw.write(input);
      } catch (IOException e) {
        e.printStackTrace();
      }
      isEmpty = false;
    } else {
      try (FileWriter fw = new FileWriter(file.getPath(), true)) {
        fw.write("\n" + input);
      } catch (IOException e) {
        e.printStackTrace();
      }
      isEmpty = false;
    }
  }

  //THis is a test method
  public void printData() {
    /*
     * try { scan = new Scanner(file);
     * 
     * if (scan.hasNext()) { while (scan.hasNext()) { System.out.println(scan.nextLine()); } } else
     * { System.out.println("Empty"); } } catch (FileNotFoundException e) { e.printStackTrace(); }
     * finally { if (scan != null) scan.close(); }
     */
    
    String loginUsername = null;
    String loginPassword = null;
    String[] urls = null;
    String[] usernames = null;
    String[] passwords = null;

    try {
      scan = new Scanner(file);

      while (scan.hasNext()) {
        String line = scan.nextLine();
        if (line.equals("") || line.equals("\n")) {
          System.out.println("");
          continue;
        }
        
        if(line.contains("LoginUser")) {
          loginUsername = line.substring(line.indexOf(":")+1);
        }
        
        if(line.contains("LoginPassword")) {
          loginPassword = line.substring(line.indexOf(":")+1);
        }

        if (line.contains("Urls")) {
          urls = line.substring(line.indexOf(":")+1).split("\\s");
        }
        
        if(line.contains("Usernames")) {
          usernames = line.substring(line.indexOf(":")+1).split("\\s");
        }
        
        if(line.contains("Passwords")) {
          passwords = line.substring(line.indexOf(":")+1).split("\\s");
        }

        Scanner tempScan = new Scanner(line);
        System.out.println(tempScan.nextLine());

      }


    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (scan != null)
        scan.close();
    }
    System.out.println(loginUsername);
    System.out.println(loginPassword);
    for(int i = 0; i < urls.length; i++) {
      System.out.print(urls[i] + "\n");
      System.out.print(usernames[i] + "\n");
      System.out.print(passwords[i] + "\n");
    }
  }

  public void loadData(HashTableMap<String, User> users) {
    String loginUsername = null;
    String loginPassword = null;
    String[] urls = null;
    String[] usernames = null;
    String[] passwords = null;

    try {
      scan = new Scanner(file);

      while (scan.hasNext()) {
        String line = scan.nextLine();
        String info;
        if (line.equals("") || line.equals("\n")) {
          continue;
        }
        
        info = line;
        line = line.substring(0, line.indexOf(":"));
        
        if(line.equals("LoginUser")) {
          loginUsername = info.substring(info.indexOf(":")+1).trim();
        }
        
        if(line.equals("LoginPassword")) {
          loginPassword = info.substring(info.indexOf(":")+1).trim();
        }

        if (line.equals("Urls")) {
          urls = info.substring(info.indexOf(":")+1).trim().split("\\s");
        }
        
        if(line.equals("Usernames")) {
          usernames = info.substring(info.indexOf(":")+1).trim().split("\\s");
        }
        
        if(line.equals("Passwords")) {
          
          info = info.substring(info.indexOf(":")+1).trim();
          //System.out.println(info);
          passwords = new String[urls.length];
            
          String temp = "";
          boolean onPassword = false;
          int counter = 0;
          
          for(int i = 0; i < info.length();i++) {
            
            if(info.charAt(i) == '\"') {
              if(onPassword == false) {
                onPassword = true;
              }else {
                onPassword = false;
                passwords[counter] = temp.substring(1);
                counter++;
                temp = "";
              }  
            }
            
            if(onPassword == true) {
              temp += info.charAt(i);
            }
          }
        }
        
        //Add data to HashTableMap
        if(line.contains("stop")) {
          //System.out.println(loginUsername);
          users.put(loginUsername, new User(loginUsername, loginPassword));
          
          for(int i = 0; i < urls.length; i++) {
            users.get(loginUsername).addCredential(urls[i], usernames[i], passwords[i]);
          }
        }
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (scan != null)
        scan.close();
    }
  }
  
  public void saveData(HashTableMap<String, User> users) {
    
  }


}
