import type { InjectionKey } from "vue";

import { provide } from "vue";

import { Configuration, DefaultApi } from "@/api/swbrett";

export const DEFAULT_API_KEY: InjectionKey<DefaultApi> = Symbol("defaultApi");

export const useApi = () => {
  const config = new Configuration({
    basePath: "/api/backend-service",
  });
  const defaultApi = new DefaultApi(config);
  provide(DEFAULT_API_KEY, defaultApi);
};
