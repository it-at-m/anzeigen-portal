<template>
  <v-select
    v-model="modelValue"
    :items="conditions"
    item-value="value"
    item-title="title"
    density="compact"
    :label="t('adConditionSelector.placeholder')"
    color="accent"
  />
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useI18n } from "vue-i18n";

import { AdTOConditionEnum } from "@/api/swbrett";

const { t } = useI18n();

const modelValue = defineModel<AdTOConditionEnum | undefined>();

defineProps<{
  disabled?: boolean;
}>();

interface AdConditionMapping {
  value: AdTOConditionEnum;
  title: string;
}

const conditions = ref<AdConditionMapping[]>([
  {
    value: AdTOConditionEnum.NEW,
    title: t("adConditionSelector.conditions.new"),
  },
  {
    value: AdTOConditionEnum.GOOD,
    title: t("adConditionSelector.conditions.good"),
  },
  {
    value: AdTOConditionEnum.OKAY,
    title: t("adConditionSelector.conditions.okay"),
  },
  {
    value: AdTOConditionEnum.VERY_GOOD,
    title: t("adConditionSelector.conditions.very_good"),
  },
  {
    value: AdTOConditionEnum.DEFECTIVE,
    title: t("adConditionSelector.conditions.defective"),
  },
]);
</script>

<style scoped></style>
