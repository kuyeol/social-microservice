package org.acme.ext.terran.test;

import java.net.HttpCookie;
import org.acme.core.interfaces.Command;

public class TerranCommand extends Command<UserAccount>
{

    private final HttpCookie httpCookie;

    private final UserCookie userCookie;

    static Setter setter;


    public TerranCommand(HttpCookie cookie)
    {
        super(setter);
        this.httpCookie = cookie;
        this.userCookie = UserCookie.parseCookie(httpCookie);
    }


    @Override
    protected UserAccount run()
    {
        /* simulate performing network call to retrieve user information */
        try {
            Thread.sleep((int) (Math.random() * 10) + 2);
        } catch (InterruptedException e) {
            // do nothing
        }

        /* fail 5% of the time to show how fallback works */
        if (Math.random() > 0.95) {
            throw new RuntimeException("random failure processing UserAccount network response");
        }

        /* latency spike 5% of the time so timeouts can be triggered occasionally */
        if (Math.random() > 0.95) {
            // random latency spike
            try {
                Thread.sleep((int) (Math.random() * 300) + 25);
            } catch (InterruptedException e) {
                // do nothing
            }
        }

        /* success ... create UserAccount with data "from" the remote service response */
        return new UserAccount(86975, "John James", 2, true, false, true);
    }


    /**
     * Use the HttpCookie value as the cacheKey so multiple executions in the same
     * HystrixRequestContext will respond from cache.
     */

    protected String getCacheKey()
    {
        return httpCookie.getValue();
    }


    /**
     * Fallback that will use data from the UserCookie and stubbed defaults to create a UserAccount
     * if the network call failed.
     */
    @Override
    protected UserAccount getFallback()
    {
        /*
         * first 3 come from the HttpCookie
         * next 3 are stubbed defaults
         */
        return new UserAccount(userCookie.userId,
                               userCookie.name,
                               userCookie.accountType,
                               true,
                               true,
                               true);
    }



    private static class UserCookie
    {


        private static UserCookie parseCookie(HttpCookie cookie)
        {
            /* real code would parse the cookie here */
            if (Math.random() < 0.998) {
                /* valid cookie */
                return new UserCookie(12345, "Henry Peter", 1);
            } else {
                /* invalid cookie */
                throw new IllegalArgumentException();
            }
        }


        public UserCookie(int userId, String name, int accountType)
        {
            this.userId      = userId;
            this.name        = name;
            this.accountType = accountType;
        }


        private final int    userId;

        private final String name;

        private final int    accountType;

    }


}
