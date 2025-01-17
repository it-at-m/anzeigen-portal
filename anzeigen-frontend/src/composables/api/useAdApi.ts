import type {
  AdTO,
  CreateAdRequest,
  DeactivateAdRequest,
  DeleteAdRequest,
  GetAdRequest,
  GetAds200Response,
  GetAdsRequest,
  IncrementViewRequest,
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

export function useGetAd() {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  return useApiCall<GetAdRequest, AdTO>((params: GetAdRequest) =>
    api.getAd(params)
  );
}

export function useGetAds() {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  return useApiCall<GetAdsRequest, GetAds200Response>((params: GetAdsRequest) =>
    api.getAds(params)
  );
}

export function useDeactivateAd() {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<DeactivateAdRequest, void>((params: DeactivateAdRequest) =>
    api.deactivateAd(params)
  );
}

export function useIncrementAdView() {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<IncrementViewRequest, void>(
    (params: IncrementViewRequest) => api.incrementView(params)
  );
}
