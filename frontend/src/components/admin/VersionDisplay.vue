<template>
  <ad-display-card>
    <template #title> {{ t(`versionDisplay.heading`) }} </template>
    <template #text>
      <v-row>
        <v-col>
          <ad-display-card>
            <template #subtitle> {{ t(`versionDisplay.backend`) }} </template>
            <template #text>{{ appInfoData?.application.version }}</template>
          </ad-display-card>
        </v-col>
        <v-col>
          <ad-display-card>
            <template #subtitle> {{ t(`versionDisplay.frontend`) }} </template>
            <template #text>{{ version }}</template>
          </ad-display-card>
        </v-col>
      </v-row>
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import { onMounted } from "vue";
import { useI18n } from "vue-i18n";

import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { useGetAppInfo } from "@/composables/api/useSettingsApi.ts";

const { t } = useI18n();

const version = import.meta.env.VITE_APP_VERSION;
const { data: appInfoData, call: appInfoCall } = useGetAppInfo();

onMounted(() => appInfoCall());
</script>
