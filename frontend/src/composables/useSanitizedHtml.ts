import type { Ref } from "vue";

import { computed } from "vue";

/**
 * Extracts clean plain text from HTML.
 * Converts <li> to comma-separated lists and preserves spacing between block elements.
 */
export function usePlainTextFromHtml(input: Ref<string>) {
  return computed(() => {
    const html = input.value;
    if (!html) return "";

    // SSR Fallback (Sicherheitshalber, falls das mal auf dem Server läuft)
    if (typeof window === "undefined" || !window.DOMParser) return "";

    // HTML vorbereiten: Kommas nach Listen, Leerzeichen nach Blöcken
    const preparedHtml = html
      .replace(/<\/li>/gi, ", </li>")
      .replace(/<\/(p|div|h[1-6])>/gi, " </$1>")
      .replace(/<br\s*\/?>/gi, " ");

    // Sicher parsen ohne Code-Ausführung
    const doc = new DOMParser().parseFromString(preparedHtml, "text/html");
    const textContent = doc.body.textContent || doc.body.innerText || "";

    // Whitespaces normalisieren und überflüssige Kommas bereinigen
    return textContent
      .replace(/\s+/g, " ")
      .replace(/,\s*(?=[.,!]|$)/g, "")
      .trim();
  });
}
