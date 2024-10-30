type Criteria = "titel" | "price" | "creationDate";

type CriteriaValue = {
  criteria: Criteria;
  order: "asc" | "desc";
};

type SortingOrder = {
  title: string;
  value: CriteriaValue;
};

export type { Criteria, CriteriaValue, SortingOrder };
