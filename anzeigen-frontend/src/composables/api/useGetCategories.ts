import type { AdCategory } from "@/api/swbrett";

import { inject, readonly, ref } from "vue";

import { DEFAULT_API_KEY } from "@/composables/useApi";

export function useGetCategories() {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  const loadingInternal = ref(false);
  const errorInternal = ref(false);
  const dataInternal = ref<AdCategory[]>();

  const loading = readonly(loadingInternal);
  const error = readonly(errorInternal);
  const data = readonly(dataInternal);

  const call = () => {
    loadingInternal.value = true;
    errorInternal.value = false;

    return api
      .getAllAdCategories()
      .then((data) => {
        dataInternal.value = data;
      })
      .catch(() => {
        errorInternal.value = true;
      })
      .finally(() => {
        loadingInternal.value = false;
      });
  };

  return {
    loading,
    error,
    data,
    call,
  };
}
