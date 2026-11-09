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

import { useApiCall } from "@/composables/api/useApiCall";
import { ApiFactory } from "@/util/apiFactory.ts";

export const useUpdateAd = () => {
  const api = ApiFactory.getInstance();

  return useApiCall<UpdateAdRequest, AdTO>((params: UpdateAdRequest) =>
    api.updateAd(params)
  );
};

export const useDeleteAd = () => {
  const api = ApiFactory.getInstance();

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<DeleteAdRequest, void>((params: DeleteAdRequest) =>
    api.deleteAd(params)
  );
};

export function useCreateAd() {
  const api = ApiFactory.getInstance();

  return useApiCall<CreateAdRequest, AdTO>((params: CreateAdRequest) =>
    api.createAd(params)
  );
}

export function useGetAd() {
  const api = ApiFactory.getInstance();

  return useApiCall<GetAdRequest, AdTO>((params: GetAdRequest) =>
    api.getAd(params)
  );
}

export function useGetAds() {
  const api = ApiFactory.getInstance();

  return useApiCall<GetAdsRequest, GetAds200Response>((params: GetAdsRequest) =>
    api.getAds(params)
  );
}

export function useDeactivateAd() {
  const api = ApiFactory.getInstance();

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<DeactivateAdRequest, void>((params: DeactivateAdRequest) =>
    api.deactivateAd(params)
  );
}

export function useIncrementAdView() {
  const api = ApiFactory.getInstance();

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<IncrementViewRequest, void>(
    (params: IncrementViewRequest) => api.incrementView(params)
  );
}
