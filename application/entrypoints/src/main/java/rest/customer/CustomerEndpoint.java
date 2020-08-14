package rest.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerEndpoint.class);

    @PostMapping
    public ResponseEntity<CustomerDTO> register(@RequestBody CustomerDTO customer) {
        return null;
    }
}
