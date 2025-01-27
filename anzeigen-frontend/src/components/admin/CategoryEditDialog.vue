<template>
  <v-dialog>
    <template #default>
      <v-card>
        <template #title>Kategorie {{ data?.name }} editieren</template>
        <template #text>
          <v-text-field></v-text-field>
        </template>
      </v-card>
    </template>
  </v-dialog>
</template>

<script setup lang="ts">
import type { AdCategory } from "@/api/swbrett";

const modelValue = defineModel<boolean>();

const data = defineModel<AdCategory>("data");
</script>

<style scoped></style>
