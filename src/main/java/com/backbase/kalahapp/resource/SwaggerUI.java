package com.backbase.kalahapp.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Swagger UI
 *
 * @author revathik
 */
@Controller
public class SwaggerUI {

    /**
     * Redirecting swagger ui page
     *
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "redirect:swagger-ui.html";
    }
}
