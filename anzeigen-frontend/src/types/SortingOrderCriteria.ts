/**
 * Types of sorting criteria.
 */
type Criteria = "title" | "price" | "creationDate";

/**
 * Represents the value of a sorting criterion with its order.
 */
interface CriteriaValue {
  criteria: Criteria;
  order: "asc" | "desc";
}

/**
 * Represents a sorting option with a title and its corresponding value.
 */
interface SortingOrder {
  title: string;
  value: CriteriaValue;
}

export type { Criteria, CriteriaValue, SortingOrder };
