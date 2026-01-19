<template>
  <div>
    <quilly-editor
      ref="editor"
      v-model="modelValue"
      :options="options"
    />
  </div>
</template>

<script setup lang="ts">
import Quill from "quill";
import { onMounted, ref, watch } from "vue";
import { QuillyEditor } from "vue-quilly";

import "quill/dist/quill.snow.css";

const modelValue = defineModel<string>();
const { disabled } = defineProps<{
  disabled?: boolean;
}>();

const editor = ref<InstanceType<typeof QuillyEditor>>();

const options = {
  theme: "snow",
  placeholder: "Beschreibung",
  modules: {
    toolbar: [
      ["bold", "italic", "underline"],
      [{ header: [2, 3, false] }],
      [{ list: "ordered" }, { list: "bullet" }],
    ],
  },
};

let quill: Quill | undefined;

onMounted(() => {
  quill = editor.value?.initialize(Quill);
});

watch(
  () => disabled,
  () => {
    if (disabled) {
      quill?.disable();
    } else {
      quill?.enable();
    }
  }
);
</script>

<style scoped>
:deep(.ql-editor) {
  height: 150px;
}

:deep(.ql-toolbar) {
  border-top-left-radius: var(--border-radius);
  border-top-right-radius: var(--border-radius);
}

.ql-container {
  border-bottom-left-radius: var(--border-radius);
  border-bottom-right-radius: var(--border-radius);
}

.border-test {
  position: relative;
  display: flex;
  flex-direction: column;

  background-color: rgb(var(--v-theme-surface));
  border-radius: var(--border-radius);
}

/* Grundzustand: 1px Outline */
.border-test::before {
  content: "";
  position: absolute;
  inset: 0;
  border-radius: inherit;
  border: 1px solid rgba(var(--v-theme-on-surface), 0.38);
  pointer-events: none;
  box-sizing: border-box;
}

/* Hover (optional, Vuetify-like) */
.border-test:hover::before {
  border-color: rgba(var(--v-theme-on-surface), 0.6);
}

/* Fokus: 2px Accent-Ring, abgerundet, ON TOP */
.border-test:focus-within::after {
  content: "";
  position: absolute;
  inset: -1px; /* zentriert den 2px Ring */
  border-radius: calc(var(--border-radius) + 1px);
  border: 2px solid rgb(var(--v-theme-accent));
  pointer-events: none;
  box-sizing: border-box;
}
</style>
