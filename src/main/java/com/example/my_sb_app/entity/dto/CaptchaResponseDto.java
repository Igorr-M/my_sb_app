package com.example.my_sb_app.entity.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponseDto {
   private boolean success;
   @JsonAlias({"error-codes"})
   Set<String> errorCodes;

   public boolean isSuccess() {
        return this.success;
   }

   public void setSuccess(boolean success) {
        this.success = success;
   }

   public Set<String> getErrorCodes() {
        return this.errorCodes;
   }

   public void setErrorCodes(Set<String> errorCodes) {
        this.errorCodes = errorCodes;
   }
    
}
