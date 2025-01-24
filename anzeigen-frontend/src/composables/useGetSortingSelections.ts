import type { SortingOrder } from "@/types/SortingOrderCriteria";

/**
 * A list of predefined sorting options for selection.
 */
export const sortingOrderSelections = [
  { title: "Titel (alphabetisch)", value: { criteria: "title", order: "asc" } },
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

/**
 * Validates if a given selection matches any predefined sorting option.
 * @param selectionToCheck - The sorting criteria and order to validate.
 * @returns `true` if the selection is valid, otherwise `false`.
 */
export const useIsValidOrderSelection = (selectionToCheck: {
  criteria: string;
  order: string;
}) =>
  sortingOrderSelections.some(
    (selection) =>
      selection.value.criteria === selectionToCheck.criteria &&
      selection.value.order === selectionToCheck.order
  );
