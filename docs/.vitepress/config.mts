import { defineConfig } from "vitepress";
import { withMermaid } from "vitepress-plugin-mermaid";

// https://vitepress.dev/reference/site-config
const vitepressConfig = defineConfig({
  title: "Anzeigen Portal Docs",
  description: "Documentation for the Anzeigen Portal project",
  base: "/anzeigen-portal/",
  head: [
    [
      "link",
      {
        rel: "icon",
        href: `https://assets.muenchen.de/logos/lhm/icon-lhm-muenchen-32.png`,
      },
    ],
  ],
  lastUpdated: true,
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: "Home", link: "/" },
      {
        text: "Docs",
        items: [
          { text: "Frontend", link: "/frontend" },
          { text: "Backend", link: "/backend" },
        ],
      },
    ],
    sidebar: [
      { text: "Frontend", link: "/frontend" },
      { text: "Backend", link: "/backend" },
    ],
    socialLinks: [
      { icon: "github", link: "https://github.com/it-at-m/anzeigen-portal" },
    ],
    editLink: {
      pattern:
        "https://github.com/it-at-m/anzeigen-portal/blob/main/docs/:path",
      text: "View this page on GitHub",
    },
    footer: {
      message: `<a href="https://opensource.muenchen.de/impress.html">Impress and Contact</a>`,
    },
    outline: {
      level: "deep",
    },
    search: {
      provider: "local",
    },
  },
  markdown: {
    image: {
      lazyLoading: true,
    },
  },
});

export default withMermaid(vitepressConfig);
