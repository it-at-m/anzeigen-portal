<template>
  <v-sheet
    rounded
    width="100%"
  >
    <v-container class="w-100 mx-0 ad-max-width">
      <v-row no-gutters>
        <v-col
          cols="12"
          sm="3"
        >
          <v-img
            cover
            max-height="200"
            src="https://picsum.photos/300"
            class="cursor-pointer"
            @click="routeTo"
          />
        </v-col>
        <v-col
          cols="12"
          sm="9"
        >
          <v-container class="d-flex flex-column py-1 fill-height">
            <v-row
              class="w-100"
              align="start"
              justify="space-between"
              no-gutters
            >
              <v-col
                cols="12"
                sm="7"
                md="8"
                class="cursor-pointer"
                @click="routeTo"
              >
                <p class="text-h5 text-truncate">
                  {{ adTo.title }}
                </p>
              </v-col>
              <v-col
                cols="6"
                sm="5"
                md="4"
                class="d-flex justify-end"
              >
                <ad-edit-button
                  v-if="belongsToCurrentUser"
                  is-edit
                  @click="clickedEdit"
                />
              </v-col>
            </v-row>
            <v-row
              class="w-100"
              align="start"
            >
              <p class="two-line-clamp">{{ adTo.description }}</p>
            </v-row>
            <v-row
              class="w-100"
              align="start"
            >
              <ad-price :price="adTo.price!" />
            </v-row>
            <v-row
              class="w-100"
              align="end"
              justify="space-between"
            >
              <v-col class="pa-0 pb-2 d-flex ga-2">
                <ad-art-chip :is-offer="isOffer" />
                <ad-view-count-chip :views="adTo.views!" />
                <ad-category-chip :category="adTo.adCategory!" />
              </v-col>
              <v-col cols="2"> </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </v-container>
  </v-sheet>
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";
import type { DeepReadonly } from "vue";

import { computed } from "vue";
import { useRouter } from "vue-router";

import { AdTOFromJSONTyped, AdTOToJSONTyped } from "@/api/swbrett";
import AdArtChip from "@/components/Ad/AdArtChip.vue";
import AdCategoryChip from "@/components/Ad/AdCategoryChip.vue";
import AdEditButton from "@/components/Ad/AdEditButton.vue";
import AdPrice from "@/components/Ad/AdPrice.vue";
import AdViewCountChip from "@/components/Ad/AdViewCountChip.vue";
import { useDialogEventBus } from "@/composables/useEventBus";
import { useUserStore } from "@/stores/user";

const router = useRouter();

const userStore = useUserStore();

const dialogBus = useDialogEventBus();

const { adTo } = defineProps<{
  adTo: DeepReadonly<AdTO>;
}>();

const isOffer = computed(() => adTo.adType === "OFFER");

const belongsToCurrentUser = computed(() => {
  return adTo.swbUser?.id === userStore.userID;
});

/**
 * Route to a specific ad
 * @param id of the ad
 */
const routeTo = () => {
  router.push({ path: "/ad", query: { id: adTo.id } });
};

const clickedEdit = () => {
  dialogBus.emit(AdTOFromJSONTyped(AdTOToJSONTyped(adTo as AdTO), false));
};
</script>

<style scoped>
.two-line-clamp {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2; /* Anzahl der Zeilen, die angezeigt werden sollen */
  overflow: hidden;
}
</style>
