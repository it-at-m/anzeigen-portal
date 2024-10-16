<template>
  <div>
    <v-icon
      v-if="icon"
      color="accent"
      :icon="'mdi-' + icon"
      class="mr-1"
    />
    <a :href="href">{{ label }}</a>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  label: string;
  href?: string;
  icon?: string;
}>();
</script>

<style scoped></style>
