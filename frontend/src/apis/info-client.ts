import { defaultResponseHandler, getConfig } from "@/apis/fetch-utils.ts";
import { BASE_PATH } from "@/constants.ts";

export interface Info {
  application: Application;
}

export interface Application {
  name: string;
  version: string;
  heading: string;
}

export function getInfo(): Promise<Info> {
  return fetch(BASE_PATH + "/actuator/info", getConfig())
    .then((response) => {
      defaultResponseHandler(response);
      return response.json();
    })
    .catch((err) => {
      defaultResponseHandler(err);
    });
}
