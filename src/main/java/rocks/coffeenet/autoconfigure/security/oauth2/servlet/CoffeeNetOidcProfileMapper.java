package rocks.coffeenet.autoconfigure.security.oauth2.servlet;

import org.springframework.boot.context.properties.PropertyMapper;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import rocks.coffeenet.security.profile.CoffeeNetProfile;
import rocks.coffeenet.security.profile.DefaultCoffeeNetProfile;
import rocks.coffeenet.security.profile.PrincipalProfileMapper;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class CoffeeNetOidcProfileMapper implements PrincipalProfileMapper {

    @Override
    public CoffeeNetProfile mapPrincipal(Principal principal) {

        if (!OAuth2AuthenticationToken.class.isInstance(principal)) {
            return null;
        }

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;

        if (!OidcUser.class.isInstance(token.getPrincipal())) {
            return null;
        }

        OidcUser oidcUser = (OidcUser) token.getPrincipal();
        DefaultCoffeeNetProfile.Builder builder;

        String uniqueIdentifier = mapHashed(oidcUser.getSubject());
        String name = oidcUser.getPreferredUsername();

        builder = DefaultCoffeeNetProfile.withUniqueIdentifierAndName(uniqueIdentifier, name);

        PropertyMapper mapper = PropertyMapper.get();
        mapper.from(oidcUser::getFullName).whenNonNull().to(builder::withHumanReadableName);
        mapper.from(oidcUser::getProfile).whenNonNull().as(this::mapURL).whenNonNull().to(builder::withProfileURL);
        mapper.from(oidcUser::getPicture).whenNonNull().as(this::mapURL).whenNonNull().to(builder::withPictureURL);
        mapper.from(oidcUser::getEmail).whenNonNull().to(builder::withEmail);

        return builder.build();
    }


    @Override
    public boolean supports(Class<?> clazz) {

        return OAuth2AuthenticationToken.class.isAssignableFrom(clazz);
    }


    private String mapHashed(String claim) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(claim.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Couldn't get an instance of the SHA-256 digest, this should never happen.", e);
        }
    }


    private URL mapURL(String urlString) {

        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
