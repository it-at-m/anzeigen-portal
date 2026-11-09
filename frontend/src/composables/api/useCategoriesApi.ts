import type {
  AdCategory,
  CreateAdCategoryRequest,
  DeleteAdCategoryRequest,
  UpdateAdCategoryRequest,
} from "@/api/swbrett";

import { useApiCall } from "@/composables/api/useApiCall";
import { ApiFactory } from "@/util/apiFactory.ts";

export const useGetCategories = () => {
  const api = ApiFactory.getInstance();

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<void, AdCategory[]>(() => api.getAllAdCategories());
};

export const useDeleteAdCategory = () => {
  const api = ApiFactory.getInstance();

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<DeleteAdCategoryRequest, void>((params) =>
    api.deleteAdCategory(params)
  );
};

export const useCreateAdCategory = () => {
  const api = ApiFactory.getInstance();

  return useApiCall<CreateAdCategoryRequest, AdCategory>((params) =>
    api.createAdCategory(params)
  );
};

export const useUpdateAdCategory = () => {
  const api = ApiFactory.getInstance();

  return useApiCall<UpdateAdCategoryRequest, AdCategory>((params) =>
    api.updateAdCategory(params)
  );
};
