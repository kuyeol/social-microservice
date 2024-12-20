

package org.account.service.userprofile;

import org.account.service.UserModel;
import org.jboss.logging.Logger;



/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class UserProfileUtil {

    private static final Logger logger = Logger.getLogger(UserProfileUtil.class);

    public static final String USER_METADATA_GROUP = "customer-metadata";




    /**
     * Returns whether the attribute with the given {@code name} is a root attribute.
     *
     * @param name the attribute name
     * @return
     */
    public static boolean isRootAttribute(String name) {
        return UserModel.USERNAME.equals( name)
               || UserModel.EMAIL.equals(name)
               || UserModel.FIRST_NAME.equals(name)
               || UserModel.LAST_NAME.equals(name)
               || UserModel.LOCALE.equals(name);
    }



}
