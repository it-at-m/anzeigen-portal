import { useGetFile } from "@/composables/api/useFilesApi.ts";

const { call: getFile, data: fileData } = useGetFile();

/**
 * Downloads a file based on the provided ID.
 * Retrieves the file, creates a Blob, and triggers a download.
 */
export const useDownloadFile = () => {
  /**
   * Downloads a file based on the provided ID.
   * Retrieves the file, creates a Blob, and triggers a download.
   * @param id - fileId to download
   */
  return async (id: number) => {
    await getFile({ id: id });

    if (fileData.value && fileData.value.fileBase64 && fileData.value.name) {
      const blob = new Blob([fileData.value?.fileBase64]);
      const fileURL = URL.createObjectURL(blob);
      const downloadLink = document.createElement("a");

      downloadLink.href = fileURL;
      downloadLink.download = fileData.value.name;
      document.body.appendChild(downloadLink);
      downloadLink.click();
    }
  };
};
