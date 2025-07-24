<template>
  <v-list-item
    variant="text"
    color="accent"
    rounded
    class="mb-2"
  >
    <template #title>
      <v-row>
        <v-col
          cols="12"
          md="8"
        >
          <v-text-field
            v-model="name"
            variant="outlined"
            density="compact"
            :disabled="updateCategoryLoading"
            hide-details="auto"
            color="accent"
            class="mt-2"
            :label="t('editCategories.categoryName')"
            :rules="[notEmptyRule]"
          />
        </v-col>
        <v-col
          cols="6"
          md="2"
          align-self="end"
        >
          <v-btn
            variant="flat"
            prepend-icon="mdi-content-save"
            color="accent"
            class="w-100"
            :disabled="!isEdited || updateCategoryLoading"
            :loading="updateCategoryLoading"
            @click="updateCategoryName"
          >
            {{ t("common.save") }}
          </v-btn>
        </v-col>
        <v-col
          cols="6"
          md="2"
          align-self="end"
        >
          <v-btn
            v-if="isEdited"
            variant="outlined"
            prepend-icon="mdi-undo-variant"
            color="accent"
            class="w-100"
            :disabled="updateCategoryLoading"
            @click="reset"
          >
            {{ t("common.reset") }}
          </v-btn>

          <yes-no-dialog
            v-else
            :model-value="deleteDialog"
            :loading="deleteCategoryLoading"
            :dialogtitle="t('singleCategoryEdit.deleteConfirmationDialogTitle')"
            :dialogtext="t('singleCategoryEdit.deleteConfirmationDialogText')"
            @yes="confirmedDeletion"
            @no="deleteDialog = false"
          >
            <template #default>
              <v-btn
                variant="outlined"
                color="error"
                :disabled="category.standard"
                class="w-100"
                prepend-icon="mdi-trash-can-outline"
                @click="deleteDialog = true"
              >
                {{ t("common.delete") }}
              </v-btn>
            </template>
          </yes-no-dialog>
        </v-col>
      </v-row>
    </template>
  </v-list-item>
</template>

<script setup lang="ts">
import type { AdCategory } from "@/api/swbrett";

import { computed, ref } from "vue";
import { useI18n } from "vue-i18n";

import { Levels } from "@/api/error.ts";
import YesNoDialog from "@/components/common/YesNoDialog.vue";
import {
  useDeleteAdCategory,
  useUpdateAdCategory,
} from "@/composables/api/useCategoriesApi.ts";
import { useSnackbar } from "@/composables/useSnackbar.ts";

const { category } = defineProps<{
  category: AdCategory;
}>();

const { t } = useI18n();

const deleteDialog = ref<boolean>(false);

const snackbar = useSnackbar();

const name = ref<string | undefined>(category.name);

const {
  call: deleteCategory,
  loading: deleteCategoryLoading,
  error: deleteCategoryError,
} = useDeleteAdCategory();

const {
  call: updateCategory,
  loading: updateCategoryLoading,
  error: createCategoryError,
} = useUpdateAdCategory();

const isEdited = computed(() => name.value !== category.name);

const reset = () => (name.value = category.name);

const emit = defineEmits<{
  updated: [];
}>();

const notEmptyRule = (value: string) =>
  value.length !== 0 || t("singleCategoryEdit.notEmptyMessage");

const updateCategoryName = async () => {
  if (category.id) {
    await updateCategory({
      id: category.id,
      adCategory: {
        ...category,
        name: name.value,
      },
    });
  }

  if (!createCategoryError.value) {
    snackbar.sendMessage({
      level: Levels.SUCCESS,
      message: t("singleCategoryEdit.categoryChangeSuccess"),
    });
  } else {
    snackbar.sendMessage({
      level: Levels.WARNING,
      message: t("singleCategoryEdit.categoryChangeError"),
    });
  }

  emit("updated");
};

const confirmedDeletion = async () => {
  if (category.id) {
    await deleteCategory({
      id: category.id,
    });
  }

  if (deleteCategoryError.value) {
    snackbar.sendMessage({
      level: Levels.WARNING,
      message: t("singleCategoryEdit.categoryDeleteError"),
    });
  } else {
    snackbar.sendMessage({
      level: Levels.SUCCESS,
      message: t("singleCategoryEdit.categoryDeleteSuccess"),
    });
  }

  emit("updated");

  deleteDialog.value = false;
};
</script>

<style scoped></style>
