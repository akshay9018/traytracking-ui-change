package com.pointclickcare.nutrition.bean;

public class UIUser
{
  private String userName;
  private String password;
  private String passwordHash;
  public String getUserName()
  {
    return userName;
  }
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  public String getPassword()
  {
    return password;
  }
  public void setPassword(String password)
  {
    this.password = password;
  }
  public String getPasswordHash()
  {
    return passwordHash;
  }
  public void setPasswordHash(String passwordHash)
  {
    this.passwordHash = passwordHash;
  }
  
}
