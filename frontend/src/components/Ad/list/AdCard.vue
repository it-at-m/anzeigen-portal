<template>
  <v-sheet
    rounded
    width="100%"
  >
    <v-container class="w-100 mx-0 ad-max-width">
      <v-row>
        <v-col
          cols="12"
          sm="3"
        >
          <router-link
            :to="{
              name: ROUTES_AD,
              params: { id: adTo.id! },
            }"
            class="w-100 h-100 d-flex align-center justify-center rounded bg-grey-lighten-3 overflow-hidden remove-a-tag-styling"
            :class="{ inactive: !adTo.active }"
          >
            <v-img
              v-if="adTo.imagePreviewBase64"
              class="w-100 h-100 cursor-pointer"
              max-height="164"
              cover
              :src="PREVIEW_IMAGE_FILE_URI_PREFIX + adTo.imagePreviewBase64"
            />
            <v-icon
              v-else
              :icon="mdiCamera"
              color="accent"
              size="164"
            />
          </router-link>
        </v-col>
        <v-col
          cols="12"
          sm="9"
          class="d-flex flex-column"
        >
          <div class="d-flex justify-space-between align-start w-100">
            <router-link
              :to="{
                name: ROUTES_AD,
                params: { id: adTo.id! },
              }"
              class="w-100 remove-a-tag-styling text-headline-small text-truncate pr-4"
              :class="{ inactive: !adTo.active }"
            >
              {{ adTo.title }}
            </router-link>

            <ad-edit-button
              v-if="canEdit"
              is-edit
              class="flex-shrink-0"
              @click="clickedEdit"
            />
          </div>
          <p
            class="two-line-clamp mt-2 mb-0"
            :class="{ inactive: !adTo.active }"
          >
            {{ sanitizedDescription }}
          </p>
          <ad-price
            v-if="CONFIG.SHOW_PRICE"
            :price="adTo.price!"
            class="mt-auto"
            :class="{ inactive: !adTo.active }"
          />
          <div class="mt-0 d-flex ga-2 pt-2 w-100">
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
          </div>
        </v-col>
      </v-row>
    </v-container>
  </v-sheet>
</template>

<script setup lang="ts">
import type { AdTO } from "@/apis/generated/anzeigen-backend";
import type { DeepReadonly } from "vue";

import { mdiCamera } from "@mdi/js";
import { computed, toRef } from "vue";

import {
  AdTOFromJSONTyped,
  AdTOToJSONTyped,
} from "@/apis/generated/anzeigen-backend";
import AdArtChip from "@/components/Ad/list/AdArtChip.vue";
import AdCategoryChip from "@/components/Ad/list/AdCategoryChip.vue";
import AdEditButton from "@/components/Ad/list/AdEditButton.vue";
import AdPrice from "@/components/Ad/list/AdPrice.vue";
import AdViewCountChip from "@/components/Ad/list/AdViewCountChip.vue";
import { useDialogEventBus } from "@/composables/useEventBus.ts";
import useHasAnyRole from "@/composables/useHasAnyRole.ts";
import { usePlainTextFromHtml } from "@/composables/useSanitizedHtml.ts";
import {
  CONFIG,
  PREVIEW_IMAGE_FILE_URI_PREFIX,
  ROUTES_AD,
} from "@/constants.ts";
import { useUserInfoStore } from "@/stores/userinfo.ts";
import { Role } from "@/types/Role.ts";

const userStore = useUserInfoStore();
const hasAdminRole = useHasAnyRole(Role.FACHADMIN);

const dialogBus = useDialogEventBus();

const { adTo } = defineProps<{
  adTo: DeepReadonly<AdTO>;
}>();

const sanitizedDescription = usePlainTextFromHtml(
  toRef(adTo.description || "")
);

/**
 * Computes whether the ad can be edited by the current user, either belongs to him, or user is admin.
 */
const canEdit = computed(
  () => adTo.swbUser?.id === userStore.userID || hasAdminRole.value
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
