import type {
  AdTO,
  CreateAdRequest,
  DeleteAdRequest,
  UpdateAdRequest,
} from "@/api/swbrett";

import { inject } from "vue";

import { useApiCall } from "@/composables/api/useApiCall";
import { DEFAULT_API_KEY } from "@/composables/useApi";

export const useUpdateAd = () => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  return useApiCall<UpdateAdRequest, AdTO>((params: UpdateAdRequest) =>
    api.updateAd(params)
  );
};

export const useDeleteAd = () => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<DeleteAdRequest, void>((params: DeleteAdRequest) =>
    api.deleteAd(params)
  );
};

export function useCreateAd() {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  return useApiCall<CreateAdRequest, AdTO>((params: CreateAdRequest) =>
    api.createAd(params)
  );
}
