import { computed } from "vue";

import { QUERY_NAME_ORDER, QUERY_NAME_SORTBY } from "@/Constants.ts";
import { useSettingStore } from "@/stores/settings.ts";

export const useDefaultQuery = () => {
  const settingStore = useSettingStore();

  return computed(() => {
    return {
      [QUERY_NAME_ORDER]:
        settingStore.getSetting("DEFAULT_ORDERING")?.textValue,
      [QUERY_NAME_SORTBY]:
        settingStore.getSetting("DEFAULT_SORTING")?.textValue,
    };
  });
};
