<template>
  <v-container>
    <v-row>
      <v-col>
        <ad-display-sheet class="pa-2 d-flex justify-center">
          <div
            class="bg-background w-100 h-100 d-flex justify-center align-center rounded"
          >
            <ad-image-display
              v-if="adDetails.imagePreviewBase64"
              :ad-details="adDetails"
            />
            <v-icon
              v-else
              class="w-100 h-100 rounded"
              :icon="mdiCamera"
              color="accent"
              size="196"
            />
          </div>
        </ad-display-sheet>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <ad-display-card>
          <template #title>
            {{ adDetails.title }}
          </template>
          <template #subtitle>
            <ad-price
              v-if="CONFIG.SHOW_PRICE && adDetails.price"
              :price="adDetails.price"
            />
          </template>
          <template #text>
            <div class="border rounded px-2 quill-content">
              <!-- eslint-disable-next-line vue/no-v-html -->
              <div v-html="adDetails.description" />
            </div>
          </template>
        </ad-display-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <ad-display-card>
          <template #subtitle>{{ t("adOverview.detailsCard.title") }}</template>
          <template #text>
            <v-container class="pl-0">
              <v-row density="compact">
                <v-col class="py-0">
                  {{ t("adOverview.detailsCard.type") }}
                </v-col>
                <v-col class="py-0">
                  {{ t(`ad.type.${adDetails.adType!.toLocaleLowerCase()}`) }}
                </v-col>
              </v-row>
              <v-row
                v-if="adDetails.adType === 'RENTAL'"
                density="compact"
              >
                <v-col class="py-0">
                  {{ t("adOverview.detailsCard.rentalDate") }}
                </v-col>
                <v-col class="py-0">
                  {{ useDateFormat(adDetails.rentalDate, DATE_DISPLAY_FORMAT) }}
                </v-col>
              </v-row>
              <v-row
                v-if="adDetails.condition"
                density="compact"
              >
                <v-col class="py-0">
                  {{ t("adConditionSelector.placeholder") }}
                </v-col>
                <v-col class="py-0">
                  {{
                    t(
                      `adConditionSelector.conditions.${adDetails.condition!.toLocaleLowerCase()}`
                    )
                  }}
                </v-col>
              </v-row>
              <v-row density="compact">
                <v-col class="py-0">
                  {{ t("adOverview.detailsCard.category") }}
                </v-col>
                <v-col class="py-0">
                  {{ adDetails.adCategory?.name }}
                </v-col>
              </v-row>
              <v-row density="compact">
                <v-col class="py-0">
                  {{ t("adOverview.detailsCard.creationDate") }}
                </v-col>
                <v-col class="py-0">
                  {{
                    useDateFormat(
                      adDetails.creationDateTime,
                      DATE_DISPLAY_FORMAT
                    )
                  }}
                </v-col>
              </v-row>
              <v-row density="compact">
                <v-col class="py-0">
                  {{ t("adOverview.detailsCard.expiryDate") }}
                </v-col>
                <v-col class="py-0">
                  {{ useDateFormat(adDetails.expiryDate, DATE_DISPLAY_FORMAT) }}
                </v-col>
              </v-row>
              <v-row density="compact">
                <v-col class="py-0">
                  {{ t("adOverview.detailsCard.views") }}
                </v-col>
                <v-col class="py-0"> {{ adDetails.views }} </v-col>
              </v-row>
              <v-row
                v-if="adDetails.link"
                density="compact"
              >
                <v-col class="py-0">
                  {{ t("adOverview.detailsCard.additionalLink") }}
                </v-col>
                <v-col class="py-0 text-truncate">
                  <icon-text
                    :label="adDetails.link"
                    :href="adDetails.link"
                  />
                </v-col>
              </v-row>
              <v-row density="compact">
                <v-col class="py-0">
                  {{ t("adOverview.detailsCard.directLink") }}
                </v-col>
                <v-col class="py-0 text-truncate">
                  <icon-text
                    :label="currentLink"
                    :href="currentLink"
                  />
                </v-col>
              </v-row>
            </v-container>
          </template>
        </ad-display-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        lg="6"
      >
        <ad-display-card>
          <template #subtitle>{{ t("adOverview.contactCard.title") }}</template>
          <template #text>
            <icon-text
              v-if="adDetails.address?.street && adDetails.address?.postalCode"
              class="mb-2"
              :icon="mdiMapMarkerOutline"
              :label="combinedAddress"
            />
            <icon-text
              v-if="adDetails.phone"
              class="mb-2"
              :icon="mdiPhone"
              :label="adDetails.phone"
            />
            <icon-text
              v-if="adDetails.email"
              class="mb-2"
              :icon="mdiEmail"
              :label="adDetails.email"
            />
            <icon-text
              v-if="adUser && adUser.displayName"
              class="mb-2"
              :icon="mdiAccountTag"
              :label="adUser.displayName"
            />
            <router-link
              :to="{
                name: ROUTES_BOARD,
                query: { userId: adDetails.swbUser?.id },
              }"
            >
              <icon-text
                v-if="adDetails.swbUser?.id"
                :icon="mdiAccount"
                link
                :label="t('adOverview.contactCard.account')"
              />
            </router-link>
          </template>
        </ad-display-card>
      </v-col>
      <v-col
        v-if="adDetails.adFiles?.length !== 0"
        cols="12"
        lg="6"
      >
        <ad-display-card>
          <template #subtitle>
            {{ t("adOverview.additionalInformationCard.title") }}
          </template>
          <template #text>
            <icon-text
              v-for="i in adDetails.adFiles"
              :key="i.id"
              class="mb-2 cursor-pointer"
              :label="i.name!"
              :icon="mdiLinkVariant"
              @click="downloadFile(i.id!)"
            />
          </template>
        </ad-display-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import type { AdTO, SwbUserTO } from "@/apis/generated/anzeigen-backend";

import {
  mdiAccount,
  mdiAccountTag,
  mdiCamera,
  mdiEmail,
  mdiLinkVariant,
  mdiMapMarkerOutline,
  mdiPhone,
} from "@mdi/js";
import { useDateFormat } from "@vueuse/shared";
import { computed } from "vue";
import { useI18n } from "vue-i18n";

import AdImageDisplay from "@/components/Ad/details/AdImageDisplay.vue";
import AdPrice from "@/components/Ad/list/AdPrice.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import AdDisplaySheet from "@/components/common/AdDisplaySheet.vue";
import IconText from "@/components/common/IconText.vue";
import { useDownloadFile } from "@/composables/useDownloadFile.ts";
import { CONFIG, DATE_DISPLAY_FORMAT, ROUTES_BOARD } from "@/constants.ts";

const { t } = useI18n();

const downloadFile = useDownloadFile();

const { adDetails } = defineProps<{
  adDetails: Readonly<AdTO>;
  adUser: Readonly<SwbUserTO> | null;
}>();

const currentLink = computed(() => window.location.href);

const combinedAddress = computed(
  () => adDetails.address?.street + ", " + adDetails.address?.postalCode
);
</script>
