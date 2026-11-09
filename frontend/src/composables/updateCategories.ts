import type { AdCategory } from "@/api/swbrett";

import { Levels } from "@/api/error.ts";
import { useGetCategories } from "@/composables/api/useCategoriesApi.ts";
import { useSnackbar } from "@/composables/useSnackbar.ts";
import { API_ERROR_MSG } from "@/Constants.ts";
import { useCategoriesStore } from "@/stores/adcategory.ts";

const {
  call: getCategories,
  data: categoryData,
  error: categoryError,
} = useGetCategories();

/**
 * Loads the categories and stores them
 */
export const useUpdateCategories = () => {
  const snackbar = useSnackbar();
  const categoriesStore = useCategoriesStore();

  return async () => {
    // Fetch categories from the API.
    await getCategories();

    // Error handling: show a snackbar if the API call fails.
    if (categoryError.value) {
      snackbar.sendMessage({
        level: Levels.ERROR,
        message: API_ERROR_MSG,
      });
    }

    // Store the fetched categories.
    categoriesStore.setCategories(categoryData.value as AdCategory[]);
  };
};
