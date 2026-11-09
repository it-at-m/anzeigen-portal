import type { AdTO, GetAds200Response } from "@/api/swbrett";
import type { DeepReadonly } from "vue";

import { defineStore } from "pinia";
import { computed, ref } from "vue";

/**
 * Pinia store for managing the displayed ads.
 */
export const useAdStore = defineStore("ad", () => {
  /**
   * List of currently shown ads.
   */
  const listOfAds = ref<DeepReadonly<AdTO[]>>([]);

  /**
   * Meta information of newest ads.
   */
  const meta = ref();

  /**
   * Current page number which was fetched.
   */
  const page = ref<number>(0);

  /**
   * The next page number.
   */
  const nextPage = computed(() => page.value + 1);

  /**
   * If ads are available
   */
  const isEmpty = computed(() => listOfAds.value.length === 0);

  /**
   * Sets or adds new ads to the current state. Increments / resets the page number.
   * @param listOfAdditionalAds new list of ads.
   * @param isNextPage should the new list be appended or replaced.
   */
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
