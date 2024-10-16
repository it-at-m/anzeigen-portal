<template>
  <card>
    <template #title>Nach Kategorie filtern</template>
    <template #text>
      <v-tabs
        v-model="selectedCategory"
        direction="vertical"
      >
        <v-tab>
          <template #prepend>
            <v-icon
              color="accent"
              icon="mdi-check-all"
            />
          </template>
          Alle</v-tab
        >
        <v-tab
          v-for="category in categories"
          :key="category.id"
          :value="category.id"
        >
          <template #prepend>
            <v-icon
              color="accent"
              icon="mdi-card-multiple"
            />
          </template>
          {{ category.name }}
        </v-tab>
      </v-tabs></template
    >
  </card>
</template>

<script setup lang="ts">
import { ref } from "vue";

import Card from "@/components/common/Card.vue";

const selectedCategory = ref();

const categories = [
  { id: 5, name: "Sonstiges", standard: true },
  { id: 1, name: "Möbel, Ausstattung und Garten", standard: false },
  { id: 2, name: "Bücher, Filme und Musik", standard: false },
  { id: 4, name: "Freizeit, Sport und Hobby", standard: false },
  { id: 3, name: "Immobilien und Wohnungen", standard: false },
  { id: 6, name: "Auto und Motorrad", standard: false },
  { id: 7, name: "Dienstleistungen", standard: false },
  { id: 8, name: "Eintrittskarten und Tickets", standard: false },
  { id: 9, name: "Elektronik", standard: false },
  { id: 10, name: "Familie und Kind", standard: false },
  { id: 12, name: "Kleidung", standard: false },
  { id: 13, name: "Lost and Found (Fundsachen)", standard: false },
  { id: 14, name: "Tausche Hilfe gegen Hilfe", standard: false },
];
</script>

<style scoped></style>
