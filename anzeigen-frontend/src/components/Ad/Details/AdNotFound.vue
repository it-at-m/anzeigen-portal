<template>
  <v-container>
    <v-row>
      <v-col>
        <ad-display-sheet
          class="pa-2 d-flex align-center justify-center"
          style="height: 300px"
        >
          <v-icon
            class="w-100 h-100"
            style="background-color: #eeeeee"
            icon="mdi-file-cancel"
            color="accent"
            size="196"
          />
        </ad-display-sheet>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <ad-display-card>
          <template #title> Es konnte keine Anzeige gefunden werden </template>
          <template #text>
            <router-link
              :to="{
                name: ROUTES_BOARD,
                query: DEFAULT_BOARD_QUERIES,
              }"
            >
              Klicken Sie hier um zur Übersicht zurückzukehren.
            </router-link>
          </template>
        </ad-display-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import AdDisplaySheet from "@/components/common/AdDisplaySheet.vue";
import { DEFAULT_BOARD_QUERIES, ROUTES_BOARD } from "@/Constants";
</script>

<style scoped></style>
