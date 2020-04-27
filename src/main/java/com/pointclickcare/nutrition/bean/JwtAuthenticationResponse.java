package com.pointclickcare.nutrition.bean;

public class JwtAuthenticationResponse {
  
      private String facilityName;
	    private String accessToken;
	    private String tokenType = "Bearer";

	    public JwtAuthenticationResponse(String accessToken) {
	        this.accessToken = accessToken;
	    }
	    
	    public JwtAuthenticationResponse(String accessToken, String facilityName) {
	      this.facilityName = facilityName;
        this.accessToken = accessToken;
      }

	    public String getAccessToken() {
	        return accessToken;
	    }

	    public void setAccessToken(String accessToken) {
	        this.accessToken = accessToken;
	    }

	    public String getTokenType() {
	        return tokenType;
	    }

	    public void setTokenType(String tokenType) {
	        this.tokenType = tokenType;
	    }

      public String getFacilityName()
      {
        return facilityName;
      }

      public void setFacilityName(String facilityName)
      {
        this.facilityName = facilityName;
      }
}
