package nyomio.app.core

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule

@Controller("/api/v1/core")
class TestEndPoint {

    @Get("/hello")
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun hello(): String {
        return "Hello"
    }
}
