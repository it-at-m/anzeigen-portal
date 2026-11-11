import * as fs from "node:fs/promises";

import type { Plugin } from "vite";
import type { EditableTreeNode } from "vue-router/unplugin";

/**
 * This plugin ensures parameterized file-based routes (e.g. files like index.vue) are handled correctly through API gateway
 * when developing locally.
 * The plugin URL encodes brackets in the URL because API gateway (Netty webserver) cannot process brackets directly.
 * Currently, there are some issues open for Vite.js:
 * https://github.com/vitejs/vite/issues/10307
 * https://github.com/vitejs/vite/issues/20799
 *
 * @constructor
 */
export function EncodeBracketsPlugin(): Plugin {
  return {
    name: "vite-plugin-apigateway-bracket-fix",
    enforce: "pre",

    async resolveId(source, importer, options) {
      if (source.includes("%5B") || source.includes("%5D")) {
        // 1. Für den Vite-Resolver decodieren, damit die echte Datei gesucht wird
        const decodedSource = source
          .replace(/%5B/gi, "[")
          .replace(/%5D/gi, "]");

        // 2. Vite den echten Pfad auflösen lassen. Vite registriert das Modul intern
        // und wendet später automatisch den konfigurierten `base`-Pfad an.
        const resolved = await this.resolve(decodedSource, importer, {
          skipSelf: true,
          ...options,
        });

        if (resolved) {
          // 3. Den gefundenen Systempfad mit encodierten Brackets zurückgeben.
          return {
            ...resolved,
            id: resolved.id.replace(/\[/g, "%5B").replace(/\]/g, "%5D"),
          };
        }
      }
      return null;
    },

    async load(id) {
      if (id.includes("%5B") || id.includes("%5D")) {
        // id ist hier ein absoluter OS-Pfad. Für fs.readFile decodieren.
        const realPath = id.replace(/%5B/gi, "[").replace(/%5D/gi, "]");

        try {
          this.addWatchFile(realPath);
          return await fs.readFile(realPath, "utf-8");
        } catch (err) {
          console.error(
            "vite-plugin-apigateway-bracket-fix: Could not load file: ",
            realPath
          );
        }
      }
      return null;
    },
  };
}

export function extendRoute(route: EditableTreeNode): PromiseLike<void> | void {
  if (route.components) {
    for (const [viewName, componentPath] of route.components.entries()) {
      if (componentPath.includes("[") || componentPath.includes("]")) {
        const encodedPath = componentPath
          .replace(/\[/g, "%5B")
          .replace(/\]/g, "%5D");
        route.components.set(viewName, encodedPath);
      }
    }
  }
}
