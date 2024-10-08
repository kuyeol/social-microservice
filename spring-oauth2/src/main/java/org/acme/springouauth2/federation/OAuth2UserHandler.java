package org.acme.springouauth2.federation;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.function.Consumer;

public final class OAuth2UserHandler implements Consumer< OAuth2User > {

    private final UserRepository userRepository = new UserRepository();



    @Override
    public void accept( OAuth2User user ) {



        if (this.userRepository.findByName(user.getName()) == null) {
            System.out.println("Saving first-time user: name=" + user.getName() + ", claims=" + user.getAttributes() + ", authorities=" + user.getAuthorities());
            this.userRepository.save(user);
        }
    }

    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    @Override
    public Consumer< OAuth2User > andThen( Consumer< ? super OAuth2User > after ) {
        return Consumer.super.andThen( after );
    }
}
