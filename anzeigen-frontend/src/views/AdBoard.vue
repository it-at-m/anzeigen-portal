<template>
  <div>
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
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";

import { checkHealth } from "@/api/health-client";
import AdDialog from "@/components/Ad/Edit/AdDialog.vue";
import AdList from "@/components/AdList.vue";
import AdNavBar from "@/components/AdNavBar.vue";
import { useSnackbar } from "@/composables/useSnackbar";
import HealthState from "@/types/HealthState";

const snackbar = useSnackbar();
const status = ref("DOWN");

onMounted(() => {
  checkHealth()
    .then((content: HealthState) => (status.value = content.status))
    .catch((error) => {
      snackbar.sendMessage(error);
    });
});
</script>

<style scoped></style>
