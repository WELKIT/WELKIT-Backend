package welkit_server.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "blocked")
public class BlockedDomainsConfig {

    private List<String> emailDomains;

    public List<String> getEmailDomains() {
        return emailDomains;
    }

    public void setEmailDomains(List<String> emailDomains) {
        this.emailDomains = emailDomains;
    }

}