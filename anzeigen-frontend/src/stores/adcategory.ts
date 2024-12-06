import type { AdCategory } from "@/api/swbrett";

import { defineStore } from "pinia";
import { ref } from "vue";

import { useGetCategories } from "@/composables/api/useGetCategories";

const { call, data, error } = useGetCategories();

export const useCategoriesStore = defineStore("category", () => {
  const categories = ref<AdCategory[]>([]);

  const setCategories = (payload: AdCategory[]) => {
    categories.value = payload;
  };

  const update = async () => {
    await call();
    if (!error.value) {
      categories.value = data.value as AdCategory[];
    }
  };

  return { categories, setCategories, update };
});
