package dongguk.rangers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test", description = "Test API입니다.")
@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping
    public String home() {
        return "CI/CD Test";
    }
}
