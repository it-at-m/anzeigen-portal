import type { SettingTO } from "@/apis/generated/anzeigen-backend";

import { useGetSettings } from "@/composables/api/useSettingsApi.ts";
import { API_ERROR_MSG, STATUS_INDICATORS } from "@/constants.ts";
import { useSettingStore } from "@/stores/settings.ts";
import { useSnackbarStore } from "@/stores/snackbar.ts";

const {
  call: getSettings,
  data: settingsData,
  error: settingsError,
} = useGetSettings();

/**
 * Loads the categories and stores them
 */
export const useUpdateSettings = () => {
  const snackbar = useSnackbarStore();
  const settingStore = useSettingStore();

  return async () => {
    await getSettings();

    if (settingsError.value) {
      snackbar.push({
        color: STATUS_INDICATORS.ERROR,
        text: API_ERROR_MSG,
      });
      return;
    }

    settingStore.setSettings(settingsData.value as SettingTO[]);
  };
};
