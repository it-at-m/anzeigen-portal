<template>
  <v-app>
    <the-snackbar-queue />
    <v-app-bar color="primary">
      <v-container class="ad-max-width">
        <v-row
          align="center"
          justify="center"
        >
          <v-col>
            <v-row
              align="center"
              justify="space-between"
            >
              <v-col
                cols="3"
                class="d-flex align-center justify-start"
              >
                <router-link
                  :to="{
                    name: ROUTES_BOARD,
                    query: DEFAULT_BOARD_QUERIES,
                  }"
                >
                  <v-toolbar-title class="font-weight-bold">
                    <span class="text-white">Schwarzes </span>
                    <span class="text-secondary">Brett</span>
                  </v-toolbar-title>
                </router-link>
              </v-col>
              <v-col
                cols="6"
                class="d-flex align-center justify-center"
              >
                <search-ad />
              </v-col>
              <v-col
                cols="1"
                class="d-flex justify-end"
              >
                <ad2-image-avatar
                  v-if="userStore.getUser !== null"
                  :username="userStore.getUser.username"
                />
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-container>
    </v-app-bar>
    <v-main>
      <v-container class="ad-max-width">
        <v-row>
          <v-col>
            <router-view v-slot="{ Component }">
              <v-fade-transition mode="out-in">
                <component :is="Component" />
              </v-fade-transition>
            </router-view>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
import { useTitle } from "@vueuse/core";

import Ad2ImageAvatar from "@/components/common/Ad2ImageAvatar.vue";
import SearchAd from "@/components/Filter/SearchAd.vue";
import TheSnackbarQueue from "@/components/TheSnackbarQueue.vue";
import { useApi } from "@/composables/useApi";
import { DEFAULT_BOARD_QUERIES, ROUTES_BOARD } from "@/Constants";
import { useUserStore } from "@/stores/user";

useApi();

const userStore = useUserStore();

useTitle("Anzeigen Portal");
</script>

<style>
.ad-max-width {
  max-width: 2000px !important;
}
</style>
