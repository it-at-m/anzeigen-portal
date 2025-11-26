/// <reference types="vite/client" />

declare module "*.vue" {
  import type { DefineComponent } from "vue";
  const component: DefineComponent<{}, {}, any>;
  export default component;
}

export interface ImportMetaEnv {
  readonly VITE_AD2IMAGE_URL: string;
  readonly VITE_APPSWITCHER_SERVER_URL: string;
  readonly VITE_APP_VARIANT: "swb" | "gbr";
  readonly VITE_BASE_PATH: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
