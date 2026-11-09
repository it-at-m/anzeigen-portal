<template>
  <v-dialog
    :model-value="modelValue"
    persistent
    width="800"
  >
    <template #activator="{ props: open }">
      <slot :activator="open" />
    </template>
    <v-card :loading="loading">
      <v-card-title>
        {{ dialogtitle }}
      </v-card-title>
      <v-card-text>
        {{ dialogtext }}
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn
          id="yesnodialog-btn-no"
          variant="flat"
          color="accent"
          :disabled="loading"
          @click="no"
        >
          Nein
        </v-btn>
        <v-btn
          id="yesnodialog-btn-yes"
          color="accent"
          variant="outlined"
          :disabled="loading"
          @click="yes"
        >
          Ja
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
/**
 * The YesNo dialog is a generic dialog for yes/no queries to the user.
 * For example, it can be used to confirm the deletion of an entity.
 *
 * As confirming an action is usually associated with a button, the YesNoDialog offers this at the same time. This can be configured via `buttontext` and `icontext`.
 *
 * If both `buttontext` and `icontext` are not set, the YesNoDialog can also be used as a pure dialog. In this case, the value is passed through from the dialog.
 *
 * Confirmation of the dialog is signalled via a `yes` event. Similarly, the
 * rejection is signaled by a `no` event.
 *
 * Example:
 * <yes-no-dialog
 *    v-model="deleteDialog"
 *    buttontext="Delete"
 *    dialogtitle="Delete?"
 *    dialogtext="Do you really want to delete the entity?"
 *    @no="deleteDialog = false"
 *    @yes="deleteSome"></yes-no-dialog>
 */

const modelValue = defineModel<boolean>();

defineProps<{
  /**
   * Control flag for dialog
   */
  dialogtitle: string;
  dialogtext: string;
  loading?: boolean;
}>();

const emits = defineEmits<{
  no: [];
  yes: [];
}>();

function no(): void {
  emits("no");
}
function yes(): void {
  emits("yes");
}
</script>
