import type { HTTPHeaders } from "@/api/swbrett";
import type { InjectionKey } from "vue";

import { provide } from "vue";

import { getHeaders } from "@/api/fetch-utils";
import { Configuration, DefaultApi } from "@/api/swbrett";

export const DEFAULT_API_KEY: InjectionKey<DefaultApi> = Symbol("defaultApi");

/**
 * Initialisiert und konfiguriert die API mit Standard-Einstellungen.
 */
export const useApi = () => {
  const config = new Configuration({
    basePath: "/api/backend-service",
    credentials: "same-origin",
    headers: convertHeaders(getHeaders()),
  });
  const defaultApi = new DefaultApi(config);
  provide(DEFAULT_API_KEY, defaultApi);
};

/**
 * Konvertiert ein Headers-Objekt in ein einfaches Schlüssel-Wert-Paar-Objekt.
 * @param {Headers} headers - Das zu konvertierende Headers-Objekt.
 * @returns {HTTPHeaders} Ein Objekt mit den gleichen Headern.
 */
function convertHeaders(headers: Headers): HTTPHeaders {
  const httpHeaders: HTTPHeaders = {};

  headers.forEach((value, key) => {
    httpHeaders[key] = value;
  });

  return httpHeaders;
}
