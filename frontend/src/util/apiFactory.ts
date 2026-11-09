import type { HTTPHeaders } from "@/api/swbrett";

import { getHeaders } from "@/api/fetch-utils.ts";
import { Configuration, DefaultApi } from "@/api/swbrett";

// eslint-disable-next-line @typescript-eslint/no-extraneous-class
export class ApiFactory {
  private static _instance: DefaultApi;

  public static getInstance() {
    if (!ApiFactory._instance) {
      const config = new Configuration({
        basePath: "/api/backend-service",
        credentials: "same-origin",
        headers: convertHeaders(getHeaders()),
      });
      ApiFactory._instance = new DefaultApi(config);
    }
    return ApiFactory._instance;
  }
}

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
