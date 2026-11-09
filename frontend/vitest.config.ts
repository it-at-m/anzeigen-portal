import { fileURLToPath, URL } from "node:url";

import { configDefaults, defineConfig, mergeConfig } from "vitest/config";

import viteConfig from "./vite.config";

const variant = process.env.VITE_APP_VARIANT === "gbr" ? "gbr" : "swb";

const resolvedViteConfig =
  typeof viteConfig === "function" ? viteConfig({ mode: variant }) : viteConfig;

export default mergeConfig(
  resolvedViteConfig,
  defineConfig({
    resolve: {
      alias: {
        "@": fileURLToPath(new URL("./src", import.meta.url)),
        "@variants": fileURLToPath(
          new URL(`./src/variants/${variant}`, import.meta.url)
        ),
      },
    },
    test: {
      globals: true,
      environment: "jsdom",
      exclude: [...configDefaults.exclude, "e2e/*"],
      root: fileURLToPath(new URL("./", import.meta.url)),
      server: { deps: { inline: ["vuetify"] } },
    },
  })
);
