<template>
  <ad-display-card>
    <template #title>
      <v-row>
        <v-col> Kategorien bearbeiten </v-col>
        <v-col class="d-flex justify-end">
          <v-btn
            prepend-icon="mdi-plus"
            color="accent"
            variant="flat"
            @click="createDialog = true"
          >
            Kategorie erstellen
          </v-btn>
        </v-col>
      </v-row>
    </template>
    <template #text>
      <v-dialog
        v-model="createDialog"
        max-width="600"
      >
        <template #default>
          <v-card
            :loading="createCategoryLoading"
            :disabled="createCategoryLoading"
            title="Kategorie erstellen"
            subtitle="Geben Sie einen Kategorienamen ein."
          >
            <template #text>
              <v-text-field
                v-model="newCategoryName"
                label="Kategoriename"
                variant="outlined"
                :rules="[notEmptyRule]"
              />
            </template>
            <template #actions>
              <v-btn
                prepend-icon="mdi-window-close"
                variant="outlined"
                text="Abbrechen"
                @click="createDialog = false"
              />
              <v-btn
                variant="elevated"
                color="accent"
                prepend-icon="mdi-content-save-outline"
                @click="clickCreateCategory"
              >
                <p>Erstellen</p>
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
import { ref } from "vue";

import { Levels } from "@/api/error.ts";
import SingleCategoryEdit from "@/components/admin/SingleCategoryEdit.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { useCreateAdCategory } from "@/composables/api/useCategoriesApi.ts";
import { useUpdateCategories } from "@/composables/updateCategories.ts";
import { useSnackbar } from "@/composables/useSnackbar.ts";
import { useCategoriesStore } from "@/stores/adcategory.ts";

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

const notEmptyRule = (value: string) =>
  value.length !== 0 || "Der Name darf nicht leer sein!";

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
      message: "Kategorie erfolgreich erstellt.",
    });
  }

  await updateCategories();

  createDialog.value = false;
};
</script>

<style scoped></style>
