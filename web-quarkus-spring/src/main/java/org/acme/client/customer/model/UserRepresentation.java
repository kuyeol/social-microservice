package org.acme.client.customer.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class UserRepresentation extends AbstractUserRepresentation implements UserModel {


    private String username;
    private String id;
    private long timestamp;
    protected List<CredentialRepresentation> credentials;
    protected List<String> requiredActions;
    protected Long createdTimestamp;
    private Map<String, Boolean> access;
    protected Boolean enabled;
    protected Map<String, List<String>> clientRoles;

    public UserRepresentation() {
    }

    public UserRepresentation(UserRepresentation rep) {
        this.id = rep.getId();
        this.username = rep.getUsername();
        this.firstName = rep.getFirstName();
        this.lastName = rep.getLastName();
        this.email = rep.getEmail();
        this.emailVerified = rep.isEmailVerified();
        this.attributes = rep.getAttributes();
        this.createdTimestamp = rep.getCreatedTimestamp();
        this.enabled = rep.isEnabled();
        this.credentials = rep.getCredentials();
        this.requiredActions = rep.getRequiredActions();
        this.clientRoles = rep.getClientRoles();
        this.access = rep.getAccess();
    }


    public Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public void setSingleAttribute(String name, String value) {

    }

    @Override
    public void setAttribute(String name, List<String> values) {

    }

    @Override
    public void removeAttribute(String name) {

    }

    @Override
    public String getFirstAttribute(String name) {
        return "";
    }

    @Override
    public Stream<String> getAttributeStream(String name) {
        return Stream.empty();
    }

    @Override
    public Stream<String> getRequiredActionsStream() {
        return Stream.empty();
    }

    @Override
    public void addRequiredAction(String action) {

    }

    @Override
    public void removeRequiredAction(String action) {

    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<CredentialRepresentation> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<CredentialRepresentation> credentials) {
        this.credentials = credentials;
    }

    public List<String> getRequiredActions() {
        return requiredActions;
    }

    public void setRequiredActions(List<String> requiredActions) {
        this.requiredActions = requiredActions;
    }


    public Map<String, List<String>> getClientRoles() {
        return clientRoles;
    }

    public void setClientRoles(Map<String, List<String>> clientRoles) {
        this.clientRoles = clientRoles;
    }

    public Map<String, Boolean> getAccess() {
        return access;
    }

    public void setAccess(Map<String, Boolean> access) {
        this.access = access;
    }


}
