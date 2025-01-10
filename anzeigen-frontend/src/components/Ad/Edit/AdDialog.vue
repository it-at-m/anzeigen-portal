<template>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="900px"
  >
    <v-card :disabled="loading">
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
              <common-ad-information
                :disabled="disabledInputs"
                :title="adTo?.title"
                :category="adTo?.adCategory"
                :ad-type="adTo?.adType"
                :description="adTo?.description"
                :price="adTo?.price"
              />
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
          :disabled="!form"
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
          :loading="deactivateAdLoading"
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

import { isDefined } from "@vueuse/core";
import { computed, ref } from "vue";

import CommonAdInformation from "@/components/Ad/Edit/CommonAdInformation.vue";
import OptionalAdInformation from "@/components/Ad/Edit/OptionalAdInformation.vue";
import SellerAdInformation from "@/components/Ad/Edit/SellerAdInformation.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import YesNoDialog from "@/components/common/YesNoDialog.vue";
import {
  useCreateAd,
  useDeactivateAd,
  useUpdateAd,
} from "@/composables/api/useAdApi";
import {
  useDialogEventBus,
  useUpdateAdListEventBus,
} from "@/composables/useEventBus";

const updateAdListEventBus = useUpdateAdListEventBus();

const dialog = ref<boolean>(false);

const adTo = ref<AdTO>();

const disabledInputs = ref<boolean>(false);

const dialogBus = useDialogEventBus();

const form = ref<boolean>(false);

const deleteDialog = ref<boolean>(false);

dialogBus.on((event: AdTO) => {
  dialog.value = true;
  adTo.value = event;
});

const { loading: deactivateAdLoading, call: deactivateAd } = useDeactivateAd();

const { loading: createAdLoading, call: createAd } = useCreateAd();

const { loading: updateAdLoading, call: updateAd } = useUpdateAd();

const loading = computed(() => createAdLoading.value || updateAdLoading.value);

const isAdCreate = computed(() => !isDefined(adTo));

const confirmedDeletion = async () => {
  if (adTo.value?.id) {
    await deactivateAd({ id: adTo.value.id });
  }
  deleteDialog.value = false;
  dialog.value = false;
};

const writeAd = async () => {
  if (isAdCreate.value) {
    await createAd({});
  } else {
    await updateAd({ id: adTo.value?.id, adTO: adTo.value });
  }

  close();
};

const close = () => {
  updateAdListEventBus.emit();
  dialog.value = false;
};
</script>

<style scoped></style>
