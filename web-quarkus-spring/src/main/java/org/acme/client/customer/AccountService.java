package org.acme.client.customer;


import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;
import org.acme.client.customer.entity.Customer;
import org.acme.client.customer.model.CustomerCredentialModel;
import org.acme.client.customer.model.CustomerModel;
import org.acme.client.customer.model.PasswordCustomerCredentialModel;
import org.acme.client.customer.repository.CustomerDefaultRepository;
import org.acme.core.model.PasswordCredentialModel;
import org.acme.core.security.hash.Argon2Parameters;
import org.acme.core.security.hash.Argon2PasswordHashProviderFactory;
import org.acme.core.security.hash.CredentialInput;
import org.acme.core.utils.PasswordCredentialData;
import org.acme.core.utils.PasswordSecretData;
import  org.acme.core.security.hash.Argon2Parameters;


@ApplicationScoped
public class AccountService {
    private PasswordCredentialModel passwordCredentialModel;
    private final CustomerCredentialStore userCredentialStore;
    private final CustomerDefaultRepository customerRepository;
    private static Argon2Parameters argon2Parameters;

    public AccountService(CustomerCredentialStore userCredentialStore, CustomerDefaultRepository customerRepository,
        Map<String, String> userPasswords) {
        this.userCredentialStore = userCredentialStore;
        this.customerRepository = customerRepository;
        this.userPasswords = userPasswords;
    }

    private static PasswordCredentialModel credentialModel;


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


        PasswordCustomerCredentialModel pass = new PasswordCustomerCredentialModel("", "", "", false);

        CustomerCredentialModel cred = new CustomerCredentialModel();


        customerRepository.add(customer);
    }

    protected final Map<String, String> userPasswords;


    public boolean updateCredential(CustomerModel user, CredentialInput input) {

        if (!(input instanceof CustomerCredentialModel)) {
            return false;
        }
        if (input.getType().equals(PasswordCredentialModel.TYPE)) {
            userPasswords.put(translateUserName(user.getCustomerName()), input.getChallengeResponse());
            return true;

        } else {
            return false;


        }
    }


    public CustomerModel validate(CustomerModel local) {
        final boolean userExists = userPasswords.containsKey(translateUserName(local.getCustomerName()));

        return userExists ? local : null;
    }

    private static String translateUserName(String userName) {
        return userName == null ? null : userName.toLowerCase();
    }

}