<template>
  <v-select
    :model-value="modelValue"
    density="compact"
    placeholder="Kategorie"
    color="accent"
    :disabled="disabled"
    :items="categoryStore.categories"
    item-title="name"
    item-value="id"
    item-color="accent"
    :rules="[(value) => !!value || 'Bitte wählen Sie eine Kategorie aus.']"
    @update:model-value="updatedSelection"
  />
</template>

<script setup lang="ts">
import type { AdCategory } from "@/api/swbrett";

import { useCategoriesStore } from "@/stores/adcategory";

const categoryStore = useCategoriesStore();

defineProps<{
  disabled?: boolean;
  modelValue: AdCategory | undefined;
}>();

const emit = defineEmits<{
  "update:modelValue": [modelValue: AdCategory];
}>();

const updatedSelection = (selection: AdCategory) => {
  // Type error of v-select. It does not return the category, instead it returns the id.
  const id = selection as number;

  const selectedCategory = categoryStore.getCategory(id);

  if (selectedCategory) {
    emit("update:modelValue", selectedCategory);
  }
};
</script>

<style scoped></style>
