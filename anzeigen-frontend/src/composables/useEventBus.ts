import type { AdTO } from "@/api/swbrett";
import type { SnackbarMessage } from "@/types/SnackbarMessage";

import { useEventBus } from "@vueuse/core";

import { EV_EDIT_AD_DIALOG, EV_SNACKBAR } from "@/Constants";

export const useDialogEventBus = () => useEventBus<AdTO>(EV_EDIT_AD_DIALOG);

export const useSnackbarEventBus = () =>
  useEventBus<SnackbarMessage>(EV_SNACKBAR);
