import "vuetify/styles";

import type { VueI18nAdapterParams } from "vuetify/locale/adapters/vue-i18n";

import theme from "@variants/plugins/theme.ts";
import { useI18n } from "vue-i18n";
import { createVuetify } from "vuetify";
import { aliases, mdi } from "vuetify/iconsets/mdi-svg";
import { createVueI18nAdapter } from "vuetify/locale/adapters/vue-i18n";

import i18n from "@/plugins/i18n";

export default createVuetify({
  icons: {
    defaultSet: "mdi",
    aliases,
    sets: {
      mdi,
    },
  },
  theme: {
    defaultTheme: "light",
    variations: {
      colors: ["primary", "secondary", "accent"],
      lighten: 3,
      darken: 3,
    },
    themes: theme,
  },
  locale: {
    // @ts-expect-error false positive for type mismatch (no tsc compilation error)
    adapter: createVueI18nAdapter({ i18n, useI18n } as VueI18nAdapterParams),
  },
});
