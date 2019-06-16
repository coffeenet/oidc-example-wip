package rocks.coffeenet.examples.oidc;

import org.springframework.http.MediaType;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import java.util.HashMap;
import java.util.Map;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> index() {

        return Map.of("hello", "world");
    }


    @GetMapping(value = "/authentication-needed", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> authenticationNeeded(Principal p) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        HashMap<String, Object> result = new HashMap<>();
        result.put("principal", p);
        result.put("authentication", authentication);

        return result;
    }


    @GetMapping(value = "/authentication-not-needed", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> authenticationNotNeeded(Principal p) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        HashMap<String, Object> result = new HashMap<>();
        result.put("principal", p);
        result.put("authentication", authentication);

        return result;
    }
}
