import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";

import theme from "@variants/plugins/theme.ts";
import { createVuetify } from "vuetify";

export default createVuetify({
  theme: {
    themes: theme,
  },
});
