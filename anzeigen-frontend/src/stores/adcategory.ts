import type { AdCategory, SubscriptionTO } from "@/api/swbrett";

import { defineStore } from "pinia";
import { computed, ref } from "vue";

/**
 * Pinia store for managing the ad category.
 */
export const useCategoriesStore = defineStore("category", () => {
  /**
   * Available ad categories.
   */
  const categories = ref<AdCategory[]>([]);

  const subscriptions = ref<SubscriptionTO[]>([]);

  /**
   * Replace the list of categories.
   * @param payload new categories.
   */
  const setCategories = (payload: AdCategory[]) => {
    categories.value = payload;
  };

  /**
   * Replace the list of categories.
   * @param payload new categories.
   */
  const setSubscriptions = (payload: SubscriptionTO[]) => {
    subscriptions.value = payload;
  };

  /**
   * Get specific category by id. Needed because the v-select just returns the id of the category.
   * @param id of the category to get.
   */
  const getCategory = (id: number) => {
    return categories.value.find((category) => category.id === id);
  };

  //TODO: ... use this one!
  const subscribedCategories = computed(() =>
    categories.value.map((c) => {
      return {
        category: c,
        subscribed:
          !!subscriptions.value.find((s) => s.adCategory?.id === c.id) || false,
      };
    })
  );

  /**
   * If categories are available.
   */
  const isEmptyCategories = computed(() => categories.value.length === 0);
  const isEmptySubscriptions = computed(() => subscriptions.value.length === 0);

  return {
    categories,
    setCategories,
    isEmpty: isEmptyCategories,
    getCategory,
    isEmptySubscriptions,
    subscribedCategories,
    setSubscriptions,
  };
});
