<template>
  <v-btn
    color="accent"
    :prepend-icon="computedIcon"
    :text="computedText"
    @click="click"
  />
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useI18n } from "vue-i18n";

const { t } = useI18n();

const emit = defineEmits<{
  click: [];
}>();

const { isEdit = false } = defineProps<{
  isEdit?: boolean;
}>();

/**
 * Computes the text based on whether the component is in edit mode or not.
 */
const computedText = computed(() =>
  isEdit ? t("ad.adEdit") : t("ad.adCreate")
);

/**
 * Computes the icon based on whether the component is in edit mode or not.
 */
const computedIcon = computed(() => (isEdit ? "mdi-pencil" : "mdi-plus"));

/**
 * Emits a "click" event when the action is triggered.
 */
const click = () => emit("click");
</script>

<style scoped></style>
