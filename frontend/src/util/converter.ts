import type { SwbFileTO } from "@/api/swbrett";

export const swbFileToFile = (swbFile: File) => {
  return new File(
    [new Uint8Array(swbFile.size || 0)],
    swbFile.name || "undefined",
    {
      type: "application/octet-stream",
    }
  );
};

export const fileToSwbFile = (file: File) => {
  const reader = new FileReader();
  reader.onloadend = function () {
    const result = (reader.result as string).split(",")[1];

    return {
      size: file.size,
      name: file.name,
      fileBase64: result,
    } as SwbFileTO;
  };

  reader.readAsDataURL(file);
};
