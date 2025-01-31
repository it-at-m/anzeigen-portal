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
      const a = document.createElement("a");
      a.href =
        "data:" +
        getFileExtension(fileData.value.fileBase64) +
        ";base64," +
        fileData.value.fileBase64;
      a.download = fileData.value.name;
      a.click();
    }
  };
};

/**
 * Improve this later - use real magic numbers instead
 * @param value
 */
const getFileExtension = (value: string) => {
  /**
   * Bestimmt den MIME-Typ anhand des ersten Zeichens eines Strings.
   * @param {string} value - Der zu analysierende String.
   * @returns {string} Der erkannte MIME-Typ oder 'unknown' bei unbekanntem Typ.
   */
  const mapping: Record<string, string> = {
    "/": "image/jpeg",
    i: "image/png",
    R: "image/gif",
    U: "image/webp",
    J: "application/pdf",
  };

  return mapping[value.charAt(0)] || "unknown";
};
