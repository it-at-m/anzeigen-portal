<template>
  <ad-fab class="position-absolute bottom-0 right-0" />
  <v-row>
    <v-col
      md="4"
      cols="12"
    >
      <ad-search />
    </v-col>
    <v-col
      md="8"
      cols="12"
    >
      <ad-list />
    </v-col>
  </v-row>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";

import HealthService from "@/api/HealthService";
import AdList from "@/components/AdList.vue";
import AdSearch from "@/components/AdSearch.vue";
import AdFab from "@/components/common/AdFab.vue";
import { useSnackbarStore } from "@/stores/snackbar";
import HealthState from "@/types/HealthState";

const snackbarStore = useSnackbarStore();
const status = ref("DOWN");

onMounted(() => {
  HealthService.checkHealth()
    .then((content: HealthState) => (status.value = content.status))
    .catch((error) => {
      snackbarStore.showMessage(error);
    });
});
</script>

<style scoped></style>
