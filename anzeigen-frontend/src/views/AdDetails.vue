<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="6">
        <v-container>
          <v-row>
            <v-col>
              <ad-display-sheet class="pa-2">
                <v-img
                  cover
                  max-height="300"
                  src="https://picsum.photos/300"
                />
              </ad-display-sheet>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <ad-display-card>
                <template #title>
                  {{ adDetails.title }}
                </template>
                <template #subtitle>
                  <ad-price
                    v-if="adDetails.price"
                    :price="adDetails.price"
                  />
                </template>
                <template #text>
                  {{ adDetails.description }}
                </template>
              </ad-display-card>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <ad-display-card>
                <template #subtitle>Details</template>
                <template #text>
                  <v-container class="pl-0">
                    <v-row>
                      <v-col class="py-0"> Art </v-col>
                      <v-col class="py-0"> {{ adDetails.adType }} </v-col>
                    </v-row>
                    <v-row>
                      <v-col class="py-0"> Kategorie </v-col>
                      <v-col class="py-0">
                        {{ adDetails.adCategory?.name }}
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col class="py-0"> Erstellungsdatum </v-col>
                      <v-col class="py-0">
                        {{
                          useDateFormat(
                            adDetails.creationDateTime,
                            DATE_DISPLAY_FORMAT
                          )
                        }}
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col class="py-0"> Ablaufdatum </v-col>
                      <v-col class="py-0">
                        {{
                          useDateFormat(
                            adDetails.expiryDate,
                            DATE_DISPLAY_FORMAT
                          )
                        }}
                      </v-col>
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
                </template>
              </ad-display-card>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <ad-display-card>
                <template #subtitle>Weitere Informationen</template>
                <template #text>
                  <icon-text
                    v-for="i in adDetails.adFiles"
                    :key="i.id"
                    :label="i.name!"
                    icon="link-variant"
                  />
                </template>
              </ad-display-card>
            </v-col>
            <v-col>
              <ad-display-card>
                <template #subtitle>Kontakt</template>
                <template #text>
                  <icon-text
                    v-if="adDetails.phone"
                    icon="phone"
                    :label="adDetails.phone"
                  />
                  <icon-text
                    v-if="adDetails.email"
                    icon="email"
                    :label="adDetails.email"
                  />
                  <icon-text
                    v-if="adDetails.swbUser?.displayName"
                    icon="account"
                    :label="adDetails.swbUser?.displayName"
                    href="https://google.de"
                  />
                </template>
              </ad-display-card>
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import type {
  AdCategory,
  AdTO,
  GetAdRequest,
  SwbFileTO,
  SwbImageTO,
  SwbUserTO,
} from "@/api/swbrett";

import { useDateFormat, useMemoize } from "@vueuse/core";
import { useRouteQuery } from "@vueuse/router";
import { aw } from "vitest/dist/chunks/reporters.anwo7Y6a";
import { onMounted, ref, watch } from "vue";

import AdPrice from "@/components/Ad/AdPrice.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import AdDisplaySheet from "@/components/common/AdDisplaySheet.vue";
import IconText from "@/components/common/IconText.vue";
import { useGetAd } from "@/composables/api/useAdApi";
import { DATE_DISPLAY_FORMAT } from "@/Constants";

/**
 * This could be huge here: https://vueuse.org/core/useMemoize/
 */

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

const { call: getAdCall, data: getAdData } = useGetAd();

const adDetails = ref<Readonly<AdTO> | null>(exampleAd);

const idQuery = useRouteQuery("id");

watch(idQuery, (newId) => {
  if (newId !== null) updateAd(newId.toString() || "1");
});

onMounted(() => {
  updateAd(idQuery.value?.toString() || "");
});

const updateAd = async (id: string) => {
  console.log("idQuery:", id);
  adDetails.value = (await getAd(parseInt(id))).value as AdTO;
};

const getAd = useMemoize(async (adId: number) => {
  await getAdCall({ id: adId });
  return getAdData;
});
</script>

<style scoped></style>
