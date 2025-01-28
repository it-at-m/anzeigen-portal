<template>
  <div>
    <h2>Administrationsseite</h2>
    <edit-categories class="my-4" />
    <general-settings />
  </div>
</template>

<script setup lang="ts">
import EditCategories from "@/components/admin/EditCategories.vue";
import GeneralSettings from "@/components/admin/GeneralSettings.vue";
</script>

<style scoped></style>
