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
        <div
          v-if="loading"
          class="d-flex justify-center h-100 align-center"
        >
          <v-progress-circular
            indeterminate
            size="100"
            color="accent"
            width="6"
          />
        </div>
        <div v-else>
          <ad-overview
            v-if="!getAdError && adDetails !== null"
            :ad-details="adDetails"
          />
          <ad-not-found v-else />
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { useMemoize } from "@vueuse/core";
import { useRouteQuery } from "@vueuse/router";
import { computed, onMounted, ref, watch } from "vue";
import { useRouter } from "vue-router";

import AdNotFound from "@/components/Ad/Details/AdNotFound.vue";
import AdOverview from "@/components/Ad/Details/AdOverview.vue";
import { useGetAd, useIncrementAdView } from "@/composables/api/useAdApi";
import { useClearCacheEventBus } from "@/composables/useEventBus";

const clearCacheEventBus = useClearCacheEventBus();

const idQuery = useRouteQuery("id");

const router = useRouter();

const {
  call: getAdCall,
  data: getAdData,
  error: getAdError,
  loading: getAdLoading,
} = useGetAd();

const { call: incrementView, loading: incrementAdViewLoading } =
  useIncrementAdView();

const adDetails = ref<Readonly<AdTO> | null>(null);

const loading = computed(
  () => getAdLoading.value || incrementAdViewLoading.value
);

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
  if (idQuery.value) {
    await incrementView({ id: parseInt(idQuery.value.toString()) });
  }
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
