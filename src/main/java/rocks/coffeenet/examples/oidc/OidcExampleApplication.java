package rocks.coffeenet.examples.oidc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@SpringBootApplication
public class OidcExampleApplication {

    public static void main(String[] args) {

        SpringApplication.run(OidcExampleApplication.class, args);
    }

    @Configuration
    static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) throws Exception {

            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {

            //J-
            //@formatter:off
            http.authorizeRequests()
                .antMatchers("/authentication-needed").authenticated()
                .antMatchers("/authentication-not-needed").anonymous();
            //@formatter:on
            //J+
        }
    }
}
