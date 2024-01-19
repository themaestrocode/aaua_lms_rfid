package com.themaestrocode.aaualms.utility;

import com.themaestrocode.aaualms.entity.Password;
import com.themaestrocode.aaualms.service.PasswordService;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {


    public String generateSalt() {
        return BCrypt.gensalt(); // generate salt for the password
    }

    public String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt); // Hash the password with the salt
    }

    public boolean verifyPassword(String password) {
        PasswordService passwordService = new PasswordService();

        //fetch the original password from the DB
        Password currentPassword = passwordService.getCurrentPassword();

        //check the entered password against the hash of the original password in the DB
        boolean passwordMatch = BCrypt.checkpw(password, currentPassword.getHash());

        return passwordMatch;
    }
}
