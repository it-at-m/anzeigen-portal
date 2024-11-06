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

const emit = defineEmits<{
  click: [];
}>();

const { isEdit = false } = defineProps<{
  isEdit?: boolean;
}>();

const computedText = computed(() =>
  isEdit ? "Bearbeiten" : "Anzeige erstellen"
);

const computedIcon = computed(() => (isEdit ? "mdi-pencil" : "mdi-plus"));

const click = () => emit("click");
</script>

<style scoped></style>
