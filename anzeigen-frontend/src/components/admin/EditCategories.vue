<template>
  <ad-display-card>
    <template #title>Kategorien bearbeiten</template>
    <template #text>
      <v-btn
        prepend-icon="mdi-plus"
        color="accent"
        variant="flat"
      >
        Kategorie hinzufügen
      </v-btn>
      <v-list lines="two">
        <v-list-item
          v-for="category in categoryStore.categories"
          :key="category.id"
          :title="category.name"
          rounded
          class="mb-2"
        >
          <template #title>
            <div v-if="category.id === categoryToEdit.id">
              <v-text-field
                variant="outlined"
                class="w-66"
                color="accent"
                hide-details
                :model-value="category.name"
              />
            </div>
            <div v-else>
              {{ category.name }}
            </div>
          </template>
          <template #append>
            <v-row>
              <v-col
                v-if="!category.standard && category.id !== categoryToEdit?.id"
                cols="6"
              >
                <v-btn
                  class="w-100"
                  prepend-icon="mdi-trash-can-outline"
                  variant="outlined"
                  color="red"
                >
                  Löschen
                </v-btn>
              </v-col>
              <v-col
                v-if="category.id === categoryToEdit?.id"
                cols="6"
              >
                <v-btn
                  class="w-100"
                  prepend-icon="mdi-pencil"
                  variant="outlined"
                  color="accent"
                  @click="resetEditCategory"
                >
                  Abbrechen
                </v-btn>
              </v-col>
              <v-col cols="6">
                <v-btn
                  prepend-icon="mdi-pencil"
                  class="ml-2 w-100"
                  variant="flat"
                  color="accent"
                  @click="editCategory(category)"
                >
                  {{
                    category.id === categoryToEdit?.id
                      ? "Speichern"
                      : "Bearbeiten"
                  }}
                </v-btn>
              </v-col>
            </v-row>
          </template>
        </v-list-item>
      </v-list>
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import type { AdCategory } from "@/api/swbrett";

import { ref } from "vue";

import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { useCategoriesStore } from "@/stores/adcategory.ts";

const categoryStore = useCategoriesStore();

const categoryToEdit = ref<AdCategory>({});

const editCategory = (category: AdCategory) => {
  categoryToEdit.value = category;
};

const resetEditCategory = () => {
  categoryToEdit.value = {};
};
</script>

<style scoped></style>
