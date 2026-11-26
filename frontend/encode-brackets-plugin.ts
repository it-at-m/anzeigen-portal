import * as fs from "node:fs/promises";

import type { HotPayload, Plugin } from "vite";
import type { EditableTreeNode } from "vue-router/unplugin";

const BRACKET_RE = /[[\]]/;
const ENCODED_BRACKET_RE = /%5[bd]/i;

function encodeBrackets(value: string): string {
  return value.replace(/\[/g, "%5B").replace(/\]/g, "%5D");
}

function decodeBrackets(value: string): string {
  return value.replace(/%5B/gi, "[").replace(/%5D/gi, "]");
}

/**
 * Ensures parameterized file-based routes (e.g. `[id].vue`) work behind an API gateway
 * (Netty webserver) during local development (`serve` mode only).
 *
 * Netty rejects unencoded brackets (`[` / `]`) in request URLs with HTTP 400.
 * Working together with {@link extendRoute}, this plugin:
 * - Resolves and loads encoded module IDs (`%5B` / `%5D`) from their decoded filesystem path
 *   while ignoring virtual SFC sub-requests (`?...`) and watching the real file.
 * - Maps file-watcher events in `handleHotUpdate` from the unencoded file path back to the
 *   encoded entries in Vite's module graph.
 * - Intercepts outgoing Vite HMR updates (`server.environments.client.hot.send`) and encodes
 *   brackets in `path` and `acceptedPath` so browser HMR imports pass through the gateway.
 *
 * Open Vite.js issues:
 * - https://github.com/vitejs/vite/issues/10307
 * - https://github.com/vitejs/vite/issues/20799
 */
export function EncodeBracketsPlugin(): Plugin {
  return {
    name: "vite-plugin-apigateway-bracket-fix",
    apply: "serve",
    enforce: "pre",

    resolveId(source) {
      if (ENCODED_BRACKET_RE.test(source)) {
        return source;
      }
      return null;
    },

    async load(id) {
      if (id.includes("?") || !ENCODED_BRACKET_RE.test(id)) {
        return null;
      }

      const realPath = decodeBrackets(id);
      try {
        this.addWatchFile(realPath);
        return await fs.readFile(realPath, "utf-8");
      } catch {
        // eslint-disable-next-line no-console
        console.error(
          "vite-plugin-apigateway-bracket-fix: Could not load file: ",
          realPath
        );
        return null;
      }
    },

    handleHotUpdate({ file, server }) {
      if (!BRACKET_RE.test(file)) {
        return;
      }

      const mods = server.moduleGraph.getModulesByFile(encodeBrackets(file));
      if (mods && mods.size > 0) {
        return Array.from(mods);
      }
    },

    configureServer(server) {
      const { hot } = server.environments.client;
      const originalSend = hot.send.bind(hot);

      hot.send = (payload: HotPayload | string, customData?: unknown): void => {
        if (typeof payload === "string") {
          originalSend(payload, customData);
          return;
        }

        if (payload.type === "update") {
          for (const update of payload.updates) {
            update.path = encodeBrackets(update.path);
            update.acceptedPath = encodeBrackets(update.acceptedPath);
          }
        }

        originalSend(payload);
      };
    },
  };
}

/**
 * Encodes brackets in `vue-router/vite` route component paths (`[` / `]` -> `%5B` / `%5D`)
 * so initial route module requests pass through the API gateway.
 */
export function extendRoute(route: EditableTreeNode): void {
  if (!route.components) {
    return;
  }

  for (const [viewName, componentPath] of route.components.entries()) {
    if (BRACKET_RE.test(componentPath)) {
      route.components.set(viewName, encodeBrackets(componentPath));
    }
  }
}
