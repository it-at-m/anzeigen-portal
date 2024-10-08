import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";

import { createVuetify } from "vuetify";

export default createVuetify({
  theme: {
    themes: {
      light: {
        colors: {
          primary: "#333333",
          secondary: "#e8e8e8",
          accent: "#3b5961",
          success: "#69BE28",
          error: "#FF0000",
        },
      },
    },
  },
});
