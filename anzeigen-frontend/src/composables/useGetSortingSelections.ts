import type { SortingOrder } from "@/types/SortingOrderCriteria";

export const sortingOrderSelections = [
  { title: "Titel (alphabetisch)", value: { criteria: "titel", order: "asc" } },
  { title: "Preis aufsteigend", value: { criteria: "price", order: "asc" } },
  { title: "Preis absteigend", value: { criteria: "price", order: "desc" } },
  {
    title: "Erstellungsdatum aufsteigend",
    value: { criteria: "creationDate", order: "asc" },
  },
  {
    title: "Erstellungsdatum absteigend",
    value: { criteria: "creationDate", order: "desc" },
  },
] as SortingOrder[];

export const useIsValidOrderSelection = (selectionToCheck: {
  criteria: string;
  order: string;
}) =>
  sortingOrderSelections.some(
    (selection) =>
      selection.value.criteria === selectionToCheck.criteria &&
      selection.value.order === selectionToCheck.order
  );
