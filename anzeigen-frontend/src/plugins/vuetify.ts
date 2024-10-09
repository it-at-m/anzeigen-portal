import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";

import { createVuetify } from "vuetify";

export default createVuetify({
  theme: {
    themes: {
      light: {
        colors: {
          background: "#e8e8e8",
          primary: "#333333",
          secondary: "#e8e8e8",
          accent: "#3b5961",
          success: "#4caf50",
          error: "#ef5350",
          info: "#03a9f4",
          warning: "#ff9800",
        },
      },
    },
  },
});
