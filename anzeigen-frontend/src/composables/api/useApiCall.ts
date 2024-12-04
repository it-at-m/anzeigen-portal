import { readonly, ref } from "vue";

export function useApiCall<TRequest, TResponse = void>(
  apiMethod: (params: TRequest) => Promise<TResponse>
) {
  const loadingInternal = ref(false);
  const errorInternal = ref(false);
  const dataInternal = ref<TResponse | null>(null);

  const loading = readonly(loadingInternal);
  const error = readonly(errorInternal);
  const data = readonly(dataInternal);

  const call = (params: TRequest): Promise<TResponse | boolean> => {
    loadingInternal.value = true;
    errorInternal.value = false;

    return apiMethod(params)
      .then((data) => (dataInternal.value = data))
      .catch(() => (errorInternal.value = true))
      .finally(() => (loadingInternal.value = false));
  };

  return {
    loading,
    error,
    data,
    call,
  };
}
