<template>
  <v-field variant="outlined">
    <div class="w-100">
      <quilly-editor
        ref="editor"
        v-model="modelValue"
        :options="options"
        is-semantic-html-model
      />
    </div>
  </v-field>
</template>

<script setup lang="ts">
import Quill from "@mirohq/quill";
import { onMounted, ref, watch } from "vue";
import { QuillyEditor } from "vue-quilly";

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
      [{ list: "ordered" }, { list: "bullet" }],
    ],
  },
};

let quill: Quill | undefined;

onMounted(() => {
  // @ts-expect-error: vue-quilly expects the original type, but quilly is not supported anymore...
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

:deep(.ql-toolbar.ql-snow) {
  border-top: 0;
  border-inline: 0;
}

:deep(.ql-container.ql-snow) {
  border: 0;
}
</style>
