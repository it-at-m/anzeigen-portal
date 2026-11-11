import type { AdCategory } from "@/apis/generated/anzeigen-backend";

import { useGetCategories } from "@/composables/api/useCategoriesApi.ts";
import { API_ERROR_MSG, STATUS_INDICATORS } from "@/constants.ts";
import { useCategoriesStore } from "@/stores/adcategory.ts";
import { useSnackbarStore } from "@/stores/snackbar.ts";

const {
  call: getCategories,
  data: categoryData,
  error: categoryError,
} = useGetCategories();

/**
 * Loads the categories and stores them
 */
export const useUpdateCategories = () => {
  const snackbar = useSnackbarStore();
  const categoriesStore = useCategoriesStore();

  return async () => {
    // Fetch categories from the API.
    await getCategories();

    // Error handling: show a snackbar if the API call fails.
    if (categoryError.value) {
      snackbar.push({
        color: STATUS_INDICATORS.ERROR,
        text: API_ERROR_MSG,
      });
    }

    // Store the fetched categories.
    categoriesStore.setCategories(categoryData.value as AdCategory[]);
  };
};
