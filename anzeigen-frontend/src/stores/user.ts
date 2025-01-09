import type { SwbUserTO } from "@/api/swbrett";

import { defineStore } from "pinia";
import { computed, ref } from "vue";

import User from "@/types/User";

export interface UserState {
  user: User | null;
}

export const useUserStore = defineStore("user", () => {
  const user = ref<User | null>(null);
  const userID = ref<number | null>();

  const getUser = computed((): User | null => {
    return user.value;
  });

  const swbUserTo = computed(() => {
    return {
      lhmObjectId: user.value?.lhmObjectID,
      displayName: user.value?.displayName,
    } as SwbUserTO;
  });

  const lhmObjectId = computed(() => user.value?.lhmObjectID);

  function setUser(payload: User | null): void {
    user.value = payload;
  }

  function setUserId(payload: number) {
    userID.value = payload;
  }

  return { getUser, setUser, swbUserTo, lhmObjectId, setUserId, userID };
});
