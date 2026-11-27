<template>
  <ad-display-card>
    <template #title> {{ t("userFilter.title") }} </template>
    <template #text>
      <v-btn
        variant="tonal"
        color="accent"
        :prepend-icon="mdiWindowClose"
        :text="t('userFilter.resetFilter')"
        @click="emit('click')"
      />
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import { mdiWindowClose } from "@mdi/js";
import { useI18n } from "vue-i18n";

import AdDisplayCard from "@/components/common/AdDisplayCard.vue";

const { t } = useI18n();

const emit = defineEmits<{
  click: [];
}>();
</script>

<style scoped></style>
