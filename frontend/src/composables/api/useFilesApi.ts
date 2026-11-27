import type {
  GetFileRequest,
  GetImageRequest,
  SanitizeImageRequest,
  SwbFileTO,
  SwbImageTO,
} from "@/apis/generated/anzeigen-backend";

import { DefaultApi } from "@/apis/generated/anzeigen-backend";
import { useApi } from "@/composables/api/useApi.ts";
import { ApiFactory } from "@/utils/apiFactory.ts";

export const useGetAdImage = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<GetImageRequest, SwbImageTO>((params: GetImageRequest) =>
    api.getImage(params)
  );
};

export const useGetFile = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<GetFileRequest, SwbFileTO>((params: GetFileRequest) =>
    api.getFile(params)
  );
};

export const useSanitizeImage = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<SanitizeImageRequest, string>((params: SanitizeImageRequest) =>
    api.sanitizeImage(params)
  );
};
