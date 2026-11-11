import type {
  CreateUserRequest,
  FindUserRequest,
  GetUserRequest,
  SwbUserTO,
} from "@/apis/generated/anzeigen-backend";

import { DefaultApi } from "@/apis/generated/anzeigen-backend";
import { useApi } from "@/composables/api/useApi.ts";
import { ApiFactory } from "@/util/apiFactory.ts";

export const useCreateUser = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<CreateUserRequest, SwbUserTO>((params: CreateUserRequest) =>
    api.createUser(params)
  );
};

export const useFindUser = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<FindUserRequest, SwbUserTO>((params: FindUserRequest) =>
    api.findUser(params)
  );
};

export const useGetUser = () => {
  const api = ApiFactory.getInstance(DefaultApi);

  return useApi<GetUserRequest, SwbUserTO>((params: GetUserRequest) =>
    api.getUser(params)
  );
};
