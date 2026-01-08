<template></template>

<script setup lang="ts">
import { onMounted } from "vue";

const props = defineProps<{
  disabled?: boolean;
  modelValue: number;
}>();

const emit = defineEmits<{
  "update:modelValue": [modelValue: number];
}>();

onMounted(() => {
  if (props.modelValue !== 0) {
    emit("update:modelValue", 0);
  }
});
</script>

<style scoped></style>
