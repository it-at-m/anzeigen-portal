import type {
  GetFileRequest,
  GetImageRequest,
  SanitizeImageRequest,
  SwbFileTO,
  SwbImageTO,
} from "@/api/swbrett";

import { useApiCall } from "@/composables/api/useApiCall";
import { ApiFactory } from "@/util/apiFactory.ts";

export const useGetAdImage = () => {
  const api = ApiFactory.getInstance();

  return useApiCall<GetImageRequest, SwbImageTO>((params: GetImageRequest) =>
    api.getImage(params)
  );
};

export const useGetFile = () => {
  const api = ApiFactory.getInstance();

  return useApiCall<GetFileRequest, SwbFileTO>((params: GetFileRequest) =>
    api.getFile(params)
  );
};

export const useSanitizeImage = () => {
  const api = ApiFactory.getInstance();

  return useApiCall<SanitizeImageRequest, string>(
    (params: SanitizeImageRequest) => api.sanitizeImage(params)
  );
};
