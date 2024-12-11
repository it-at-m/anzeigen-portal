import { useEventBus } from "@vueuse/core";

import { EV_CLEAR_CACHE } from "@/Constants";

export const useClearCacheEventBus = () => useEventBus(EV_CLEAR_CACHE);
