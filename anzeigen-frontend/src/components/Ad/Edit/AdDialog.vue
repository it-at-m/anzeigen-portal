<template>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="900px"
  >
    <v-card>
      <v-card-title>
        <v-container class="mx-0 ad-max-width">
          <v-row>
            <p v-if="isAdCreate">Anzeige erstellen</p>
            <p v-else>Anzeige bearbeiten</p>
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
        <v-form v-model="form">
          <ad-display-card>
            <template #subtitle> Allgemeine Informationen </template>
            <template #text>
              <common-ad-information :disabled="disabledInputs" />
            </template>
          </ad-display-card>
          <v-divider />
          <ad-display-card>
            <template #subtitle> Optionale Informationen </template>
            <template #text>
              <optional-ad-information :disabled="disabledInputs" />
            </template>
          </ad-display-card>
          <v-divider />
          <ad-display-card>
            <template #subtitle> Verkäufer Informationen </template>
            <template #text>
              <seller-ad-information :disabled="disabledInputs" />
            </template>
          </ad-display-card>
        </v-form>
      </v-card-text>
      <v-card-actions class="px-4">
        <v-btn
          variant="elevated"
          color="accent"
          prepend-icon="mdi-content-save-outline"
          @click="createAd"
        >
          <p v-if="isAdCreate">Erstellen</p>
          <p v-else>Speichern</p>
        </v-btn>
        <v-btn
          v-if="!isAdCreate"
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
import type { AdTO } from "@/api/swbrett";

import { isDefined } from "@vueuse/core";
import { computed, ref } from "vue";

import CommonAdInformation from "@/components/Ad/Edit/CommonAdInformation.vue";
import OptionalAdInformation from "@/components/Ad/Edit/OptionalAdInformation.vue";
import SellerAdInformation from "@/components/Ad/Edit/SellerAdInformation.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { useCreateUser } from "@/composables/api/useUserApi";
import { useDialogEventBus } from "@/composables/useEventBus";

const { call } = useCreateUser();

const dialog = ref<boolean>(false);

const adTo = ref<AdTO>();

const disabledInputs = ref<boolean>(false);

const dialogBus = useDialogEventBus();

const form = ref<boolean>();

dialogBus.on((event: AdTO) => {
  dialog.value = true;
  adTo.value = event;
});

const createAd = () => {
  call({
    swbUserTO: {
      displayName: "user",
      id: 4,
      lhmObjectId: "idontknow",
    },
  });
};

const close = () => (dialog.value = false);

const isAdCreate = computed(() => !isDefined(adTo));
</script>

<style scoped></style>
