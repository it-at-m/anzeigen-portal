<template>
  <v-expansion-panel>
    <v-expansion-panel-title>
      <v-container class="py-0 pl-0">
        <v-row no-gutters>
          <v-col
            cols="12"
            sm="3"
          >
            <a href="https://google.de">
              <v-img
                max-height="150"
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
              >
                <v-col
                  cols="12"
                  sm="10"
                >
                  <h2>
                    Das ist ein Artikelname und hier schreibt einer zu viel rein
                  </h2>
                </v-col>
                <v-col
                  cols="2"
                  sm="2"
                >
                  <v-speed-dial
                    location="left center"
                    transition="slide-x-reverse-transition"
                  >
                    <template v-slot:activator="{ props: activatorProps }">
                      <v-btn
                        variant="elevated"
                        color="accent"
                        icon="mdi-cog-outline"
                        v-bind="activatorProps"
                      ></v-btn>
                    </template>

                    <v-btn
                      key="1"
                      color="warning"
                      icon="mdi-pencil"
                    />
                    <v-btn
                      key="2"
                      color="error"
                      icon="mdi-trash-can-outline"
                    />
                  </v-speed-dial>
                </v-col>
              </v-row>
              <v-row
                class="w-100"
                align="end"
                justify="space-between"
              >
                <v-col class="pa-0 pb-2">
                  <ad-art-chip
                    :is-offer="isOffer"
                    class="mr-2"
                  />
                  <v-chip
                    variant="flat"
                    prepend-icon="mdi-eye-outline"
                    color="accent"
                  >
                    100
                  </v-chip>
                </v-col>
                <v-col cols="2">
                  <h2>30€ VHB</h2>
                </v-col>
              </v-row>
            </v-container>
          </v-col>
        </v-row>
      </v-container>
    </v-expansion-panel-title>
    <v-expansion-panel-text>
      <v-divider />
      <ad-card-text />
    </v-expansion-panel-text>
  </v-expansion-panel>
</template>

<script setup lang="ts">
import { ref } from "vue";

import AdArtChip from "@/components/Ad/AdArtChip.vue";
import AdCardText from "@/components/Ad/AdCardText.vue";

const isOffer = ref(false);
</script>

<style scoped></style>
