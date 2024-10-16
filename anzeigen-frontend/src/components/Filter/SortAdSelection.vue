<template>
  <card>
    <template #title>Sortieren</template>
    <template #text>
      <v-select
        v-model="sortingCriteria"
        color="accent"
        :items="selections"
        variant="outlined"
      />
      <v-radio-group v-model="sortingOrder">
        <v-radio
          color="accent"
          label="Aufsteigend"
          value="asc"
        />
        <v-radio
          color="accent"
          label="Absteigend"
          value="desc"
        />
      </v-radio-group>
    </template>
  </card>
</template>

<script setup lang="ts">
import { ref } from "vue";

import Card from "@/components/common/Card.vue";

type Order = "asc" | "desc";

type Criteria = "titel" | "type" | "price" | "creationDate" | "expirationDate";

const selections = [
  { title: "Titel (alphabetisch)", value: "title" },
  { title: "Art (alphabetisch)", value: "type" },
  { title: "Preis", value: "price" },
  { title: "Erstellungsdatum", value: "creationDate" },
  { title: "Ablaufdatum", value: "expirationDate" },
];

const sortingCriteria = ref<Criteria>("creationDate");

const sortingOrder = ref<Order>("asc");
</script>

<style scoped></style>
