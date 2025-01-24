import type { SwbUserTO } from "@/api/swbrett";

import { defineStore } from "pinia";
import { computed, ref } from "vue";

import User from "@/types/User";

export interface UserState {
  user: User | null;
}

/**
 * Pinia store for managing current user state.
 */
export const useUserStore = defineStore("user", () => {
  const user = ref<User | null>(null);
  const userID = ref<number | null>();

  /**
   * The current user.
   */
  const getUser = computed((): User | null => {
    return user.value;
  });

  /**
   * Current user transformed as SwbUserTO.
   */
  const swbUserTo = computed(() => {
    return {
      id: userID.value,
      lhmObjectId: user.value?.lhmObjectID,
      displayName: user.value?.displayName,
    } as SwbUserTO;
  });

  /**
   * ObjectId of current user.
   */
  const lhmObjectId = computed(() => user.value?.lhmObjectID);

  /**
   * Set the current user.
   * @param payload new user
   */
  function setUser(payload: User | null): void {
    user.value = payload;
  }

  /**
   * Sets the user id from the backend.
   * @param payload
   */
  function setUserId(payload: number) {
    userID.value = payload;
  }

  return { getUser, setUser, swbUserTo, lhmObjectId, setUserId, userID };
});
