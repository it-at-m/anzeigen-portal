import type { Info } from "@/api/info-client.ts";
import type {
  GetSettingRequest,
  SettingTO,
  UpdateSettingsRequest,
} from "@/api/swbrett";

import { getInfo } from "@/api/info-client.ts";
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

export const usePutSettings = () => {
  const api = ApiFactory.getInstance();

  return useApiCall<UpdateSettingsRequest, SettingTO[]>((params) =>
    api.updateSettings(params)
  );
};

export const useGetAppInfo = () => {
  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<void, Info>(() => getInfo());
};
