package com.dewansh.quizapp.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);   // Logger instance
    

    @Autowired
    JwtUtil jwtUtil;

    public ResponseEntity<?> handleLogin(String username, String password) {
        log.info("User login attempted: " + username);
        try{
            //hardcode logic for now
            if (username.equals("dewansh") && password.equals("huihuihui")){
                
                //generate a jwt token and send it back as response
                String token = jwtUtil.generateToken(username);
                return new ResponseEntity<>(token, HttpStatus.OK);
                
            }
        }
        catch (Exception e){
            e.printStackTrace();
            log.error("User login failed: " + username);
            return ResponseEntity.status(500).body("Internal Server Error " + e.getMessage());
        }
        // return ResponseEntity.status(401).body("Invalid Credentials");
        
    
        log.debug("User login debug info: " + username);
        return new ResponseEntity<>("invalid cred", HttpStatus.UNAUTHORIZED);
    }

}
