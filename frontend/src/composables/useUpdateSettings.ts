import type { SettingTO } from "@/api/swbrett";

import { Levels } from "@/api/error.ts";
import { useGetSettings } from "@/composables/api/useSettingsApi.ts";
import { useSnackbar } from "@/composables/useSnackbar.ts";
import { API_ERROR_MSG } from "@/Constants.ts";
import { useSettingStore } from "@/stores/settings.ts";

const {
  call: getSettings,
  data: settingsData,
  error: settingsError,
} = useGetSettings();

/**
 * Loads the categories and stores them
 */
export const useUpdateSettings = () => {
  const snackbar = useSnackbar();
  const settingStore = useSettingStore();

  return async () => {
    await getSettings();

    if (settingsError.value) {
      snackbar.sendMessage({
        level: Levels.ERROR,
        message: API_ERROR_MSG,
      });
      return;
    }

    settingStore.setSettings(settingsData.value as SettingTO[]);
  };
};
