// Plugins
import { fileURLToPath, URL } from "node:url";

import vue from "@vitejs/plugin-vue";
import ViteFonts from "unplugin-fonts/vite";
import { defineConfig, loadEnv } from "vite";
import vueDevTools from "vite-plugin-vue-devtools";
import vuetify, { transformAssetUrls } from "vite-plugin-vuetify";

import { ImportMetaEnv } from "./env";

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), "") as unknown as ImportMetaEnv;
  const variant = env.VITE_APP_VARIANT || (mode as "swb" | "gbr");

  return {
    base: env.VITE_BASE_PATH || `/${variant}/`,
    plugins: [
      vue({
        template: { transformAssetUrls },
        features: {
          optionsAPI: false,
        },
      }),
      vuetify(),
      vueDevTools(),
      ViteFonts({
        google: {
          families: [
            {
              name: "Roboto",
              styles: "wght@100;300;400;500;700;900",
            },
          ],
        },
      }),
    ],
    server: {
      host: true,
      port: 8081,
      proxy: {
        "/api": "http://localhost:8083",
        "/actuator": "http://localhost:8083",
      },
      allowedHosts: ["host.docker.internal"], // required to use frontend behind proxy (e.g. API Gateway)
      headers: {
        "x-frame-options": "SAMEORIGIN", // required to use devtools behind proxy (e.g. API Gateway)
      },
    },
    resolve: {
      alias: {
        "@variants": fileURLToPath(
          new URL(`./src/variants/${variant}`, import.meta.url)
        ),
        "@": fileURLToPath(new URL("./src", import.meta.url)),
      },
      extensions: [".js", ".json", ".jsx", ".mjs", ".ts", ".tsx", ".vue"],
    },
    build: {
      minify: true,
      outDir: `dist/${variant}`,
      emptyOutDir: false,
    },
    define: {
      "process.env": {},
    },
  };
});
