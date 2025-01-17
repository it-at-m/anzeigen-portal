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
              :disabled="loading"
              @click="close"
            />
          </v-row>
        </v-container>
      </v-card-title>
      <v-card-text :disabled="loading">
        <v-form v-model="form">
          <ad-display-card>
            <template #title> Allgemeine Informationen </template>
            <template #text>
              <common-ad-information
                v-model="adTo"
                :disabled="loading"
              />
            </template>
          </ad-display-card>
          <v-divider />
          <ad-display-card>
            <template #subtitle> Optionale Informationen </template>
            <template #text>
              <optional-ad-information
                v-model="adTo"
                :disabled="loading"
              />
            </template>
          </ad-display-card>
          <v-divider />
          <ad-display-card>
            <template #subtitle> Verkäufer Informationen </template>
            <template #text>
              <seller-ad-information
                v-model="adTo"
                :disabled="loading"
              />
            </template>
          </ad-display-card>
        </v-form>
      </v-card-text>
      <v-card-actions class="px-4">
        <v-btn
          :disabled="!form || loading"
          variant="elevated"
          color="accent"
          prepend-icon="mdi-content-save-outline"
          @click="writeAd"
        >
          <p v-if="isAdCreate">Erstellen</p>
          <p v-else>Speichern</p>
        </v-btn>
        <yes-no-dialog
          v-if="!isAdCreate"
          :model-value="deleteDialog"
          dialogtitle="Anzeige löschen"
          dialogtext="Möchten Sie wirklich die Anzeige löschen?"
          @yes="confirmedDeletion"
          @no="deleteDialog = false"
        >
          <template #default>
            <v-btn
              variant="elevated"
              color="error"
              prepend-icon="mdi-trash-can-outline"
              :disabled="loading"
              @click="deleteDialog = true"
            >
              Löschen
            </v-btn>
          </template>
        </yes-no-dialog>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { ref } from "vue";

import CommonAdInformation from "@/components/Ad/Edit/CommonAdInformation.vue";
import OptionalAdInformation from "@/components/Ad/Edit/OptionalAdInformation.vue";
import SellerAdInformation from "@/components/Ad/Edit/SellerAdInformation.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import YesNoDialog from "@/components/common/YesNoDialog.vue";
import { useDialogEventBus } from "@/composables/useEventBus";

const dialog = defineModel<boolean>();

const adTo = ref<AdTO>({} as AdTO);

const dialogBus = useDialogEventBus();

const form = ref<boolean>(false);

const isAdCreate = ref<boolean>(false);

const emit = defineEmits<{
  updateAd: [AdTO];
  createAd: [AdTO];
  deactivateAd: [number];
}>();

defineProps<{
  loading: boolean;
}>();

const deleteDialog = ref<boolean>(false);

dialogBus.on((event: AdTO) => {
  isAdCreate.value = !event.id;
  dialog.value = true;
  adTo.value = event;
});

const confirmedDeletion = async () => {
  if (adTo.value?.id) {
    emit("deactivateAd", adTo.value?.id);
  }
  deleteDialog.value = false;
};

const writeAd = async () => {
  if (adTo.value) {
    // eslint-disable-next-line @typescript-eslint/no-unused-expressions
    isAdCreate.value
      ? emit("createAd", adTo.value)
      : emit("updateAd", adTo.value);
  }
};

const close = () => {
  dialog.value = false;
};
</script>

<style scoped></style>
