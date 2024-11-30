import type { SnackbarMessage } from "@/types/SnackbarMessage";

import { useEventBus } from "@vueuse/core";

import { EV_SNACKBAR } from "@/Constants";

const snackbarBus = useEventBus<SnackbarMessage>(EV_SNACKBAR);

export const useSnackbar = () => {
  const sendMessage = (message: SnackbarMessage) => {
    snackbarBus.emit(message);
  };

  return { sendMessage };
};
