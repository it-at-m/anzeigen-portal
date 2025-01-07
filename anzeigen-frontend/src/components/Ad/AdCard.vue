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
          <a href="https://google.de">
            <v-img
              cover
              max-height="200"
              src="https://picsum.photos/300"
            />
          </a>
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
                <ad-edit-button is-edit />
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

import { ref } from "vue";

import AdArtChip from "@/components/Ad/AdArtChip.vue";
import AdCategoryChip from "@/components/Ad/AdCategoryChip.vue";
import AdEditButton from "@/components/Ad/AdEditButton.vue";
import AdPrice from "@/components/Ad/AdPrice.vue";
import AdViewCountChip from "@/components/Ad/AdViewCountChip.vue";

defineProps<{
  adTo: DeepReadonly<AdTO>;
}>();

const description =
  "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

const isOffer = ref(false);
</script>

<style scoped>
.two-line-clamp {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2; /* Anzahl der Zeilen, die angezeigt werden sollen */
  overflow: hidden;
}
</style>
