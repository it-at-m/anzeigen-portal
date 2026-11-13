import type { AdTO } from "@/apis/generated/anzeigen-backend";

import { useEventBus } from "@vueuse/core";

import {
  EV_CLEAR_CACHE,
  EV_EDIT_AD_DIALOG,
  EV_SETTINGS_UPDATED,
  EV_UPDATE_AD_LIST,
} from "@/constants.ts";

/**
 * Composes all event buses as composables.
 */
export const useClearCacheEventBus = () => useEventBus(EV_CLEAR_CACHE);

export const useDialogEventBus = () => useEventBus<AdTO>(EV_EDIT_AD_DIALOG);

export const useUpdateAdListEventBus = () => useEventBus(EV_UPDATE_AD_LIST);

export const useSettingsUpdatedEventBus = () =>
  useEventBus(EV_SETTINGS_UPDATED);
