<template>
  <v-container>
    <v-row justify="center">
      <v-col
        cols="12"
        md="10"
        xl="8"
      >
        <v-container>
          <v-btn
            color="accent"
            @click="back"
          >
            Zurück
          </v-btn>
        </v-container>
        <ad-overview
          v-if="!getAdError && adDetails !== null"
          :ad-details="adDetails"
        />
        <ad-not-found v-else />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { useMemoize } from "@vueuse/core";
import { useRouteQuery } from "@vueuse/router";
import { onMounted, ref, watch } from "vue";
import { useRouter } from "vue-router";

import AdNotFound from "@/components/Ad/Details/AdNotFound.vue";
import AdOverview from "@/components/Ad/Details/AdOverview.vue";
import { useGetAd } from "@/composables/api/useAdApi";
import { useClearCacheEventBus } from "@/composables/useEventBus";

const clearCacheEventBus = useClearCacheEventBus();

const idQuery = useRouteQuery("id");

const router = useRouter();

const { call: getAdCall, data: getAdData, error: getAdError } = useGetAd();

const adDetails = ref<Readonly<AdTO> | null>(null);

const getAd = useMemoize(async (adId: number) => {
  await getAdCall({ id: adId });
  return getAdData;
});

clearCacheEventBus.on(() => getAd.clear());

watch(idQuery, (newId) => {
  if (newId !== null) {
    updateAd(newId.toString() || "1");
  }
});

onMounted(async () => {
  await updateAd(idQuery.value?.toString() || "");
});

const updateAd = async (id: string) => {
  adDetails.value = (await getAd(parseInt(id))).value as AdTO;
};

const back = () => {
  router.go(-1);
};
</script>

<style scoped></style>
