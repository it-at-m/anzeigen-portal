<template>
  <v-dialog
    :model-value="activator"
    fullscreen
  >
    <template #default>
      <v-sheet class="d-flex flex-column align-center justify-center">
        <template #default>
          <v-container align="center">
            <v-row justify="center">
              <v-col>
                <v-icon
                  size="256"
                  :icon="mdiEmoticonSadOutline"
                  color="red"
                />
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col>
                <h3>{{ t("noPermissionDialog.header") }}</h3>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col>
                <p>
                  {{ t("noPermissionDialog.info") }}
                </p>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col>
                <v-btn
                  color="accent"
                  :text="t('noPermissionDialog.reload')"
                  @click="reload"
                />
              </v-col>
            </v-row>
          </v-container>
        </template>
      </v-sheet>
    </template>
  </v-dialog>
</template>

<script setup lang="ts">
import { mdiEmoticonSadOutline } from "@mdi/js";
import { useI18n } from "vue-i18n";

import router from "@/plugins/router.ts";

const { t } = useI18n();

defineProps<{
  activator: boolean;
}>();

const reload = () => router.go(0);
</script>

<style scoped></style>
