<template>
  <ad-display-card>
    <template #title>{{ t("filterAdCategory.title") }}</template>
    <template #text>
      <v-skeleton-loader
        type="heading@8"
        :loading="categoriesStore.isEmpty"
      >
        <v-tabs
          v-model="selectedCategoryId"
          class="w-100"
          direction="vertical"
        >
          <v-tab
            :key="NO_CATEGORY.id"
            :value="NO_CATEGORY.id"
          >
            <template #prepend>
              <v-icon
                color="accent"
                icon="mdi-check-all"
              />
            </template>
            {{ NO_CATEGORY.name }}
          </v-tab>
          <v-tab
            v-for="subscribedCategory in categoriesStore.subscribedCategories"
            :key="subscribedCategory.category.id"
            :value="subscribedCategory.category.id"
            :text="subscribedCategory.category.name"
          >
            <template #prepend>
              <v-icon
                color="accent"
                icon="mdi-card-multiple"
              />
            </template>
            <template #append>
              <v-btn
                :icon="
                  subscribedCategory.subscribed
                    ? 'mdi-bell'
                    : 'mdi-bell-outline'
                "
                density="compact"
                variant="flat"
                @click="
                  subscribe(
                    subscribedCategory.category.id!,
                    subscribedCategory.subscribed
                  )
                "
              >
                <template #default>
                  <v-icon
                    color="accent"
                    icon="mdi-bell-outline"
                  />
                  {{ subscribedCategory.subscribed }}
                </template>
              </v-btn>
            </template>
          </v-tab>
        </v-tabs>
      </v-skeleton-loader>
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import { useRouteQuery } from "@vueuse/router";
import { onMounted, ref, watch } from "vue";
import { useI18n } from "vue-i18n";

import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import {
  useCreateSubscription,
  useDeleteSubscription,
} from "@/composables/api/useSubscriptionsApi.ts";
import { useUpdateSubscribtions } from "@/composables/updateCategories.ts";
import { NO_CATEGORY, QUERY_NAME_CATEGORYID } from "@/Constants.ts";
import { useCategoriesStore } from "@/stores/adcategory";

const { t } = useI18n();

const categoriesStore = useCategoriesStore();

const { call: deleteSubscription, error: deleteSubscriptionError } =
  useDeleteSubscription();

const { call: createSubscription, error: createSubscriptionError } =
  useCreateSubscription();

const updateSubscription = useUpdateSubscribtions();

const categoryQuery = useRouteQuery(QUERY_NAME_CATEGORYID);

/**
 * Internal state of current selected category.
 */
const selectedCategoryId = ref<number>(-1);

/**
 * Initializes and manages category selection and data fetching for categories.
 */
onMounted(() => {
  selectedCategoryId.value = categoryQuery.value
    ? parseInt(categoryQuery.value.toString())
    : -1;
});

/**
 * Watches for changes in the selected category and updates the query parameter accordingly.
 */
watch(selectedCategoryId, (newSelectedCategoryId) => {
  categoryQuery.value =
    newSelectedCategoryId === -1 ? null : newSelectedCategoryId.toString();
});

const subscribe = async (categoryId: number, subscribed: boolean) => {
  if (!subscribed) {
    await createSubscription({ body: categoryId });
  } else {
    await deleteSubscription({ categoryId });
  }

  await updateSubscription();
};
</script>

<style scoped></style>
