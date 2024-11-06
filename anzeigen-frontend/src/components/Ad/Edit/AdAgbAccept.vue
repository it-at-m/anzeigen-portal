<template>
  <v-checkbox
    v-model="isChecked"
    color="accent"
  >
    <template #label>
      <p>
        Ich bin mit den <a>Nutzungsbedingungen (AGB)</a> einverstanden. Das
        Schwarze Brett darf nur zu rein privaten Zwecken genutzt werden.
        Insbesondere Werbung kommerzieller Art ist nicht gestattet!
        Informationen zur Verarbeitung personenbezogener Daten enthalten die
        <a>Datenschutzhinweise</a>.
      </p>
    </template>
  </v-checkbox>
</template>

<script setup lang="ts">
const isChecked = defineModel<boolean>();
</script>

<style scoped></style>
