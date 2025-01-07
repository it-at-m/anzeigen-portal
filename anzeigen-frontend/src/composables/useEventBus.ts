import { useEventBus } from "@vueuse/core";

import { EV_CLEAR_CACHE, EV_UPDATE_AD_LIST } from "@/Constants";

export const useClearCacheEventBus = () => useEventBus(EV_CLEAR_CACHE);

export const useUpdateAdListEventBus = () => useEventBus(EV_UPDATE_AD_LIST);
