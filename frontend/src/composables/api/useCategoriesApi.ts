import type {
  AdCategory,
  CreateAdCategoryRequest,
  DeleteAdCategoryRequest,
  UpdateAdCategoryRequest,
} from "@/apis/generated/anzeigen-backend";

import { DefaultApi } from "@/apis/generated/anzeigen-backend";
import { useApi } from "@/composables/api/useApi.ts";
import { ApiFactory } from "@/utils/apiFactory.ts";

export const useGetCategories = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApi<void, AdCategory[]>(() => api.getAllAdCategories());
};

export const useDeleteAdCategory = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApi<DeleteAdCategoryRequest, void>((params) =>
    api.deleteAdCategory(params)
  );
};

export const useCreateAdCategory = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<CreateAdCategoryRequest, AdCategory>((params) =>
    api.createAdCategory(params)
  );
};

export const useUpdateAdCategory = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<UpdateAdCategoryRequest, AdCategory>((params) =>
    api.updateAdCategory(params)
  );
};
