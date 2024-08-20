package dongguk.rangers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Hello, World!";
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}

