import type { HTTPHeaders } from "@/apis/generated/anzeigen-backend";
import type { InjectionKey } from "vue";

import { provide } from "vue";

import { getHeaders } from "@/apis/fetch-utils.ts";
import { Configuration, DefaultApi } from "@/apis/generated/anzeigen-backend";
import { BASE_PATH } from "@/constants.ts";

export const DEFAULT_API_KEY: InjectionKey<DefaultApi> = Symbol("defaultApi");

/**
 * Initializes and configures the API with default settings.
 */
export const useApi = () => {
  const config = new Configuration({
    basePath: BASE_PATH,
    credentials: "same-origin",
    headers: convertHeaders(getHeaders()),
  });
  const defaultApi = new DefaultApi(config);
  provide(DEFAULT_API_KEY, defaultApi);
};

/**
 * Converts a Headers object into a simple key-value pair object.
 * @param {Headers} headers - The headers object to be converted.
 * @returns {HTTPHeaders} An object with the same headers.
 */
function convertHeaders(headers: Headers): HTTPHeaders {
  const httpHeaders: HTTPHeaders = {};

  headers.forEach((value, key) => {
    httpHeaders[key] = value;
  });

  return httpHeaders;
}
