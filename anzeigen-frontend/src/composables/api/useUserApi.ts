import type {
  CreateUserRequest,
  FindUserRequest,
  SwbUserTO,
} from "@/api/swbrett";
import type User from "@/types/User";

import { inject } from "vue";

import { getUser } from "@/api/user-client";
import { useApiCall } from "@/composables/api/useApiCall";
import { DEFAULT_API_KEY } from "@/composables/useApi";

export const useCreateUser = () => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  return useApiCall<CreateUserRequest, SwbUserTO>((params: CreateUserRequest) =>
    api.createUser(params)
  );
};

export const useFindUser = () => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const api = inject(DEFAULT_API_KEY)!;

  return useApiCall<FindUserRequest, SwbUserTO>((params: FindUserRequest) =>
    api.findUser(params)
  );
};

export const useUserInfo = () => {
  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useApiCall<void, User>(() => getUser());
};
