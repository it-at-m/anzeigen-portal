import { dirname, resolve } from "node:path";
import { fileURLToPath, URL } from "node:url";

import VueI18nPlugin from "@intlify/unplugin-vue-i18n/vite";
import vue from "@vitejs/plugin-vue";
import UnpluginFonts from "unplugin-fonts/vite";
import { defineConfig, loadEnv } from "vite";
import vueDevTools from "vite-plugin-vue-devtools";
import vuetify, { transformAssetUrls } from "vite-plugin-vuetify";
import VueRouter from "vue-router/vite";

import { EncodeBracketsPlugin, extendRoute } from "./encode-brackets-plugin.ts";

// https://vitejs.dev/config/
export default defineConfig(({ command, mode }) => {
  const env = loadEnv(mode, process.cwd(), "") as Record<string, string> &
    ImportMetaEnv;
  const variant = env.VITE_APP_VARIANT || (mode as "swb" | "gbr");
  const isDevelopment = command === "serve";

  return {
    base: isDevelopment ? `/${variant}/` : "/",
    plugins: [
      VueRouter({
        routesFolder: {
          src: "src/routes",
        },
        dts: "./route-map.d.ts",
        ...(isDevelopment && { extendRoute }),
      }),
      vue({
        template: { transformAssetUrls },
        features: {
          optionsAPI: isDevelopment,
        },
      }),
      vuetify(),
      UnpluginFonts({
        inlineFontFace: true,
        fontsource: {
          families: [
            {
              name: "Roboto",
              weights: [100, 300, 400, 500, 700, 900],
              subset: "latin",
              fallback: {
                category: "sans-serif",
              },
            },
          ],
        },
      }),
      vueDevTools(),
      VueI18nPlugin({
        include: resolve(
          dirname(fileURLToPath(import.meta.url)),
          "./src/locales/*.json"
        ),
      }),
      {
        ...EncodeBracketsPlugin(),
        apply: "serve", // Ensures plugin is only applied during serve, see https://vite.dev/guide/using-plugins#conditional-application
      },
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
    preview: {
      port: 8084,
      root: "dist",
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
