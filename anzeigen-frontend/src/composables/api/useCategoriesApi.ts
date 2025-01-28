import type { AdCategory } from "@/api/swbrett";

import { useApiCall } from "@/composables/api/useApiCall";
import { ApiFactory } from "@/util/apiFactory.ts";

export const useCategoriesApi = () => {
  const api = ApiFactory.getInstance();

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<void, AdCategory[]>(() => api.getAllAdCategories());
};
