import type { SwbUserTO } from "@/apis/generated/anzeigen-backend";
import type { UserInfo } from "@/types/UserInfo";

import { defineStore } from "pinia";
import { computed, readonly, ref } from "vue";

import { getUserInfo } from "@/apis/userinfo-client.ts";
import { useCreateUser, useFindUser } from "@/composables/api/useUserApi.ts";
import { STATUS_INDICATORS } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import { Role } from "@/types/Role";

function isRole(value: string): value is Role {
  return Object.values(Role).includes(value as Role);
}

export const useUserInfoStore = defineStore("userInfo", () => {
  const snackbarStore = useSnackbarStore();
  const internalUserInfo = ref<UserInfo | undefined>(undefined);
  const userInfo = readonly(internalUserInfo);
  const internalUserId = ref<number | undefined>();
  const userID = readonly(internalUserId);

  const {
    call: findUserCall,
    data: findUserData,
    error: findUserError,
  } = useFindUser();
  const { call: createUserCall, data: createUserData } = useCreateUser();

  /**
   * Current user transformed as SwbUserTO.
   */
  const swbUserTo = computed(() => {
    return {
      id: internalUserId.value,
      lhmObjectId: userInfo.value?.lhmObjectID,
      displayName: userInfo.value?.displayName,
    } as SwbUserTO;
  });

  /**
   * ObjectId of current user.
   */
  const lhmObjectId = computed(() => userInfo.value?.lhmObjectID);

  /**
   * Sets the user id from the backend.
   * @param payload
   */
  function setUserId(payload: number) {
    internalUserId.value = payload;
  }

  async function fetchUserInfo(): Promise<void> {
    try {
      internalUserInfo.value = await getUserInfo();

      await resolveSwbUser();
    } catch {
      snackbarStore.push({
        color: STATUS_INDICATORS.ERROR,
        text: "Nutzer konnte nicht geladen werden.",
      });
    }
  }

  /**
   * Finds or creates the swb user, used in the app
   */
  async function resolveSwbUser() {
    if (!internalUserInfo.value || !lhmObjectId.value) {
      return;
    }

    await findUserCall({
      swbUserFind: { lhmObjectID: lhmObjectId.value },
    });

    if (findUserData.value?.id === undefined && !findUserError.value) {
      await createUserCall({
        swbUserTO: {
          displayName: internalUserInfo.value?.displayName,
          lhmObjectId: lhmObjectId.value,
        },
      });
    }

    if (findUserError.value) {
      snackbarStore.clear();
      return;
    }

    const currentUser = findUserData.value?.id
      ? findUserData.value
      : createUserData.value;
    setUserId(currentUser?.id || -1);

    snackbarStore.push({
      color: findUserData.value?.id
        ? STATUS_INDICATORS.INFO
        : STATUS_INDICATORS.SUCCESS,
      text: `Willkommen ${findUserData.value?.id ? "zurück" : ""} ${currentUser?.displayName || internalUserInfo.value?.displayName}.`,
    });
  }

  const currentRoles = computed(
    () => internalUserInfo.value?.authorities.filter(isRole) || []
  );

  return {
    userInfo,
    currentRoles,
    fetchUserInfo,
    setUserId,
    userID,
    lhmObjectId,
    swbUserTo,
  };
});
