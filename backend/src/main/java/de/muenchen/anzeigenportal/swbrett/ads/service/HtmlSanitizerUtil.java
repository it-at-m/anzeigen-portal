package de.muenchen.anzeigenportal.swbrett.ads.service;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import java.util.regex.Pattern;

public final class HtmlSanitizerUtil {

    private static final PolicyFactory POLICY = new HtmlPolicyBuilder()
            // Text formatting
            .allowElements("strong", "em", "u")
            // Structure
            .allowElements("p", "ol", "ul", "li")
            // allow list differentiator from delta-type
            .allowAttributes("data-list").onElements("li")
            .allowAttributes("class").matching(Pattern.compile("ql-indent-[0-9]+")).onElements("li")
            // No attributes allowed at all
            .disallowElements("script", "style")
            .disallowAttributes("style").globally()
            //.disallowAttributes("style", "class")
            //.disallowElements("script", "style")
            .toFactory();

    private HtmlSanitizerUtil() {
    }

    public static String sanitize(final String htmlToSanitize) {
        if (htmlToSanitize == null || htmlToSanitize.isBlank()) {
            return "";
        }
        return POLICY.sanitize(htmlToSanitize);
    }
}
