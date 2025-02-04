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

  const setSetting = (setting: SettingTO) => {
    isDirty.value = true;
    settings.value = settings.value.map((s) =>
      s.settingName === setting.settingName ? setting : s
    );
  };

  const getSetting = (settingName: SettingTOSettingNameEnum) =>
    settings.value.find((setting) => setting.settingName === settingName);

  return {
    settings,
    isLoaded,
    setSettings,
    getSetting,
    setSetting,
    isDirty,
  };
});
