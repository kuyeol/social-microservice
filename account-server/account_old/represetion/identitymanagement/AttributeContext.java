
package org.account.represetion.identitymanagement;

import org.account.service.Session;
import org.account.service.UserModel;
import org.account.service.userprofile.UserProfileContext;

import java.util.List;
import java.util.Map;




/**
 * @author <a href="mailto:psilva@redhat.com">Pedro Igor</a>
 */
public final class AttributeContext {

    private final Session session;
    private final Map.Entry<String, List<String>> attribute;
    private final UserModel user;
    private final AttributeMetadata               metadata;
    private final Attributes                      attributes;
    private UserProfileContext context;

    public AttributeContext(UserProfileContext context, Session session, Map.Entry<String, List<String>> attribute,
                            UserModel user, AttributeMetadata metadata, Attributes attributes) {
        this.context = context;
        this.session = session;
        this.attribute = attribute;
        this.user = user;
        this.metadata = metadata;
        this.attributes = attributes;
    }

    public Session getSession() {
        return session;
    }

    public Map.Entry<String, List<String>> getAttribute() {
        return attribute;
    }

    public UserModel getUser() {
        return user;
    }

    public UserProfileContext getContext() {
        return context;
    }

    public AttributeMetadata getMetadata() {
        return metadata;
    }

    public Attributes getAttributes() {
        return attributes;
    }
}
