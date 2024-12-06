import type { SnackbarMessage } from "@/types/SnackbarMessage";

import { useSnackbarEventBus } from "@/composables/useEventBus";

export const snackbarBus = useSnackbarEventBus();

export const useSnackbar = () => {
  const sendMessage = (message: SnackbarMessage) => {
    snackbarBus.emit(message);
  };

  return { sendMessage };
};
