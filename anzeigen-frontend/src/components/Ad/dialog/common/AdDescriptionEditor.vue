<template>
  <div class="quill-input">
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
  height: 125px;
}

/* Toolbar */
:deep(.ql-toolbar) {
  border-top: none;
  border-left: none;
  border-right: none;
  border-radius: 0;
}

/* Container */
:deep(.ql-container) {
  border: none;
  border-radius: 0;
}

/* Default */
:deep(.ql-snow.ql-toolbar .ql-stroke) {
  stroke: rgba(var(--v-theme-primary), 0.7);
}

/* Hover */
:deep(.ql-snow.ql-toolbar button:hover .ql-stroke) {
  stroke: rgba(var(--v-theme-primary));
}

:deep(.ql-snow.ql-toolbar .ql-picker-label:hover) {
  color: rgba(var(--v-theme-primary));
}
:deep(.ql-snow.ql-toolbar .ql-picker-item:hover) {
  color: rgba(var(--v-theme-accent));
}
:deep(.ql-snow.ql-toolbar .ql-picker-label:hover .ql-stroke) {
  stroke: rgba(var(--v-theme-primary));
}

/* Aktiv */
:deep(.ql-snow.ql-toolbar button.ql-active .ql-stroke) {
  stroke: rgb(var(--v-theme-accent));
}

:deep(.ql-snow.ql-toolbar button.ql-active .ql-fill) {
  fill: rgb(var(--v-theme-accent));
}
:deep(.ql-snow.ql-toolbar .ql-picker-item.ql-selected) {
  color: rgb(var(--v-theme-accent));
}
:deep(.ql-snow.ql-toolbar .ql-picker-label.ql-active) {
  color: rgb(var(--v-theme-accent));
}
:deep(.ql-snow.ql-toolbar .ql-picker-label.ql-active .ql-stroke) {
  stroke: rgb(var(--v-theme-accent));
}

/* Vuetify Hover and Fokus Look - Border specific */
.quill-input {
  position: relative;
  border-radius: var(--border-radius);
  background-color: rgb(var(--v-theme-surface));
}

.quill-input::before {
  content: "";
  position: absolute;
  inset: 0;
  border-radius: inherit;
  border: 1px solid rgba(var(--v-theme-primary), 0.38);
  pointer-events: none;
}

.quill-input:hover::before {
  border-color: rgba(var(--v-theme-primary), 0.6);
}

.quill-input:focus-within::before {
  border-color: transparent;
}

.quill-input:focus-within::after {
  content: "";
  position: absolute;
  inset: 0;
  border-radius: inherit;
  border: 2px solid rgb(var(--v-theme-accent));
  pointer-events: none;
}
</style>
