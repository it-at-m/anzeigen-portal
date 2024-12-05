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
import type {
  AdCategory,
  AdTO,
  SwbFileTO,
  SwbImageTO,
  SwbUserTO,
} from "@/api/swbrett";

import { useEventBus } from "@vueuse/core";
import { computed, ref } from "vue";

import CommonAdInformation from "@/components/Ad/Edit/CommonAdInformation.vue";
import OptionalAdInformation from "@/components/Ad/Edit/OptionalAdInformation.vue";
import SellerAdInformation from "@/components/Ad/Edit/SellerAdInformation.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import {
  useCreateAd,
  useDeleteAd,
  useUpdateAd,
} from "@/composables/api/useAdApi";
import { useCreateUser } from "@/composables/api/useUserApi";
import { EV_EDIT_AD_DIALOG } from "@/Constants";

const {
  data: updateAdData,
  call: updateAdCall,
  loading: updateAdLoading,
  error: updateAdError,
} = useUpdateAd();
const {
  call: deleteAdCall,
  loading: deleteAdLoading,
  error: deleteAdError,
} = useDeleteAd();
const {
  data: createAdData,
  call: createAdCall,
  loading: createAdLoading,
  error: createAdError,
} = useCreateAd();

const { data, call, loading, error } = useCreateUser();

const dialog = ref<boolean>(false);

const adTo = ref<AdTO>();

const disabledInputs = ref<boolean>(false);

const dialogBus = useEventBus<AdTO>(EV_EDIT_AD_DIALOG);

const form = ref<boolean>();

dialogBus.on((event: AdTO) => {
  dialog.value = true;
  adTo.value = event;
});

const exampleAd: AdTO = {
  id: 1,
  swbUser: {
    id: 123,
    name: "John Doe",
    email: "johndoe@example.com",
  } as SwbUserTO,
  adCategory: {
    id: 10,
    name: "Electronics",
  } as AdCategory,
  adType: "SEEK", // Beispielwert aus AdTOAdTypeEnum
  active: true,
  title: "Smartphone for Sale",
  description: "A lightly used smartphone in excellent condition.",
  price: 250,
  phone: "+123456789",
  email: "seller@example.com",
  link: "https://example.com/listing/1",
  creationDateTime: new Date("2024-01-01T12:00:00Z"),
  expiryDate: new Date("2024-12-31T23:59:59Z"),
  imagePreviewBase64: "data:image/png;base64,iVBORw0KGgoAAAANS...",
  adImg: {
    id: 101,
    fileName: "smartphone.png",
    url: "https://example.com/images/smartphone.png",
  } as SwbImageTO,
  adFiles: [
    {
      id: 201,
      fileName: "manual.pdf",
      url: "https://example.com/files/manual.pdf",
    } as SwbFileTO,
  ],
  views: 150,
};

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

const isAdCreate = computed(() => adTo.value === undefined);
</script>

<style scoped></style>
