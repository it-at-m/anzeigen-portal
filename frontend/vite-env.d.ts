// see https://vite.dev/guide/env-and-mode#intellisense-for-typescript
interface ViteTypeOptions {
  strictImportMetaEnv: unknown;
}

interface ImportMetaEnv {
  readonly VITE_AD2IMAGE_URL: string;
  readonly VITE_APPSWITCHER_URL: string;
  readonly VITE_BASE_API_PATH: string;
  readonly VITE_APP_VARIANT: "swb" | "gbr";
  readonly VITE_APP_VERSION: string;
  // add more custom env variables
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
