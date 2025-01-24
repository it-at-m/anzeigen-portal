import type { GetSettingRequest, SettingTO } from "@/api/swbrett";

import { inject } from "vue";

import { useApiCall } from "@/composables/api/useApiCall.ts";
import { DEFAULT_API_KEY } from "@/composables/useApi.ts";

export const useGetSettings = () => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<void, SettingTO[]>(() => api.getSettings());
};

export const useGetSetting = () => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  return useApiCall<GetSettingRequest, SettingTO>((params) =>
    api.getSetting(params)
  );
};
