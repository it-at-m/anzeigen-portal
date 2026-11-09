import { defaultResponseHandler, getConfig } from "@/api/fetch-utils";
import { BASE_PATH } from "@/Constants.ts";

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
