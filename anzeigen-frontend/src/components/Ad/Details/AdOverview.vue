<template>
  <v-container>
    <v-row>
      <v-col>
        <ad-display-sheet
          class="pa-2 d-flex justify-center"
          style="max-height: 500px"
        >
          <ad-image-display
            v-if="adDetails.imagePreviewBase64"
            :ad-details="adDetails"
          />
          <v-icon
            v-else
            class="w-100 h-100 rounded image-background-color"
            icon="mdi-camera"
            color="accent"
            size="196"
          />
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
              v-if="adDetails.price"
              :price="adDetails.price"
            />
          </template>
          <template #text>
            {{ adDetails.description }}
          </template>
        </ad-display-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <ad-display-card>
          <template #subtitle>Details</template>
          <template #text>
            <v-container class="pl-0">
              <v-row>
                <v-col class="py-0"> Art </v-col>
                <v-col class="py-0"> {{ adType }} </v-col>
              </v-row>
              <v-row>
                <v-col class="py-0"> Kategorie </v-col>
                <v-col class="py-0">
                  {{ adDetails.adCategory?.name }}
                </v-col>
              </v-row>
              <v-row>
                <v-col class="py-0"> Erstellungsdatum </v-col>
                <v-col class="py-0">
                  {{
                    useDateFormat(
                      adDetails.creationDateTime,
                      DATE_DISPLAY_FORMAT
                    )
                  }}
                </v-col>
              </v-row>
              <v-row>
                <v-col class="py-0"> Ablaufdatum </v-col>
                <v-col class="py-0">
                  {{ useDateFormat(adDetails.expiryDate, DATE_DISPLAY_FORMAT) }}
                </v-col>
              </v-row>
              <v-row>
                <v-col class="py-0"> Aufrufe </v-col>
                <v-col class="py-0"> {{ adDetails.views }} </v-col>
              </v-row>
              <v-row>
                <v-col class="py-0"> Direkter Link </v-col>
                <v-col class="py-0">
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
        v-if="adDetails.adFiles?.length !== 0"
        cols="12"
        lg="6"
      >
        <ad-display-card
          :loading="getFileLoading"
          :disabled="getFileLoading"
        >
          <template #subtitle>Weitere Informationen</template>
          <template #text>
            <icon-text
              v-for="i in adDetails.adFiles"
              :key="i.id"
              class="mb-2 cursor-pointer"
              :label="i.name!"
              icon="link-variant"
              @click="downloadFile(i.id!)"
            />
          </template>
        </ad-display-card>
      </v-col>
      <v-col
        cols="12"
        lg="6"
      >
        <ad-display-card>
          <template #subtitle>Kontakt</template>
          <template #text>
            <icon-text
              v-if="adDetails.phone"
              class="mb-2"
              icon="phone"
              :label="adDetails.phone"
            />
            <icon-text
              v-if="adDetails.email"
              class="mb-2"
              icon="email"
              :label="adDetails.email"
            />
            <icon-text
              v-if="adDetails.swbUser?.id"
              icon="account"
              link
              class="cursor-pointer mb-2"
              label="weiter Anzeigen dieses Nutzers"
              @click="routeToUser(adDetails.swbUser.id!)"
            />
          </template>
        </ad-display-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { useDateFormat } from "@vueuse/shared";
import { computed } from "vue";

import AdPrice from "@/components/Ad/AdPrice.vue";
import AdImageDisplay from "@/components/Ad/Details/AdImageDisplay.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import AdDisplaySheet from "@/components/common/AdDisplaySheet.vue";
import IconText from "@/components/common/IconText.vue";
import { useGetFile } from "@/composables/api/useFilesApi";
import { DATE_DISPLAY_FORMAT } from "@/Constants";
import router from "@/plugins/router";

const { adDetails } = defineProps<{
  adDetails: Readonly<AdTO>;
}>();

const currentLink = computed(() => window.location.href);

const { call: getFile, data: fileData, loading: getFileLoading } = useGetFile();

/**
 * Computes the ad type, returning "Suche" for SEEK and "Biete" for other ad types.
 */
const adType = computed(() =>
  adDetails.adType === "SEEK" ? "Suche" : "Biete"
);

/**
 * Navigates to the user page with the specified user ID.
 * @param id - The ID of the user.
 */
const routeToUser = (id: number) => {
  router.push({
    path: "/board",
    query: {
      userId: id,
    },
  });
};

/**
 * Downloads a file based on the provided ID.
 * Retrieves the file, creates a Blob, and triggers a download.
 * @param id - The ID of the file to download.
 */
const downloadFile = async (id: number) => {
  await getFile({ id: id });

  if (fileData.value && fileData.value.fileBase64 && fileData.value.name) {
    const blob = new Blob([fileData.value?.fileBase64]);
    const fileURL = URL.createObjectURL(blob);
    const downloadLink = document.createElement("a");

    downloadLink.href = fileURL;
    downloadLink.download = fileData.value.name;
    document.body.appendChild(downloadLink);
    downloadLink.click();
  }
};
</script>

<style scoped>
.image-background-color {
  background-color: #eeeeee;
}
</style>
