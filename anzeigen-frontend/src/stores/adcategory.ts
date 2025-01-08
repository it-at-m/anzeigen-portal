import type { AdCategory } from "@/api/swbrett";

import { defineStore } from "pinia";
import { computed, ref } from "vue";

export const useCategoriesStore = defineStore("category", () => {
  const categories = ref<AdCategory[]>([]);

  const setCategories = (payload: AdCategory[]) => {
    categories.value = payload;
  };

  const isEmpty = computed(() => categories.value.length === 0);

  return { categories, setCategories, isEmpty };
});
