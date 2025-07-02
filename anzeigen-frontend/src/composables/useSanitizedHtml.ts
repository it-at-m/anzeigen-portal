// composables/useSanitizedHtml.ts
import type { Ref } from "vue";

import { computed } from "vue";

/**
 * Sanitizes a reactive HTML string using sanitize-html.
 * @param input A ref to the potentially unsafe HTML string.
 * @returns A computed ref with the sanitized HTML string.
 */
export function useSanitizedHtml(input: Ref<string>) {
  return computed(() => {
    const tmp = document.createElement("div");
    tmp.innerHTML = input.value;
    return tmp.textContent || tmp.innerText || "";
  });
}
