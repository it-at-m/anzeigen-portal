import type { AdCategory, SubscriptionTO } from "@/api/swbrett";

import { Levels } from "@/api/error.ts";
import { useGetCategories } from "@/composables/api/useCategoriesApi.ts";
import { useGetSubscriptions } from "@/composables/api/useSubscriptionsApi.ts";
import { useSnackbar } from "@/composables/useSnackbar.ts";
import { API_ERROR_MSG } from "@/Constants.ts";
import { useCategoriesStore } from "@/stores/adcategory.ts";

const {
  call: getCategories,
  data: categoryData,
  error: categoryError,
} = useGetCategories();

const {
  call: getSubscriptions,
  data: subscriptionsData,
  error: subscribtionsError,
} = useGetSubscriptions();

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

/**
 * Loads the categories and stores them
 */
export const useUpdateSubscribtions = () => {
  const snackbar = useSnackbar();
  const categoriesStore = useCategoriesStore();

  return async () => {
    // Fetch categories from the API.
    await getSubscriptions();

    // Error handling: show a snackbar if the API call fails.
    if (subscribtionsError.value) {
      snackbar.sendMessage({
        level: Levels.ERROR,
        message: API_ERROR_MSG,
      });
    }

    // Store the fetched categories.
    categoriesStore.setSubscriptions(
      subscriptionsData.value as SubscriptionTO[]
    );
  };
};
