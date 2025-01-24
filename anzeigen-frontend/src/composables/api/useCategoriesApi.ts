import type { AdCategory } from "@/api/swbrett";

import { inject } from "vue";

import { useApiCall } from "@/composables/api/useApiCall";
import { DEFAULT_API_KEY } from "@/composables/useApi";

export const useCategoriesApi = () => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<void, AdCategory[]>(() => api.getAllAdCategories());
};
