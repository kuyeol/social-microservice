package org.account.represetion.identitymanagement;


import java.util.List;
import java.util.Map;
import java.util.Set;


public class UserRepresentation extends AbstractUserRepresentation
{



  protected Long                             createdTimestamp;

  protected Boolean                          enabled;

  protected String                           serviceAccountClientId; // For rep, it points to clientId (not DB ID)

  protected List< CredentialRepresentation > credentials;

  protected Set< String >                    disableableCredentialTypes;

  protected List< String >                   requiredActions;

  protected List< String >                   realmRoles;

  protected Map< String, List< String > >    clientRoles;

  protected Integer                          notBefore;

  protected List< String >                   groups;

  private   Map< String, Boolean >           access;


  public UserRepresentation()
  {

  }


  public UserRepresentation( UserRepresentation rep )
  {
    // AbstractUserRepresentation
    this.id            = rep.getId();
    this.username      = rep.getUsername();
    this.firstName     = rep.getFirstName();
    this.lastName      = rep.getLastName();
    this.email         = rep.getEmail();
    this.emailVerified = rep.isEmailVerified();
    this.attributes    = rep.getAttributes();
    // this.setUserProfileMetadata(rep.getUserProfileMetadata());
    this.createdTimestamp       = rep.getCreatedTimestamp();
    this.enabled                = rep.isEnabled();
    this.serviceAccountClientId = rep.getServiceAccountClientId();
    this.credentials            = rep.getCredentials();
  }

  public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }
  public Long getCreatedTimestamp() {
    return createdTimestamp;
  }

  public void setCreatedTimestamp(Long createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

    public List< CredentialRepresentation > getCredentials ( ) {
    return credentials;
  }

    public void setCredentials ( List < CredentialRepresentation > credentials ) {
    this.credentials = credentials;
  }

    public List< String > getRequiredActions ( ) {
    return requiredActions;
  }

    public void setRequiredActions ( List < String > requiredActions ) {
    this.requiredActions = requiredActions;
  }

    public List< String > getRealmRoles ( ) {
    return realmRoles;
  }

    public void setRealmRoles ( List < String > realmRoles ) {
    this.realmRoles = realmRoles;
  }

    public Map< String, List< String > > getClientRoles ( ) {
    return clientRoles;
  }

    public void setClientRoles ( Map < String, List < String >> clientRoles){
    this.clientRoles = clientRoles;
  }

    public Integer getNotBefore ( ) {
    return notBefore;
  }

    public void setNotBefore ( Integer notBefore){
    this.notBefore = notBefore;
  }


    public String getServiceAccountClientId ( ) {
    return serviceAccountClientId;
  }

    public void setServiceAccountClientId ( String serviceAccountClientId){
    this.serviceAccountClientId = serviceAccountClientId;
  }

    public List< String > getGroups ( ) {
    return groups;
  }

    public void setGroups ( List < String > groups ) {
    this.groups = groups;
  }



    public Set< String > getDisableableCredentialTypes ( ) {
    return disableableCredentialTypes;
  }

    public void setDisableableCredentialTypes ( Set < String > disableableCredentialTypes ) {
    this.disableableCredentialTypes = disableableCredentialTypes;
  }

    public Map< String, Boolean > getAccess ( ) {
    return access;
  }

    public void setAccess ( Map < String, Boolean > access){
    this.access = access;
  }

  }
