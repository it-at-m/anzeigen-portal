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
          <div
            class="h-100 d-flex align-content-center"
            :class="{ inactive: !adTo.active }"
          >
            <router-link
              :to="{
                name: ROUTES_AD,
                query: { id: adTo.id },
              }"
              class="w-100 remove-a-tag-styling"
            >
              <v-img
                v-if="adTo.imagePreviewBase64"
                rounded
                cover
                max-height="200"
                :src="PREVIEW_IMAGE_FILE_URI_PREFIX + adTo.imagePreviewBase64"
                class="cursor-pointer"
              />
              <v-icon
                v-else
                class="w-100 h-100 rounded"
                style="background-color: #eeeeee"
                icon="mdi-camera"
                color="accent"
                size="164"
              />
            </router-link>
          </div>
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
                :sm="canEdit ? 7 : 12"
                :md="canEdit ? 8 : 12"
                class="pa-0"
                :class="{ inactive: !adTo.active }"
              >
                <router-link
                  :to="{
                    name: ROUTES_AD,
                    query: { id: adTo.id },
                  }"
                  class="w-100 remove-a-tag-styling"
                >
                  <p class="text-h5 text-truncate">
                    {{ adTo.title }}
                  </p>
                </router-link>
              </v-col>
              <v-col
                v-if="canEdit"
                cols="6"
                sm="5"
                md="4"
                class="d-flex justify-end"
              >
                <ad-edit-button
                  is-edit
                  @click="clickedEdit"
                />
              </v-col>
            </v-row>
            <v-row
              class="w-100"
              align="start"
            >
              <p
                class="two-line-clamp"
                :class="{ inactive: !adTo.active }"
              >
                {{ sanitizedDescription }}
              </p>
            </v-row>
            <v-row
              class="w-100"
              align="start"
            >
              <ad-price
                :price="adTo.price!"
                :class="{ inactive: !adTo.active }"
              />
            </v-row>
            <v-row
              class="w-100"
              align="end"
              justify="space-between"
            >
              <v-col class="pa-0 pb-2 d-flex ga-2">
                <ad-art-chip
                  :ad-type="adTo.adType"
                  :class="{ inactive: !adTo.active }"
                />
                <ad-view-count-chip
                  :views="adTo.views!"
                  :class="{ inactive: !adTo.active }"
                />
                <ad-category-chip
                  :category="adTo.adCategory!"
                  :class="{ inactive: !adTo.active }"
                />
              </v-col>
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

import { computed, toRef } from "vue";

import { AdTOFromJSONTyped, AdTOToJSONTyped } from "@/api/swbrett";
import AdArtChip from "@/components/Ad/list/AdArtChip.vue";
import AdCategoryChip from "@/components/Ad/list/AdCategoryChip.vue";
import AdEditButton from "@/components/Ad/list/AdEditButton.vue";
import AdPrice from "@/components/Ad/list/AdPrice.vue";
import AdViewCountChip from "@/components/Ad/list/AdViewCountChip.vue";
import { useDialogEventBus } from "@/composables/useEventBus.ts";
import { useSanitizedHtml } from "@/composables/useSanitizedHtml.ts";
import { PREVIEW_IMAGE_FILE_URI_PREFIX, ROUTES_AD } from "@/Constants.ts";
import { useUserStore } from "@/stores/user.ts";

const userStore = useUserStore();

const dialogBus = useDialogEventBus();

const { adTo } = defineProps<{
  adTo: DeepReadonly<AdTO>;
}>();

const sanitizedDescription = useSanitizedHtml(toRef(adTo.description || ""));

/**
 * Computes whether the ad can be edited by the current user, either belongs to him, or user is admin.
 */
const canEdit = computed(
  () => adTo.swbUser?.id === userStore.userID || userStore.isAdmin
);

/**
 * Emits an event to edit the ad when clicked.
 */
const clickedEdit = () => {
  dialogBus.emit(AdTOFromJSONTyped(AdTOToJSONTyped(adTo as AdTO), false));
};
</script>

<style scoped>
.inactive {
  opacity: 60%;
}

.two-line-clamp {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2; /* Number of lines to be displayed */
  overflow: hidden;
}

.remove-a-tag-styling {
  text-decoration: none;
  color: inherit;
}
</style>
