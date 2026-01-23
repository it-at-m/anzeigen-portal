import { createApp } from "vue";

import "@/assets/styles/variables.css";

import App from "@/App.vue";
import { registerPlugins } from "@/plugins";

const app = createApp(App);

registerPlugins(app);

app.mount("#app");
