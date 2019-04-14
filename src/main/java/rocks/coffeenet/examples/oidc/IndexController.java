package rocks.coffeenet.examples.oidc;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String index(Principal p) {

        return "Hello world!";
    }
}
