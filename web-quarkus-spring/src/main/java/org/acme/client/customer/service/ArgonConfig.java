package org.acme.client.customer.service;

import java.util.concurrent.Semaphore;
import org.acme.core.security.hash.Argon2PasswordHashProvider;

public class ArgonConfig {
    static String version = "1.3";
    static String type = "id";
    static int hashLength = 32;
    static int memory = 1024;
    static int iterations = 5;
    static int parallelism = 1;
    static Semaphore cpuCoreSemaphore = new Semaphore(1);
    private final Argon2PasswordHashProvider provider;

    public ArgonConfig() {

        this.provider = new Argon2PasswordHashProvider(version, type, hashLength, memory, iterations, parallelism,
            cpuCoreSemaphore);


    }


public Argon2PasswordHashProvider getProvider() {
        return provider;
}





}
