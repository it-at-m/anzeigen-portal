import type { AdCategory } from "@/api/swbrett";

import { defineStore } from "pinia";
import { ref } from "vue";

export const useCategoriesStore = defineStore("category", () => {
  const categories = ref<AdCategory[]>([]);

  const setCategories = (payload: AdCategory[]) => {
    categories.value = payload;
  };

  return { categories, setCategories };
});
