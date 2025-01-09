import type { AdTO, GetAds200Response } from "@/api/swbrett";
import type { DeepReadonly } from "vue";

import { defineStore } from "pinia";
import { computed, ref } from "vue";

export const useAdStore = defineStore("ad", () => {
  const listOfAds = ref<DeepReadonly<AdTO[]>>([]);

  const meta = ref();

  const page = ref<number>(0);

  const nextPage = computed(() => page.value + 1);

  const isEmpty = computed(() => listOfAds.value.length === 0);

  const addNewAds = (
    listOfAdditionalAds: DeepReadonly<GetAds200Response>,
    isNextPage = false
  ) => {
    page.value = isNextPage ? page.value + 1 : 0;

    const { content, ...other } = listOfAdditionalAds;

    listOfAds.value = isNextPage
      ? [...listOfAds.value, ...(content || [])]
      : content || [];

    meta.value = other;
  };

  return {
    isEmpty,
    listOfAds,
    nextPage,
    addNewAds,
    meta,
  };
});
