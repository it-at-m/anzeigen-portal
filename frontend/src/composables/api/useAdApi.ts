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
} from "@/apis/generated/anzeigen-backend";

import { DefaultApi } from "@/apis/generated/anzeigen-backend";
import { useApi } from "@/composables/api/useApi.ts";
import { ApiFactory } from "@/util/apiFactory.ts";

export const useUpdateAd = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<UpdateAdRequest, AdTO>((params: UpdateAdRequest) =>
    api.updateAd(params)
  );
};

export const useDeleteAd = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApi<DeleteAdRequest, void>((params: DeleteAdRequest) =>
    api.deleteAd(params)
  );
};

export function useCreateAd() {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<CreateAdRequest, AdTO>((params: CreateAdRequest) =>
    api.createAd(params)
  );
}

export function useGetAd() {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<GetAdRequest, AdTO>((params: GetAdRequest) =>
    api.getAd(params)
  );
}

export function useGetAds() {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<GetAdsRequest, GetAds200Response>((params: GetAdsRequest) =>
    api.getAds(params)
  );
}

export function useDeactivateAd() {
  const api = ApiFactory.getInstance(DefaultApi);

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApi<DeactivateAdRequest, void>((params: DeactivateAdRequest) =>
    api.deactivateAd(params)
  );
}

export function useIncrementAdView() {
  const api = ApiFactory.getInstance(DefaultApi);

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApi<IncrementViewRequest, void>((params: IncrementViewRequest) =>
    api.incrementView(params)
  );
}
