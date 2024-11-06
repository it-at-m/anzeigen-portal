<template>
  <ad-dialog />
  <v-row>
    <v-col
      lg="3"
      cols="12"
    >
      <ad-nav-bar />
    </v-col>
    <v-col
      lg="9"
      cols="12"
    >
      <ad-list />
    </v-col>
  </v-row>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";

import { checkHealth } from "@/api/health-client";
import AdDialog from "@/components/Ad/Edit/AdDialog.vue";
import AdList from "@/components/AdList.vue";
import AdNavBar from "@/components/AdNavBar.vue";
import { useSnackbarStore } from "@/stores/snackbar";
import HealthState from "@/types/HealthState";

const snackbarStore = useSnackbarStore();
const status = ref("DOWN");

onMounted(() => {
  checkHealth()
    .then((content: HealthState) => (status.value = content.status))
    .catch((error) => {
      snackbarStore.showMessage(error);
    });
});
</script>

<style scoped></style>
