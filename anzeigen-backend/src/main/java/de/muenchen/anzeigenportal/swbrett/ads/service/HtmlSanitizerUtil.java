package de.muenchen.anzeigenportal.swbrett.ads.service;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.shim.*;

public class HtmlSanitizerUtil {

    private static final PolicyFactory POLICY = new HtmlPolicyBuilder()
            // Text formatting
            .allowElements("b", "i", "u")
            // Headings
            .allowElements("h2", "h3")
            // Structure
            .allowElements("p", "ol", "ul", "li")
            // No attributes allowed at all
            .disallowElements("script", "style")
            .disallowAttributes("style", "class").globally()
            //.disallowAttributes("style", "class")
            //.disallowElements("script", "style")
            .toFactory();

    public static String sanitize(String htmlToSanitize) {
        if (htmlToSanitize == null || htmlToSanitize.isBlank()) {
            return "";
        }
        return POLICY.sanitize(htmlToSanitize);
    }
}
