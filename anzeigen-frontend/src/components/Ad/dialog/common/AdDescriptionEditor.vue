<template>
  <quilly-editor
    ref="editor"
    v-model="content"
    :options="options"
    @blur="saveContent"
  />
</template>

<script setup lang="ts">
import Quill from "quill";
import { onMounted, ref } from "vue";
import { QuillyEditor } from "vue-quilly";

import "quill/dist/quill.snow.css";

const editor = ref<InstanceType<typeof QuillyEditor>>();
const content = ref("<p>Hello Quilly!</p>");
let quill: Quill | undefined;

const options = {
  theme: "snow",
  placeholder: "Beschreibung",
  modules: {
    toolbar: [
      ["bold", "italic", "underline"],
      [{ header: [1, 2, false] }],
      [{ list: "ordered" }, { list: "bullet" }],
    ],
  },
};

onMounted(() => {
  quill = editor.value?.initialize(Quill);

  const container = quill?.root.closest(".ql-container") as HTMLElement | null;
  const toolbar = container?.previousElementSibling as HTMLElement | null;

  // Rounded (Vuetify Utilities)
  container?.classList.add("rounded-b", "v-input");
  toolbar?.classList.add("rounded-t");

  // Vuetify-like border & background
  const borderColor = "rgba(var(--v-theme-on-surface), 0.38)";
  const bgColor = "rgb(var(--v-theme-surface))";

  //container?.style.setProperty("border-color", borderColor);
  toolbar?.style.setProperty("border-color", borderColor);

  container?.style.setProperty("background-color", bgColor);
});

const saveContent = (quillInstance: Quill) => {
  console.log(quillInstance);
};
</script>

<style></style>
