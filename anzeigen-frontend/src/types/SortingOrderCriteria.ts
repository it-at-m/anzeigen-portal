type Criteria = "title" | "price" | "creationDate";

interface CriteriaValue {
  criteria: Criteria;
  order: "asc" | "desc";
}

interface SortingOrder {
  title: string;
  value: CriteriaValue;
}

export type { Criteria, CriteriaValue, SortingOrder };
