<template>
  <v-container>
    <v-row>
      <v-col cols="4"> <sheet>Spalte 1</sheet> </v-col>
      <v-col cols="8">
        <sheet>Spalte 2</sheet
        ><v-btn @click="showMsges">showmsges</v-btn></v-col
      >
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";

import { Levels } from "@/api/error";
import HealthService from "@/api/HealthService";
import Sheet from "@/components/common/Sheet.vue";
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

const showMsges = () => {
  snackbarStore.showMessage({ message: "hallo", level: Levels.INFO });
};
</script>

<style scoped></style>
