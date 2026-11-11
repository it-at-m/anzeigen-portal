import { defaultResponseHandler, getConfig } from "@/apis/fetch-utils.ts";
import HealthState from "@/types/HealthState.ts";

export function checkHealth(): Promise<HealthState> {
  return fetch("/actuator/health", getConfig())
    .then((response) => {
      defaultResponseHandler(response);
      return response.json();
    })
    .catch((err) => {
      defaultResponseHandler(err);
    });
}
