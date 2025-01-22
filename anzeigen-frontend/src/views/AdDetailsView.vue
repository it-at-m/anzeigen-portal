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
import { DEFAULT_BOARD_QUERIES, ROUTES_BOARD } from "@/Constants";

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

const loading = computed(() => getAdLoading.value);

const getAd = useMemoize(async (adId: number) => {
  await getAdCall({ id: adId });
  return getAdData;
});

clearCacheEventBus.on(() => getAd.clear());

watch(idQuery, (newId) => {
  if (newId !== null) {
    updateDisplayedAd(newId.toString() || "1");
  }
});

onMounted(() => {
  if (idQuery.value) {
    incrementView({ id: parseInt(idQuery.value.toString()) });

    updateDisplayedAd(idQuery.value?.toString());
  }
});

const updateDisplayedAd = async (id: string) => {
  adDetails.value = (await getAd(parseInt(id))).value as AdTO;
};

const back = () => {
  if (window.history.length > 1) {
    router.go(-1);
  } else {
    router.push({
      name: ROUTES_BOARD,
      query: DEFAULT_BOARD_QUERIES,
    });
  }
};
</script>

<style scoped></style>
