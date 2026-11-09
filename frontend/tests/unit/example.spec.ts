import { shallowMount } from "@vue/test-utils";
import { createPinia } from "pinia";
import { beforeAll, beforeEach, describe, expect, it } from "vitest";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";

import TheSnackbarQueue from "../../src/components/TheSnackbarQueue.vue";

const pinia = createPinia();

describe("TheSnackbar.vue", () => {
  let vuetify: ReturnType<typeof createVuetify>;

  beforeAll(() => {
    createPinia();
    createVuetify();
  });

  beforeEach(() => {
    vuetify = createVuetify({
      components,
      directives,
    });
  });

  it("renders props.message when passed", () => {
    const message = "Hello_World";
    const wrapper = shallowMount(TheSnackbarQueue, {
      global: {
        plugins: [pinia, vuetify],
      },
      props: { message: message },
    });

    expect(wrapper.html()).toContain(message);
  });
});
