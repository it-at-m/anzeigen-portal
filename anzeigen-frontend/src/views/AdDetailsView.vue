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
            :ad-user="getUserData"
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

import AdNotFound from "@/components/Ad/details/AdNotFound.vue";
import AdOverview from "@/components/Ad/details/AdOverview.vue";
import { useGetAd, useIncrementAdView } from "@/composables/api/useAdApi";
import { useGetUser } from "@/composables/api/useUserApi.ts";
import { useDefaultQuery } from "@/composables/useDefaultQuery.ts";
import { useClearCacheEventBus } from "@/composables/useEventBus";
import { ROUTES_BOARD } from "@/Constants";
import { useUserStore } from "@/stores/user.ts";

const clearCacheEventBus = useClearCacheEventBus();

const idQuery = useRouteQuery("id");
const defaultQuery = useDefaultQuery();

const router = useRouter();

const userStore = useUserStore();

const {
  call: getAdCall,
  data: getAdData,
  error: getAdError,
  loading: getAdLoading,
} = useGetAd();

const {
  call: getUser,
  data: getUserData,
  loading: getUserLoading,
} = useGetUser();

const { call: incrementView } = useIncrementAdView();

/**
 * State of the current displayed ad.
 */
const adDetails = ref<Readonly<AdTO> | null>(null);

const loading = computed(() => getAdLoading.value || getUserLoading.value);

/**
 * Uses Memoize to cache the results of the request for the details of a specific ad.
 *
 * @param adId the id of the ad
 */
const getAd = useMemoize(async (adId: number) => {
  await getAdCall({ id: adId });
  return getAdData;
});

clearCacheEventBus.on(() => getAd.clear());

/**
 * Watches the `idQuery` for changes, increments the view count and fetches the new ad accordingly.
 */
watch(idQuery, (newId) => {
  if (newId !== null) {
    incrementView({ id: parseInt(newId.toString()) });

    updateDisplayedAd(newId.toString() || "1");
  }
});

/**
 * Initializes the component by incrementing the view count of the current adID and fetches the new ad.
 */
onMounted(() => {
  if (idQuery.value) {
    incrementView({ id: parseInt(idQuery.value.toString()) });

    updateDisplayedAd(idQuery.value?.toString());
  }
});

/**
 * Updates the displayed ad details based on the given ad ID.
 * @param id - The ID of the ad to display.
 */
const updateDisplayedAd = async (id: string) => {
  adDetails.value = (await getAd(parseInt(id))).value as AdTO;

  if (userStore.isAdmin && adDetails.value.swbUser?.id) {
    await getUser({
      userId: adDetails.value.swbUser?.id,
    });
  }
};

/**
 * Navigates back in history or to the default board if no history exists.
 */
const back = () => {
  if (window.history.length > 1) {
    router.go(-1);
  } else {
    router.push({
      name: ROUTES_BOARD,
      query: defaultQuery.value,
    });
  }
};
</script>

<style scoped></style>
