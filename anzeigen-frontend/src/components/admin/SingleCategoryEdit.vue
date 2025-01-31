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
            label="Kategoriename"
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
            Speichern
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
            Zurücksetzen
          </v-btn>

          <yes-no-dialog
            v-else
            :model-value="deleteDialog"
            :loading="deleteCategoryLoading"
            dialogtitle="Kategorie löschen"
            dialogtext="Möchten Sie die Kategorie wirklich löschen?"
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
                Löschen
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
  value.length !== 0 || "Der Name darf nicht leer sein!";

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
      message: "Kategorie erfolgreich gespeichert.",
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
      message: "Diese Kategorie konnte nicht gelöscht werden",
    });
  } else {
    snackbar.sendMessage({
      level: Levels.SUCCESS,
      message: "Kategorie erfolgreich gelöscht.",
    });
  }

  emit("updated");

  deleteDialog.value = false;
};
</script>

<style scoped></style>
