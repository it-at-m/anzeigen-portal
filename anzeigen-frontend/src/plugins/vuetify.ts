import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";

import theme from "@variants/plugins/theme.ts";
import { createVuetify } from "vuetify";

export default createVuetify({
  theme: {
    defaultTheme: "light",
    variations: {
      colors: ["primary", "secondary", "accent"],
      lighten: 3,
      darken: 3,
    },
    themes: theme,
  },
});
