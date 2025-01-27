import type {
  GetFileRequest,
  GetImageRequest,
  SanitizeImageRequest,
  SwbFileTO,
  SwbImageTO,
} from "@/api/swbrett";

import { inject } from "vue";

import { useApiCall } from "@/composables/api/useApiCall";
import { DEFAULT_API_KEY } from "@/composables/useApi";

export const useGetAdImage = () => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  return useApiCall<GetImageRequest, SwbImageTO>((params: GetImageRequest) =>
    api.getImage(params)
  );
};

export const useGetFile = () => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  return useApiCall<GetFileRequest, SwbFileTO>((params: GetFileRequest) =>
    api.getFile(params)
  );
};

export const useSanitizeImage = () => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  return useApiCall<SanitizeImageRequest, string>(
    (params: SanitizeImageRequest) => api.sanitizeImage(params)
  );
};
