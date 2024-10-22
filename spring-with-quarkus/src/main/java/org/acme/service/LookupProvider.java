package org.acme.service;

import org.acme.dto.UserModel;
import org.springframework.context.annotation.Bean;





public interface LookupProvider  extends AutoCloseable{


    UserProvider users();


    UserModel getUserById(UserModel model, String id);


    @Override
    void close();
}
