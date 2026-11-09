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
                  icon="mdi-emoticon-sad-outline"
                  color="red"
                />
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col>
                <h3>Sie haben nicht die Erlaubnis, den Inhalt zu sehen.</h3>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col>
                <p>
                  Wenden Sie sich an Ihren Administrator, wenn Sie Hilfe
                  benötigen
                </p>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col>
                <v-btn
                  color="accent"
                  @click="reload"
                >
                  Neu Laden
                </v-btn>
              </v-col>
            </v-row>
          </v-container>
        </template>
      </v-sheet>
    </template>
  </v-dialog>
</template>

<script setup lang="ts">
import router from "@/plugins/router.ts";

defineProps<{
  activator: boolean;
}>();

const reload = () => router.go(0);
</script>

<style scoped></style>
