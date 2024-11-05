<template>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="1400px"
  >
    <v-card>
      <v-card-title>Anzeige erstellen oder bearbeiten</v-card-title>
      <v-card-text>
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
              <v-stepper-window-item value="1">
                <v-form>
                  <v-text-field label="Titel" />
                  <v-select />
                  <v-radio-group inline>
                    <v-radio label="Biete" />
                    <v-radio label="Suche" />
                  </v-radio-group>
                  <v-textarea label="Beschreibung" />
                </v-form>
              </v-stepper-window-item>
              <v-stepper-window-item value="2">
                <v-form>
                  <v-file-input label="Foto auswählen" />
                  <v-text-field
                    prepend-inner-icon="mdi-link"
                    label="Link (optional)"
                  />
                  <v-file-input
                    label="Optionale Anhänge"
                    multiple
                  />
                </v-form>
              </v-stepper-window-item>
              <v-stepper-window-item value="3">
                <v-form>
                  <v-number-input
                    label="Preis"
                    control-variant="split"
                  />
                  <v-radio-group inline>
                    <v-radio label="VHB" />
                    <v-radio label="Festpreis" />
                    <v-radio label="zu verschenken" />
                  </v-radio-group>
                  <v-date-input label="Ablaufdatum" />
                  <v-text-field label="Telefonnummer" />
                  <v-text-field label="E-Mail" />
                  <ad-agb-accept />
                </v-form>
              </v-stepper-window-item>
            </v-stepper-window>
            <v-stepper-actions
              @click:prev="prev"
              @click:next="next"
            >
            </v-stepper-actions>
          </template>
        </v-stepper>
      </v-card-text>
      <v-card-actions>
        <v-btn
          prepend-icon="mdi-window-close"
          variant="outlined"
          @click="close"
          >Abbrechen</v-btn
        >
        <v-btn
          variant="elevated"
          color="accent"
          prepend-icon="mdi-content-save-outline"
        >
          Erstellen / Speichern
        </v-btn>
        <v-btn
          variant="elevated"
          color="error"
          prepend-icon="mdi-trash-can-outline"
          >Löschen</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { useEventBus } from "@vueuse/core";
import { ref } from "vue";
import { VDateInput, VNumberInput } from "vuetify/labs/components";

import AdAgbAccept from "@/components/Ad/Edit/AdAgbAccept.vue";
import { EV_EDIT_AD_DIALOG } from "@/Constants";

const dialog = ref(false);

const dialogBus = useEventBus<boolean>(EV_EDIT_AD_DIALOG);

dialogBus.on((event: boolean) => (dialog.value = event));

const close = () => (dialog.value = false);
</script>

<style scoped></style>
