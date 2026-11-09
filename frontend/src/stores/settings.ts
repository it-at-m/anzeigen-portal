import type { SettingTO, SettingTOSettingNameEnum } from "@/api/swbrett";

import { defineStore } from "pinia";
import { computed, ref } from "vue";

export const useSettingStore = defineStore("settings", () => {
  const settings = ref<SettingTO[]>([]);

  const isDirty = ref<boolean>(false);

  const isLoaded = computed(() => settings.value && settings.value.length > 0);

  const setSettings = (payload: SettingTO[]) => {
    settings.value = payload;
    isDirty.value = false;
  };

  /**
   * Overwrites a setting or creates a new entry
   * @param setting to update or push to the array
   */
  const setSetting = (setting: SettingTO) => {
    isDirty.value = true;

    const index = settings.value.findIndex(
      (s) => s.settingName === setting.settingName
    );

    settings.value = settings.value =
      index !== -1
        ? settings.value.map((s) =>
            s.settingName === setting.settingName ? setting : s
          )
        : [...settings.value, setting];
  };

  /**
   * Get a setting or return an empty new one if not exist
   * @param settingName to get
   */
  const getSetting = (settingName: SettingTOSettingNameEnum) =>
    settings.value.find((setting) => setting.settingName === settingName) || {
      settingName: settingName,
      id: settings.value.length,
    };

  return {
    settings,
    isLoaded,
    setSettings,
    getSetting,
    setSetting,
    isDirty,
  };
});
