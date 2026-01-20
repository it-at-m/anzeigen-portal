package de.muenchen.anzeigenportal.swbrett.ads.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HtmlSanitizerUtilTest {

    @Test
    void shouldRemoveScriptsAndStylesAndKeepAllowedTags() {
        final String input = """
                <p>Hallo <strong style="color:red">Welt</strong></p>
                <script>alert('xss')</script>
                <ol>
                  <li data-list="bullet">Feature</li>
                </ol>
                """;

        final String expected = """
                <p>Hallo Welt</p>

                <ol><li>Feature</li></ol>
                """.trim();

        final String result = HtmlSanitizerUtil.sanitize(input).trim();

        assertEquals(expected, result);
    }
}
