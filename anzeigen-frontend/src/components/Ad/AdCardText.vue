<template>
  <v-card
    title="Beschreibung"
    :text="text"
    elevation="0"
  />
  <v-divider />
  <v-card
    title="Details"
    elevation="0"
  >
    <v-card-text>
      <v-container>
        <v-row>
          <v-col class="py-0"> Art </v-col>
          <v-col class="py-0"> Biete </v-col>
        </v-row>
        <v-row>
          <v-col class="py-0"> Kategorie </v-col>
          <v-col class="py-0"> Bücher, Filme und Musik </v-col>
        </v-row>
        <v-row>
          <v-col class="py-0"> Erstellungsdatum </v-col>
          <v-col class="py-0"> 08.10.2024 </v-col>
        </v-row>
        <v-row>
          <v-col class="py-0"> Ablaufdatum </v-col>
          <v-col class="py-0"> 22.11.2024 </v-col>
        </v-row>
        <v-row>
          <v-col class="py-0"> Direkter Link </v-col>
          <v-col class="py-0">
            <icon-text
              label="https://weissesbrett.muenchen.de/board/main/7430"
              href="https://weissesbrett.muenchen.de/board/main/7430"
            />
          </v-col>
        </v-row>
      </v-container>
    </v-card-text>
  </v-card>
  <v-divider />
  <v-card
    title="Weitere Informationen"
    elevation="0"
  >
    <v-card-text>
      <icon-text
        v-for="i in 4"
        :key="i"
        label="Irgendein Link zu einer Datei"
        icon="link-variant"
      />
    </v-card-text>
  </v-card>
  <v-divider />
  <v-card
    title="Kontakt"
    elevation="0"
  >
    <v-card-text>
      <ad-card-contact />
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import AdCardContact from "@/components/Ad/AdCardContact.vue";
import IconText from "@/components/common/IconText.vue";

const text =
  "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
</script>

<style scoped></style>
