<template>
  <v-stepper>
    <template #default="{ prev, next }">
      <v-stepper-header>
        <v-stepper-item
          title="Allgemeine Informationen"
          value="1"
        />
        <v-stepper-item
          title="Optionale Informationen"
          value="2"
        />
        <v-stepper-item
          title="Verkäufer Informationen"
          subtitle="Optional"
          value="3"
        />
      </v-stepper-header>
      <v-stepper-window>
        <v-form>
          <v-stepper-window-item value="1">
            <v-text-field label="Titel" />
            <v-select />
            <v-radio-group inline>
              <v-radio label="Biete" />
              <v-radio label="Suche" />
            </v-radio-group>
            <v-textarea label="Beschreibung" />
            <v-number-input
              label="Preis"
              control-variant="split"
            />
            <v-radio-group inline>
              <v-radio label="VHB" />
              <v-radio label="Festpreis" />
              <v-radio label="zu verschenken" />
            </v-radio-group>
          </v-stepper-window-item>
          <v-stepper-window-item value="2">
            <v-file-input label="Foto auswählen" />
            <v-text-field
              prepend-inner-icon="mdi-link"
              label="Link (optional)"
            />
            <v-file-input
              label="Optionale Anhänge"
              multiple
            />
          </v-stepper-window-item>
          <v-stepper-window-item value="3">
            <v-date-input label="Ablaufdatum" />
            <v-text-field label="Telefonnummer" />
            <v-text-field label="E-Mail" />
            <ad-agb-accept />
          </v-stepper-window-item>
        </v-form>
      </v-stepper-window>
      <v-stepper-actions>
        <template #next>
          <v-btn
            text="Nächster"
            @click="next"
          />
        </template>
        <template #prev>
          <v-btn
            text="Vorheriger"
            @click="prev"
          />
        </template>
      </v-stepper-actions>
    </template>
  </v-stepper>
</template>

<script setup lang="ts">
import { VDateInput, VNumberInput } from "vuetify/labs/components";

import AdAgbAccept from "@/components/Ad/Edit/AdAgbAccept.vue";
</script>

<style scoped></style>
