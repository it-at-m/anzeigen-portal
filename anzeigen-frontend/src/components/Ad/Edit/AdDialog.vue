<template>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="1400px"
  >
    <v-card>
      <v-card-title>
        <v-container class="mx-0 ad-max-width">
          <v-row>
            <p>Anzeige erstellen oder bearbeiten</p>
            <v-spacer />
            <v-btn
              prepend-icon="mdi-window-close"
              variant="outlined"
              text="Abbrechen"
              @click="close"
            />
          </v-row>
        </v-container>
      </v-card-title>
      <v-card-text>
        <ad-edit-stepper />
      </v-card-text>
      <v-card-actions>
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
        >
          Löschen
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { useEventBus } from "@vueuse/core";
import { ref } from "vue";

import AdEditStepper from "@/components/Ad/Edit/AdEditStepper.vue";
import { EV_EDIT_AD_DIALOG } from "@/Constants";

const dialog = ref(false);

const dialogBus = useEventBus<boolean>(EV_EDIT_AD_DIALOG);

dialogBus.on((event: boolean) => (dialog.value = event));

const close = () => (dialog.value = false);
</script>

<style scoped></style>
