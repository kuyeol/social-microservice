package org.account.credential;



import org.account.cofig.Config;
import org.account.service.EnvironmentDependentProviderFactory;
import org.account.service.ProviderConfigProperty;
import org.account.service.ProviderConfigurationBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Argon2PasswordHashProviderFactory implements PasswordHashProviderFactory, EnvironmentDependentProviderFactory {

    public static final String ID = "argon2";
    public static final String TYPE_KEY = "type";
    public static final String VERSION_KEY = "version";
    public static final String HASH_LENGTH_KEY = "hashLength";
    public static final String MEMORY_KEY = "memory";
    public static final String ITERATIONS_KEY = "iterations";
    public static final String PARALLELISM_KEY = "parallelism";
    public static final String CPU_CORES_KEY = "cpuCores";

    /**
     * The Argon2 password hashing is CPU bound, so it doesn't make sense to hash more values concurrently than there are cores on the machine.
     * When we run more, this only leads to an increased memory usage and to throttling of the process in containerized environments
     * when a CPU limit is imposed. The throttling would have a negative impact on other concurrent non-hashing activities of Keycloak.
     */
    private Semaphore cpuCoreSempahore;

    private String version;
    private String type;
    private int hashLength;
    private int memory;
    private int iterations;
    private int parallelism;

    public PasswordHashProvider create() {
        return new Argon2PasswordHashProvider(version, type, hashLength, memory, iterations, parallelism, cpuCoreSempahore);
    }

    @Override
    public void init(Config.Scope config) {
        version = config.get(VERSION_KEY, Argon2Parameters.DEFAULT_VERSION);
        type = config.get(VERSION_KEY, Argon2Parameters.DEFAULT_TYPE);
        hashLength = config.getInt(HASH_LENGTH_KEY, Argon2Parameters.DEFAULT_HASH_LENGTH);
        memory = config.getInt(MEMORY_KEY, Argon2Parameters.DEFAULT_MEMORY);
        iterations = config.getInt(ITERATIONS_KEY, Argon2Parameters.DEFAULT_ITERATIONS);
        parallelism = config.getInt(PARALLELISM_KEY, Argon2Parameters.DEFAULT_PARALLELISM);
        cpuCoreSempahore = new Semaphore(config.getInt(CPU_CORES_KEY, Runtime.getRuntime().availableProcessors()));
    }


    public void postInit() {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public List<ProviderConfigProperty> getConfigMetadata() {
        ProviderConfigurationBuilder builder = ProviderConfigurationBuilder.create();

        builder.property()
                .name(VERSION_KEY)
                .type("string")
                .helpText("Version")
                .options(new LinkedList<>(Argon2Parameters.listVersions()))
                .defaultValue(Argon2Parameters.DEFAULT_VERSION)
                .add();

        builder.property()
                .name(TYPE_KEY)
                .type("string")
                .helpText("Type")
                .options(new LinkedList<>(Argon2Parameters.listTypes()))
                .defaultValue(Argon2Parameters.DEFAULT_TYPE)
                .add();

        builder.property()
                .name(TYPE_KEY)
                .type("int")
                .helpText("Hash length")
                .defaultValue(Argon2Parameters.DEFAULT_HASH_LENGTH)
                .add();

        builder.property()
                .name(MEMORY_KEY)
                .type("int")
                .helpText("Memory size (KB)")
                .defaultValue(Argon2Parameters.DEFAULT_MEMORY)
                .add();

        builder.property()
                .name(ITERATIONS_KEY)
                .type("int")
                .helpText("Iterations")
                .defaultValue(Argon2Parameters.DEFAULT_ITERATIONS)
                .add();

        builder.property()
                .name(PARALLELISM_KEY)
                .type("int")
                .helpText("Parallelism")
                .defaultValue(Argon2Parameters.DEFAULT_PARALLELISM)
                .add();

        builder.property()
                .name(CPU_CORES_KEY)
                .type("int")
                .helpText("Maximum parallel CPU cores to use for hashing")
                .add();

        return builder.build();
    }



    @Override
    public int order() {
        return 300;
    }


  @Override
  public boolean isSupported(Config.Scope config) {
    boolean supported = false;
    return supported;
  }
}
