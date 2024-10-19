package model;

public interface ModelProvider extends Provider {

    Model addUser( String id, String username, boolean addDefaultRoles, boolean addDefaultRequiredActions);
}
