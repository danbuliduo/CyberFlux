package io.cyberflux.example.spring.webflux.custom;

import org.springframework.stereotype.Component;

import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.TemplateReactor;

@Component
public class CustomReactor extends TemplateReactor {
    public CustomReactor() {
        super(CyberType.CUSTOM, "---");
    }
}
