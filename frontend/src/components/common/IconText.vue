<template>
  <div>
    <v-icon
      v-if="icon"
      color="accent"
      :icon="'mdi-' + icon"
      class="mr-1"
    />
    <a
      :href="href"
      :class="{ 'link-text': link, 'text-decoration-underline': link }"
    >
      {{ label }}
    </a>
  </div>
</template>

<script setup lang="ts">
const { link = false } = defineProps<{
  link?: boolean;
  label: string;
  href?: string;
  icon?: string;
}>();
</script>

<style scoped>
.link-text {
  color: #0066d3;
}
</style>
