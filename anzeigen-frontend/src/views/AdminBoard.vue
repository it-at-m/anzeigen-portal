<template>
  <div>
    <edit-categories class="my-4" />
    <general-settings class="my-4" />
    <version-display />
  </div>
</template>

<script setup lang="ts">
import EditCategories from "@/components/admin/EditCategories.vue";
import GeneralSettings from "@/components/admin/GeneralSettings.vue";
import VersionDisplay from "@/components/admin/VersionDisplay.vue";
</script>

<style scoped></style>
