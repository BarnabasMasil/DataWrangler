public class User{
  
  private String loginUsername;
  private String loginPassword;
  
  private HashTableMap<String, Data> credentials;
  
  public User(String username, String password){
    loginUsername = username;
    loginPassword = password;
    credentials = new HashTableMap<>();
  }
  
  public boolean addCredential(String url, String username, String password){
    return credentials.put(url, new Data(url,username,password));
  }
  
  public void printCredentials(String url) {
    System.out.println(credentials.get(url).getUrl());
    System.out.println(credentials.get(url).getUsername());
    System.out.println(credentials.get(url).getPassword());
    
  }
  
  public String getLoginUsername() {
    return loginUsername;
  }
  
  public String getLoginPassword() {
    return loginPassword;
  }
  
  public HashTableMap<String, Data> getCredentials() {
    return credentials;
  }
  
}
