public class User{
  
  private String loginUsername;
  private String loginPassword;
  
  private HashTableMap<String, Data<String, String>> credentials;
  
  public User(String username, String password){
    loginUsername = username;
    loginPassword = password;
    credentials = new HashTableMap<>();
  }
  
  public boolean addCredential(String url, String username, String password){
    return credentials.put(url, new Data<>(url,username,password));
  }
}
