import type { GetSettingRequest, SettingTO } from "@/api/swbrett";

import { useApiCall } from "@/composables/api/useApiCall.ts";
import { ApiFactory } from "@/util/apiFactory.ts";

export const useGetSetting = () => {
  const api = ApiFactory.getInstance();

  return useApiCall<GetSettingRequest, SettingTO>((params) =>
    api.getSetting(params)
  );
};

export const useGetSettings = () => {
  const api = ApiFactory.getInstance();

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<void, SettingTO[]>(() => api.getSettings());
};
