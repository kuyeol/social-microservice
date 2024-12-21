package org.acme.client.customer;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.concurrent.Semaphore;
import org.acme.client.customer.entity.Customer;
import org.acme.client.customer.model.CustomerCredentialModel;
import org.acme.client.customer.model.CustomerModel;
import org.acme.client.customer.model.PasswordCustomerCredentialModel;
import org.acme.client.customer.repository.CredentialRepository;
import org.acme.client.customer.repository.CustomerRepository;
import org.acme.core.model.PasswordCredentialModel;
import org.acme.core.security.hash.Argon2PasswordHashProvider;
import org.acme.core.security.hash.CredentialInput;
import org.acme.core.utils.PasswordCredentialData;
import org.acme.core.utils.PasswordSecretData;

import static com.arjuna.ats.jdbc.TransactionalDriver.password;


@ApplicationScoped
public class AccountService {

//    @Inject
//  PasswordCredentialModel passwordCredentialModel;
//
//    @Inject
//CustomerCredentialStore userCredentialStore;
//
//    @Inject
//    CredentialRepository credentialRepository;


 private final    CustomerRepository customerRepository;

public AccountService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
}








    public void testMethod() {
        String alg = "";

        //setPasswordPolicy("hashAlgorithm(" + Argon2PasswordHashProviderFactory.ID + ")");
        //String username = "testArgon2";
        //createUser(username);
        //
        //PasswordCredentialModel credential = PasswordCredentialModel.createFromCredentialModel(fetchCredentials(username));
        //PasswordCredentialData data = credential.getPasswordCredentialData();
        //
        //Assert.assertEquals("argon2", data.getAlgorithm());
        //Assert.assertEquals(5, data.getHashIterations());
        //Assert.assertEquals("1.3", data.getAdditionalParameters().getFirst("version"));
        //Assert.assertEquals("id", data.getAdditionalParameters().getFirst("type"));
        //Assert.assertEquals("32", data.getAdditionalParameters().getFirst("hashLength"));
        //Assert.assertEquals("7168", data.getAdditionalParameters().getFirst("memory"));
        //Assert.assertEquals("1", data.getAdditionalParameters().getFirst("parallelism"));
        //
        //loginPage.open();
        //loginPage.login("testArgon2", "invalid");
        //loginPage.assertCurrent();
        //Assert.assertEquals("Invalid username or password.", loginPage.getInputError());
        //int hashIterations = 27500;
        //
        //
        //PasswordCredentialData credentialData = new PasswordCredentialData();
        //PasswordSecretData secretData;
        //
        //

        //  createFromValues(algorithm, salt, hashIterations, null, encodedPassword)
        Customer customer = new Customer();



        String version = "1.3";
        String type = "id";
        int hashLength = 32;
        int memory = 1024;
        int iterations = 5;
        int parallelism = 1;

        Semaphore cpuCoreSemaphore = new Semaphore(11);


        Argon2PasswordHashProvider provider = new Argon2PasswordHashProvider(
            version,
            type,
            hashLength,
            memory ,
            iterations,
            parallelism,
            cpuCoreSemaphore
        );


        String passw = "mySecurePassword123";
        PasswordCredentialModel encodedPassword = provider.encodedCredential(passw, 5);
        PasswordCredentialModel encodedPass = provider.encodedCredential(passw, 15);

        System.out.println("Encoded: " + encodedPassword);
String dd= String.valueOf(encodedPassword.getPasswordCredentialData().getAdditionalParameters().keySet());
String dd1= String.valueOf(encodedPassword.getPasswordCredentialData().getAdditionalParameters().entrySet());
String aa= String.valueOf(encodedPassword.getPasswordSecretData().getValue());
byte[] aa2= encodedPassword.getPasswordSecretData().getSalt();
        // Verify the password[hashLength=[32], memory=[131072], type=[id], version=[1.3], parallelism=[1]]
        System.out.println("keySet : "+dd);
        System.out.println("entrySet : "+dd1);
        System.out.println("SecretData_Value : "+aa);
        System.out.print("SecretData_salt : ");
        for(byte s:aa2){
            System.out.print(s);
        }

        System.out.println();

        boolean isValid = provider.verify(passw, encodedPassword);
        boolean isValid2 = provider.verify(passw, encodedPass);

        System.out.println("Password valid: " + isValid);
        System.out.println("Password valid: " + isValid2);


        customerRepository.add(customer);
    }


    public boolean updateCredential(CustomerModel user, CredentialInput input) {

        return false;
    }


    public CustomerModel validate(CustomerModel local) {


        return null;
    }

    private static String translateUserName(String userName) {
        return userName == null ? null : userName.toLowerCase();
    }

}