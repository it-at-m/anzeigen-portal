<template>
  <v-container>
    <v-row>
      <v-col>
        <ad-display-sheet
          class="pa-2 d-flex justify-center"
          style="height: 300px"
        >
          <v-img
            v-if="adDetails.imagePreviewBase64"
            max-height="300"
            :src="adDetails.imagePreviewBase64"
          />
          <v-icon
            v-else
            class="w-100 h-100"
            style="background-color: #eeeeee"
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
                    label="https://weissesbrett.muenchen.de/board/main/7430"
                    href="https://weissesbrett.muenchen.de/board/main/7430"
                  />
                </v-col>
              </v-row>
            </v-container>
          </template>
        </ad-display-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col v-if="adDetails.adFiles?.length !== 0">
        <ad-display-card>
          <template #subtitle>Weitere Informationen</template>
          <template #text>
            <icon-text
              v-for="i in adDetails.adFiles"
              :key="i.id"
              :label="i.name!"
              icon="link-variant"
            />
          </template>
        </ad-display-card>
      </v-col>
      <v-col>
        <ad-display-card>
          <template #subtitle>Kontakt</template>
          <template #text>
            <icon-text
              v-if="adDetails.phone"
              icon="phone"
              :label="adDetails.phone"
            />
            <icon-text
              v-if="adDetails.email"
              class="my-2"
              icon="email"
              :label="adDetails.email"
            />
            <icon-text
              v-if="adDetails.swbUser?.id"
              icon="account"
              link
              class="cursor-pointer"
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
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import AdDisplaySheet from "@/components/common/AdDisplaySheet.vue";
import IconText from "@/components/common/IconText.vue";
import { DATE_DISPLAY_FORMAT } from "@/Constants";
import router from "@/plugins/router";

const { adDetails } = defineProps<{
  adDetails: Readonly<AdTO>;
}>();

const adType = computed(() =>
  adDetails.adType === "SEEK" ? "Suche" : "Biete"
);

const routeToUser = (id: number) => {
  router.push({
    path: "/board",
    query: {
      userId: id,
    },
  });
};
</script>

<style scoped></style>
