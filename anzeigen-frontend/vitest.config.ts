import { fileURLToPath } from "node:url";

import { configDefaults, defineConfig, mergeConfig } from "vitest/config";

import viteConfig from "./vite.config";

// Wert für mode bestimmen; 'test' oder eine Ihrer Varianten
const baseMode = process.env.VITE_APP_VARIANT ?? "test";

const resolvedViteConfig =
  typeof viteConfig === "function"
    ? viteConfig({ mode: baseMode })
    : viteConfig;

export default mergeConfig(
  resolvedViteConfig,
  defineConfig({
    test: {
      globals: true,
      environment: "jsdom",
      exclude: [...configDefaults.exclude, "e2e/*"],
      root: fileURLToPath(new URL("./", import.meta.url)),
      server: {
        deps: { inline: ["vuetify"] },
      },
    },
  })
);
