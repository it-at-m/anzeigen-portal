<template>
  <v-card elevation="0">
    <v-card-title>
      <slot name="title" />
    </v-card-title>
    <v-card-text>
      <slot name="text" />
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts"></script>

<style scoped></style>
