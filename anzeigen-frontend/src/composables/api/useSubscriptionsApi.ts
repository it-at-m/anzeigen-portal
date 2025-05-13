import type {
  CreateUserSubscriptionRequest,
  DeleteUserSubscriptionRequest,
  SubscriptionTO,
} from "@/api/swbrett";

import { useApiCall } from "@/composables/api/useApiCall";
import { ApiFactory } from "@/util/apiFactory.ts";

export const useCreateSubscription = () => {
  const api = ApiFactory.getInstance();

  return useApiCall<CreateUserSubscriptionRequest, SubscriptionTO>((params) =>
    api.createUserSubscription(params)
  );
};

export const useDeleteSubscription = () => {
  const api = ApiFactory.getInstance();

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<DeleteUserSubscriptionRequest, void>((params) =>
    api.deleteUserSubscription(params)
  );
};

export const useGetSubscriptions = () => {
  const api = ApiFactory.getInstance();

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<void, SubscriptionTO[]>(() => api.getUserSubscriptions());
};
