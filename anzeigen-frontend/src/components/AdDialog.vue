<template>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="900px"
  >
    <v-card :loading="loading">
      <v-card-title>
        <v-container class="mx-0 ad-max-width">
          <v-row>
            <p v-if="isAdCreate">{{ t("ad.adCreate") }}</p>
            <p v-else>{{ t("ad.adEdit") }}</p>
          </v-row>
        </v-container>
      </v-card-title>
      <v-card-text :disabled="loading">
        <v-form v-model="form">
          <ad-display-card>
            <template #subtitle>
              {{ t("adDialog.generalInformation") }}
            </template>
            <template #text>
              <common-ad-information
                v-model="adTo"
                :disabled="loading"
              />
            </template>
          </ad-display-card>
          <v-divider />
          <ad-display-card>
            <template #subtitle>
              {{ t("adDialog.additionalInformation") }}
            </template>
            <template #text>
              <optional-ad-information
                v-model="adTo"
                :disabled="loading"
              />
            </template>
          </ad-display-card>
          <v-divider />
          <ad-display-card>
            <template #subtitle>
              {{ t("adDialog.sellerInformation") }}
            </template>
            <template #text>
              <seller-ad-information
                v-model="adTo"
                :disabled="loading"
              />
              <ad-agb-accept
                :disabled="loading"
                :model-value="!isAdCreate"
              />
            </template>
          </ad-display-card>
        </v-form>
      </v-card-text>
      <v-card-actions class="px-4 mb-1">
        <yes-no-dialog
          v-if="!isAdCreate && adTo.active"
          :model-value="deleteDialog"
          :dialogtitle="t(`adDialog.deleteDialog.title`)"
          :dialogtext="t(`adDialog.deleteDialog.text`)"
          @yes="confirmedDeletion"
          @no="deleteDialog = false"
        >
          <template #default>
            <v-btn
              variant="flat"
              color="error"
              prepend-icon="mdi-trash-can-outline"
              :disabled="loading"
              @click="deleteDialog = true"
            >
              {{ t("common.delete") }}
            </v-btn>
          </template>
        </yes-no-dialog>
        <v-spacer />
        <v-btn
          prepend-icon="mdi-window-close"
          variant="outlined"
          :text="t(`common.cancel`)"
          :disabled="loading"
          @click="close"
        />
        <v-btn
          :disabled="!form || loading"
          variant="elevated"
          color="accent"
          prepend-icon="mdi-content-save-outline"
          @click="writeAd"
        >
          <p v-if="isAdCreate">{{ t("common.create") }}</p>
          <p v-else>{{ t("common.save") }}</p>
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { ref } from "vue";
import { useI18n } from "vue-i18n";

import AdAgbAccept from "@/components/Ad/dialog/AdAgbAccept.vue";
import CommonAdInformation from "@/components/Ad/dialog/CommonAdInformation.vue";
import OptionalAdInformation from "@/components/Ad/dialog/OptionalAdInformation.vue";
import SellerAdInformation from "@/components/Ad/dialog/SellerAdInformation.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import YesNoDialog from "@/components/common/YesNoDialog.vue";
import { useDialogEventBus } from "@/composables/useEventBus.ts";

const { t } = useI18n();

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

/**
 * Listens for ad-related events and opens the dialog with the ad data. This a created ad or an empty ad.
 */
dialogBus.on((event: AdTO) => {
  isAdCreate.value = !event.id;
  dialog.value = true;
  adTo.value = event;
});

/**
 * Handles the confirmation of ad deletion.
 * Emits the "deactivateAd" event with the ad ID and closes the delete dialog.
 */
const confirmedDeletion = async () => {
  if (adTo.value?.id) {
    emit("deactivateAd", adTo.value?.id);
  }
  deleteDialog.value = false;
};

/**
 * Handles the creation or updating of an ad.
 * Emits the appropriate event based on whether it's a new ad or an existing one.
 */
const writeAd = async () => {
  if (adTo.value) {
    // eslint-disable-next-line @typescript-eslint/no-unused-expressions
    isAdCreate.value
      ? emit("createAd", adTo.value)
      : emit("updateAd", adTo.value);
  }
};

/**
 * Closes the ad dialog.
 */
const close = () => {
  dialog.value = false;
};
</script>

<style scoped></style>
