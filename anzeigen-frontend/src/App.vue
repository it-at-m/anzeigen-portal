<template>
  <v-app>
    <the-snackbar />
    <v-app-bar color="primary">
      <v-container
        fluid
        class="max-width"
      >
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
                <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
                <router-link to="/">
                  <v-toolbar-title class="font-weight-bold">
                    <span class="text-white">Schwarzes-</span>
                    <span class="text-secondary">Brett</span>
                  </v-toolbar-title>
                </router-link>
              </v-col>
              <v-col
                cols="6"
                class="d-flex align-center justify-center"
              >
                <v-text-field
                  id="searchField"
                  v-model="query"
                  flat
                  variant="solo-inverted"
                  hide-details
                  label="Suche"
                  clearable
                  prepend-inner-icon="mdi-magnify"
                  theme="dark"
                  @keyup.enter="search"
                />
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
      <v-container
        fluid
        class="max-width"
      >
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
import { onMounted, ref } from "vue";

import UserService from "@/api/UserService";
import Ad2ImageAvatar from "@/components/common/Ad2ImageAvatar.vue";
import TheSnackbar from "@/components/TheSnackbar.vue";
import { useSnackbarStore } from "@/stores/snackbar";
import { useUserStore } from "@/stores/user";
import User, { UserLocalDevelopment } from "@/types/User";

const drawer = ref(true);
const query = ref<string>("");
useTitle("Anzeigen Portal");

const snackbarStore = useSnackbarStore();
const userStore = useUserStore();

onMounted(() => {
  loadUser();
});

/**
 * Loads UserInfo from the backend and sets it in the store.
 */
function loadUser(): void {
  UserService.getUser()
    .then((user: User) => userStore.setUser(user))
    .catch(() => {
      // No user info received, so fallback
      if (import.meta.env.DEV) {
        userStore.setUser(UserLocalDevelopment());
      } else {
        userStore.setUser(null);
      }
    });
}

/**
 * Navigates to the page with the search results and sends an event to trigger further searches.
 */

async function search(): Promise<void> {
  if (query.value !== "" && query.value !== null) {
    snackbarStore.showMessage({
      message: "Sie haben nach " + query.value + " gesucht. ;)",
    });
  }
}
</script>

<style>
.max-width {
  max-width: 1400px;
}
</style>
