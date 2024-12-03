import { Levels } from "@/api/error";

export interface SnackbarMessage {
  message?: string | undefined;
  level?: Levels;
  timeout?: number;
}
