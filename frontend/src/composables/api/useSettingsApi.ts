import type {
  GetSettingRequest,
  SettingTO,
  UpdateSettingsRequest,
} from "@/apis/generated/anzeigen-backend";
import type { Info } from "@/apis/info-client.ts";

import { DefaultApi } from "@/apis/generated/anzeigen-backend";
import { getInfo } from "@/apis/info-client.ts";
import { useApi } from "@/composables/api/useApi.ts";
import { ApiFactory } from "@/util/apiFactory.ts";

export const useGetSetting = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<GetSettingRequest, SettingTO>((params) =>
    api.getSetting(params)
  );
};

export const useGetSettings = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApi<void, SettingTO[]>(() => api.getSettings());
};

export const usePutSettings = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<UpdateSettingsRequest, SettingTO[]>((params) =>
    api.updateSettings(params)
  );
};

export const useGetAppInfo = () => {
  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApi<void, Info>(() => getInfo());
};
