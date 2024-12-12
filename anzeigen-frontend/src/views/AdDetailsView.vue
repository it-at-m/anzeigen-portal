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
import type {
  AdCategory,
  AdTO,
  SwbFileTO,
  SwbImageTO,
  SwbUserTO,
} from "@/api/swbrett";

import { useMemoize } from "@vueuse/core";
import { useRouteQuery } from "@vueuse/router";
import { onMounted, ref, watch } from "vue";
import { useRouter } from "vue-router";

import AdNotFound from "@/components/Ad/Details/AdNotFound.vue";
import AdOverview from "@/components/Ad/Details/AdOverview.vue";
import { useGetAd } from "@/composables/api/useAdApi";
import { useClearCacheEventBus } from "@/composables/useEventBus";

const exampleAd: AdTO = {
  id: 1,
  swbUser: {
    id: 123,
    displayName: "John Doe",
    email: "johndoe@example.com",
  } as SwbUserTO,
  adCategory: {
    id: 10,
    name: "Electronics",
  } as AdCategory,
  adType: "SEEK", // Beispielwert aus AdTOAdTypeEnum
  active: true,
  title: "Smartphone for Sale",
  description:
    "A lightly used smartphone in excellent condition. A lightly used smartphone in excellent condition. A lightly used smartphone in excellent condition. A lightly used smartphone in excellent condition. A lightly used smartphone in excellent condition.",
  price: 250,
  phone: "+123456789",
  email: "seller@example.com",
  link: "https://example.com/listing/1",
  creationDateTime: new Date("2024-01-01T12:00:00Z"),
  expiryDate: new Date("2024-12-31T23:59:59Z"),
  imagePreviewBase64: "data:image/png;base64,iVBORw0KGgoAAAANS...",
  adImg: {
    id: 101,
    fileName: "smartphone.png",
    url: "https://example.com/images/smartphone.png",
  } as SwbImageTO,
  adFiles: [
    {
      id: 1,
      name: "manual.pdf",
      url: "https://example.com/files/manual.pdf",
    } as SwbFileTO,
    {
      id: 2,
      name: "someVeryLongFileNameBecauseItsAllowed.png",
      url: "https://example.com/files/manual.pdf",
    } as SwbFileTO,
    {
      id: 3,
      name: "wooops.pdf",
      url: "https://example.com/files/manual.pdf",
    } as SwbFileTO,
  ],
  views: 150,
};

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
