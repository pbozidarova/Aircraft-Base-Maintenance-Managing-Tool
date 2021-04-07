package managing.tool.config;

import managing.tool.exception.CustomGlobalCaughtExp;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Configuration
public class ErrorsConfiguration {

    @Bean
    DefaultErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest,
                                                          ErrorAttributeOptions options) {

                Map<String, Object> attributes = super.getErrorAttributes(webRequest, options);

                Throwable error = getError(webRequest);

                if (error instanceof CustomGlobalCaughtExp) {
                    CustomGlobalCaughtExp myException = (CustomGlobalCaughtExp) error;

                    attributes.put("message", myException.getMessage());
                    attributes.put("causedBy", myException.getCausedBy());

                    attributes.put("reason", "N/A");

               }

                return attributes;
            }

        };
    }

}
