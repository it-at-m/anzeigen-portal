import { Levels } from "@/api/error";

/**
 * Represents a message to be displayed in a snackbar.
 */
export interface SnackbarMessage {
  message?: string | undefined;
  level?: Levels;
  timeout?: number;
}
