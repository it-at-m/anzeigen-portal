<template>
  <ad-display-card>
    <template #title> {{ t("editCategories.title") }} </template>
    <template #text>
      <v-btn
        prepend-icon="mdi-plus"
        color="accent"
        class="ml-4"
        variant="flat"
        @click="createDialog = true"
      >
        {{ t("editCategories.createCategories.title") }}
      </v-btn>
      <v-dialog
        v-model="createDialog"
        max-width="600"
      >
        <template #default>
          <v-card
            :loading="createCategoryLoading"
            :disabled="createCategoryLoading"
            :title="t('editCategories.createCategories.title')"
            :subtitle="t('editCategories.createCategories.subtitle')"
          >
            <template #text>
              <v-text-field
                v-model="newCategoryName"
                :label="t('editCategories.categoryName')"
                variant="outlined"
                :rules="[notEmptyRule]"
              />
            </template>
            <template #actions>
              <v-btn
                prepend-icon="mdi-window-close"
                variant="outlined"
                :text="t('common.cancel')"
                @click="createDialog = false"
              />
              <v-btn
                variant="elevated"
                color="accent"
                prepend-icon="mdi-content-save-outline"
                :disabled="isEmptyNewCategoryName"
                @click="clickCreateCategory"
              >
                <p>{{ t("common.create") }}</p>
              </v-btn>
            </template>
          </v-card>
        </template>
      </v-dialog>

      <v-list lines="two">
        <single-category-edit
          v-for="category in categoryStore.categories"
          :key="category.id"
          :category="category"
          @updated="updateCategories"
        />
      </v-list>
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import { useI18n } from "vue-i18n";

import { Levels } from "@/api/error.ts";
import SingleCategoryEdit from "@/components/admin/SingleCategoryEdit.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { useCreateAdCategory } from "@/composables/api/useCategoriesApi.ts";
import { useUpdateCategories } from "@/composables/updateCategories.ts";
import { useSnackbar } from "@/composables/useSnackbar.ts";
import { useCategoriesStore } from "@/stores/adcategory.ts";

const { t } = useI18n();

const categoryStore = useCategoriesStore();

const snackbar = useSnackbar();

const createDialog = ref<boolean>(false);

const newCategoryName = ref<string>("");

const updateCategories = useUpdateCategories();

const {
  call: createCategory,
  loading: createCategoryLoading,
  error: createCategoryError,
} = useCreateAdCategory();

const isEmptyNewCategoryName = computed(
  () => newCategoryName.value.length === 0
);

const notEmptyRule = (value: string) =>
  value.length !== 0 || t("editCategories.createCategories.notEmptyMessage");

const clickCreateCategory = async () => {
  await createCategory({
    adCategory: {
      name: newCategoryName.value,
      standard: false,
    },
  });

  if (!createCategoryError.value) {
    snackbar.sendMessage({
      level: Levels.SUCCESS,
      message: t("editCategories.createCategories.success"),
    });
  } else {
    snackbar.sendMessage({
      level: Levels.WARNING,
      message: t("editCategories.createCategories.error"),
    });
  }

  await updateCategories();

  createDialog.value = false;
};
</script>

<style scoped></style>
