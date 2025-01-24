import type { SnackbarMessage } from "@/types/SnackbarMessage";

import { useSnackbarEventBus } from "@/composables/useEventBus";

export const snackbarBus = useSnackbarEventBus();

/**
 * Provides a utility for sending snackbar messages through the event bus.
 */
export const useSnackbar = () => {
  /**
   * Sends a snackbar message.
   * @param message - The message to be displayed in the snackbar.
   */
  const sendMessage = (message: SnackbarMessage) => {
    snackbarBus.emit(message);
  };

  return { sendMessage };
};
