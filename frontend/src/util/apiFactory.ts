import type { HTTPHeaders } from "@/apis/generated/anzeigen-backend";

import { getHeaders } from "@/apis/fetch-utils.ts";
import { BaseAPI, Configuration } from "@/apis/generated/anzeigen-backend";
import { BASE_PATH } from "@/constants.ts";

type ApiCtor<T extends BaseAPI> = new (config: Configuration) => T;

const instances = new Map<ApiCtor<BaseAPI>, BaseAPI>();

function createConfig(): Configuration {
  return new Configuration({
    basePath: BASE_PATH,
    credentials: "same-origin",
    headers: convertHeaders(getHeaders()),
  });
}

function getInstance<T extends BaseAPI>(ApiClass: ApiCtor<T>): T {
  const existing = instances.get(ApiClass as ApiCtor<BaseAPI>);
  if (existing) {
    return existing as T;
  }

  const api = new ApiClass(createConfig());
  instances.set(ApiClass as ApiCtor<BaseAPI>, api);
  return api;
}

export const ApiFactory = {
  getInstance,
} as const;

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
