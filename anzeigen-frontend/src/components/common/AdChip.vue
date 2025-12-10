<template>
  <v-chip
    color="accent"
    variant="tonal"
  >
    <template #default>
      <span
        v-if="text"
        class="text-primary"
        >{{ text }}</span
      >
    </template>
    <template #prepend>
      <v-icon
        v-if="icon"
        color="primary"
        :icon="icon"
        class="mr-2"
      />
    </template>
  </v-chip>
</template>

<script setup lang="ts">
defineProps<{
  text?: string;
  icon?: string;
}>();
</script>

<style scoped></style>
