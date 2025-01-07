import { computed } from "vue";
import { useRoute } from "vue-router";

const useMyBoard = () => {
  const route = useRoute();
  return computed(() => route.path === "/myboard");
};

export { useMyBoard };
