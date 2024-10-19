package model;

public interface Session extends AutoCloseable {


    <T extends Provider> T getProvider(Class<T> clazz);



}
