import type {
  CreateUserRequest,
  FindUserRequest,
  SwbUserTO,
} from "@/api/swbrett";
import type User from "@/types/User";

import { getUser } from "@/api/user-client";
import { useApiCall } from "@/composables/api/useApiCall";
import { ApiFactory } from "@/util/apiFactory.ts";

export const useCreateUser = () => {
  const api = ApiFactory.getInstance();

  return useApiCall<CreateUserRequest, SwbUserTO>((params: CreateUserRequest) =>
    api.createUser(params)
  );
};

export const useFindUser = () => {
  const api = ApiFactory.getInstance();

  return useApiCall<FindUserRequest, SwbUserTO>((params: FindUserRequest) =>
    api.findUser(params)
  );
};

export const useUserInfo = () => {
  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<void, User>(() => getUser());
};
