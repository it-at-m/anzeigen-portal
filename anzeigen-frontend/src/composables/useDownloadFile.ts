import { useGetFile } from "@/composables/api/useFilesApi.ts";

const { call: getFile, data: fileData } = useGetFile();

/**
 * Downloads a file based on the provided ID.
 * Retrieves the file, creates a Blob, and triggers a download.
 */
export const useDownloadFile = () => {
  // TODO: not working correctly! sth wrong with base64 decoding or encoding
  /**
   * Downloads a file based on the provided ID.
   * Retrieves the file, creates a Blob, and triggers a download.
   * @param id - fileId to download
   */
  return async (id: number) => {
    await getFile({ id: id });

    if (fileData.value && fileData.value.fileBase64 && fileData.value.name) {
      const blob = new Blob([window.atob(fileData.value?.fileBase64)], {
        type: "application/pdf",
      });
      console.log(blob.size, ":", fileData.value.fileBase64.length);
      const fileURL = URL.createObjectURL(blob);
      const downloadLink = document.createElement("a");

      downloadLink.href = fileURL;
      downloadLink.download = fileData.value.name;
      document.body.appendChild(downloadLink);
      downloadLink.click();
    }
  };
};
