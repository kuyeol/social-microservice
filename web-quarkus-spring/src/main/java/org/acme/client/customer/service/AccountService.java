package org.acme.client.customer.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.client.customer.entity.Credential;
import org.acme.client.customer.entity.Customer;
import org.acme.client.customer.model.CredentialRepresentation;
import org.acme.client.customer.model.CustomerModel;
import org.acme.client.customer.model.UserModel;
import org.acme.client.customer.model.UserRepresentation;
import org.acme.client.customer.repository.CredentialRepository;
import org.acme.client.customer.repository.CustomerRepository;
import org.acme.core.model.CredentialModel;
import org.acme.core.model.PasswordCredentialModel;
import org.acme.core.security.hash.Argon2PasswordHashProvider;
import org.acme.core.security.hash.CredentialInput;
import org.acme.core.utils.ModelUtils;

@ApplicationScoped
public class AccountService {


    @Inject
    CredentialRepository credentialRepository;
    @Inject
    CustomerRepository customerRepository;


    public AccountService() {

    }


    @Transactional
    public void add(Customer customer) {

        String passw = "mySecurePassword123";


        ArgonConfig argonConfig = new ArgonConfig();
        Credential credential = new Credential();

        Argon2PasswordHashProvider AP = argonConfig.getProvider();


        PasswordCredentialModel encodedPassword = AP.encodedCredential(passw, 5);
        CredentialRepresentation credR = new CredentialRepresentation();

        credR.setSecretData(encodedPassword.getSecretData());
        credR.setCredentialData(encodedPassword.getCredentialData());


        credential.setUser(customer);
        credential.setSecretData(encodedPassword.getSecretData());

        credential.setCredentialData(credR.getCredentialData());

        credential.setCredentialData(encodedPassword.getCredentialData());

        customer.setCredentials(credential.getUser().getCredentials());

        System.out.println(credential.getUser().getCredentials());

        CredentialModel model = new CredentialModel();
        model.setCredentialData(credR.getCredentialData());
        model.setSecretData(credR.getSecretData());
        createCredential(model, customer.getId());

    }


    public UserRepresentation findId(String name) {


        UserModel model = customerRepository.findByName(name);

        System.out.println(model);

        UserRepresentation rep = new UserRepresentation();
        rep.setId(model.getId());
        rep.setUsername(model.getUsername());

        return rep;
    }


    void createCredential(CredentialModel cred, String id) {

        Credential entity = new Credential();

        String credid = ModelUtils.generateId();
        entity.setId(credid);
        entity.setCreatedDate(cred.getCreatedDate());
        entity.setSecretData(cred.getSecretData());
        entity.setCredentialData(cred.getCredentialData());

        Customer customerRef = new Customer();
        customerRef.setId(id);

        entity.setUser(customerRef);
        credentialRepository.add(entity);

    }


    public UserRepresentation findUser(UserModel model) {


        UserRepresentation rep = new UserRepresentation();
        UserModel find = customerRepository.findByName(model.getUsername());
        rep.setUsername(find.getUsername());

        return rep;


    }


    public void testMethod() {
        String alg = "";

        // setPasswordPolicy("hashAlgorithm(" + Argon2PasswordHashProviderFactory.ID +
        // ")");
        // String username = "testArgon2";
        // createUser(username);
        //
        // PasswordCredentialModel credential =
        // PasswordCredentialModel.createFromCredentialModel(fetchCredentials(username));
        // PasswordCredentialData data = credential.getPasswordCredentialData();
        //
        // Assert.assertEquals("argon2", data.getAlgorithm());
        // Assert.assertEquals(5, data.getHashIterations());
        // Assert.assertEquals("1.3",
        // data.getAdditionalParameters().getFirst("version"));
        // Assert.assertEquals("id", data.getAdditionalParameters().getFirst("type"));
        // Assert.assertEquals("32",
        // data.getAdditionalParameters().getFirst("hashLength"));
        // Assert.assertEquals("7168",
        // data.getAdditionalParameters().getFirst("memory"));
        // Assert.assertEquals("1",
        // data.getAdditionalParameters().getFirst("parallelism"));
        //
        // loginPage.open();
        // loginPage.login("testArgon2", "invalid");
        // loginPage.assertCurrent();
        // Assert.assertEquals("Invalid username or password.",
        // loginPage.getInputError());
        // int hashIterations = 27500;
        //
        //
        // PasswordCredentialData credentialData = new PasswordCredentialData();
        // PasswordSecretData secretData;
        //
        //

        // createFromValues(algorithm, salt, hashIterations, null, encodedPassword)
        Customer customer = new Customer();


        //String passw = "mySecurePassword123";
        //PasswordCredentialModel encodedPassword = provider.encodedCredential(passw, 5);
        //PasswordCredentialModel encodedPass = provider.encodedCredential(passw, 15);
        //
        //System.out.println("Encoded: " + encodedPassword);
        //String dd = String.valueOf(encodedPassword.getPasswordCredentialData().getAdditionalParameters().keySet());
        //String dd1 = String.valueOf(encodedPassword.getPasswordCredentialData().getAdditionalParameters().entrySet());
        //String aa = String.valueOf(encodedPassword.getPasswordSecretData().getValue());
        //byte[] aa2 = encodedPassword.getPasswordSecretData().getSalt();
        //// Verify the password[hashLength=[32], memory=[131072], type=[id],
        //// version=[1.3], parallelism=[1]]
        //System.out.println("keySet : " + dd);
        //System.out.println("entrySet : " + dd1);
        //System.out.println("SecretData_Value : " + aa);
        //System.out.print("SecretData_salt : ");
        //for (byte s : aa2) {
        //    System.out.print(s);
        //}
        //
        //System.out.println();
        //
        //boolean isValid = provider.verify(passw, encodedPassword);
        //boolean isValid2 = provider.verify(passw, encodedPass);
        //
        //System.out.println("Password valid: " + isValid);
        //System.out.println("Password valid: " + isValid2);
        //

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