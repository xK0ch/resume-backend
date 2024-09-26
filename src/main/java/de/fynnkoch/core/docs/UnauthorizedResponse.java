package de.fynnkoch.core.docs;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Inherited
@Documented
@Retention(RUNTIME)
@Target(METHOD)
@ApiResponse(responseCode = "401", content = @Content)
public @interface UnauthorizedResponse {
  @AliasFor(annotation = ApiResponse.class)
  String description() default "If the user is not authorized to access this resource.";
}
