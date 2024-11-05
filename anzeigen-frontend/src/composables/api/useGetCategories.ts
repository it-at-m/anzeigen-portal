import type { AdCategory } from "@/types/api/AdCategory";

import { readonly, ref } from "vue";

export function useGetCategories() {
  const loadingInternal = ref(false);
  const errorInternal = ref(false);
  const dataInternal = ref<AdCategory[]>();

  const loading = readonly(loadingInternal);
  const error = readonly(errorInternal);
  const data = readonly(dataInternal);

  const call = async () => {
    loadingInternal.value = true;
    errorInternal.value = false;
    await new Promise((r) => setTimeout(r, 500)); // TODO: remove
    try {
      dataInternal.value = [
        { id: 5, name: "Sonstiges", standard: true },
        { id: 1, name: "Möbel, Ausstattung und Garten", standard: false },
        { id: 2, name: "Bücher, Filme und Musik", standard: false },
        { id: 4, name: "Freizeit, Sport und Hobby", standard: false },
        { id: 3, name: "Immobilien und Wohnungen", standard: false },
        { id: 6, name: "Auto und Motorrad", standard: false },
        { id: 7, name: "Dienstleistungen", standard: false },
        { id: 8, name: "Eintrittskarten und Tickets", standard: false },
        { id: 9, name: "Elektronik", standard: false },
        { id: 10, name: "Familie und Kind", standard: false },
        { id: 12, name: "Kleidung", standard: false },
        { id: 13, name: "Lost and Found (Fundsachen)", standard: false },
        { id: 14, name: "Tausche Hilfe gegen Hilfe", standard: false },
      ];
    } catch (e) {
      errorInternal.value = true;
    } finally {
      loadingInternal.value = false;
    }
  };

  return {
    loading,
    error,
    data,
    call,
  };
}