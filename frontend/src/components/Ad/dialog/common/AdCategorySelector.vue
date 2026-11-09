<template>
  <v-select
    :model-value="modelValue"
    density="compact"
    :placeholder="t('adCategorySelector.placeholder')"
    color="accent"
    :disabled="disabled"
    :items="categoryStore.categories"
    item-title="name"
    item-value="id"
    item-color="accent"
    :rules="[(value) => !!value || t('adCategorySelector.ruleMsg')]"
    @update:model-value="updatedSelection"
  />
</template>

<script setup lang="ts">
import type { AdCategory } from "@/api/swbrett";

import { useI18n } from "vue-i18n";

import { useCategoriesStore } from "@/stores/adcategory.ts";

const { t } = useI18n();

const categoryStore = useCategoriesStore();

defineProps<{
  disabled?: boolean;
  modelValue: AdCategory | undefined;
}>();

const emit = defineEmits<{
  "update:modelValue": [modelValue: AdCategory];
}>();

/**
 * Emits an update for the selected category when the selection changes.
 *
 * @param selection - The selected category (id is extracted for compatibility).
 */
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
