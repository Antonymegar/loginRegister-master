package com.example.loginregister.Config;

import org.springframework.web.bind.annotation.GetMapping;

class MvcConfigTest {
   @GetMapping("/login")
public String viewLoginPage() {
return "login";
   }


}