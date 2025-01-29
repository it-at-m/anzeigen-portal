<template>
  <ad-display-card>
    <template #title>Allgemeine Einstellungen</template>
    <template #text>
      <text-select-value-setting />
      <v-row>
        <v-col>
          <text-value-setting setting-name="MOTD" />
        </v-col>
      </v-row>
      <v-row>
        <v-col
          cols="12"
          md="6"
        >
          <text-value-setting setting-name="DEFAULT_ORDERING" />
        </v-col>
        <v-col
          cols="12"
          md="6"
        >
          <text-value-setting setting-name="DEFAULT_SORTING" />
        </v-col>
      </v-row>
      <v-row>
        <v-col
          v-for="settingName in NUMBER_VALUE_SETTINGS"
          :key="settingName"
          cols="12"
          md="6"
          lg="4"
        >
          <number-value-setting :setting-name="settingName" />
        </v-col>
      </v-row>
      <v-row>
        <v-col class="d-flex justify-end">
          <v-btn
            class="mr-2"
            variant="outlined"
            prepend-icon="mdi-undo-variant"
            color="accent"
            @click="updateSettings"
          >
            Änderungen verwerfen
          </v-btn>
          <v-btn
            variant="flat"
            prepend-icon="mdi-content-save"
            color="accent"
          >
            Speichern
          </v-btn>
        </v-col>
      </v-row>
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/api/swbrett";

import NumberValueSetting from "@/components/admin/general/NumberValueSetting.vue";
import TextSelectValueSetting from "@/components/admin/general/TextSelectValueSetting.vue";
import TextValueSetting from "@/components/admin/general/TextValueSetting.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { useUpdateSettings } from "@/composables/useUpdateSettings.ts";
import { CRITERIA_VALUES } from "../../types/SortingOrderCriteria.ts";

const updateSettings = useUpdateSettings();

const NUMBER_VALUE_SETTINGS: SettingTOSettingNameEnum[] = [
  "MAX_SWB_IMAGE_SIZE",
  "MAX_PAGE_SIZE",
  "MAX_SWB_FILE_SIZE",
  "MAX_SWB_FILES_LENGTH",
  "MAX_EXPIRY_DATE_RANGE",
  "MAX_ARCHIVE_DATE_RANGE",
];

/**
 * Loads settings and stores them in the settings store.
 */
const loadSettings = async () => {};
</script>

<style scoped></style>
