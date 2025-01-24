import type { AdCategory } from "@/api/swbrett";

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

  /**
   * Replace the list of categories.
   * @param payload new categories.
   */
  const setCategories = (payload: AdCategory[]) => {
    categories.value = payload;
  };

  /**
   * Get specific category by id. Needed because the v-select just returns the id of the category.
   * @param id of the category to get.
   */
  const getCategory = (id: number) => {
    return categories.value.find((category) => category.id === id);
  };

  /**
   * If categories are available.
   */
  const isEmpty = computed(() => categories.value.length === 0);

  return { categories, setCategories, isEmpty, getCategory };
});
