package com.themaestrocode.aaualms.service;

import com.themaestrocode.aaualms.entity.Password;
import com.themaestrocode.aaualms.repository.PasswordRepository;

public class PasswordService {

    PasswordRepository passwordRepository = new PasswordRepository();

    public boolean savePassword(String hashedPassword, String salt) {
        return passwordRepository.savePassword(hashedPassword, salt);
    }

    public Password getCurrentPassword() {
        return passwordRepository.getCurrentPassword();
    }

    public Password findPassword(String passwordToFind) {
        return passwordRepository.findPassword(passwordToFind);
    }
}
