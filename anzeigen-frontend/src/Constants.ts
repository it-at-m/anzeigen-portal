import type { AdTO, SwbUserTO } from "@/api/swbrett";
import type { InjectionKey, Ref } from "vue";

export const ROUTES_BOARD = "board";
export const ROUTES_MYBOARD = "myboard";
export const ROUTES_AD = "ad";
export const ROUTES_ADMIN = "admin";
export const ROUTES_GETSTARTED = "getstarted";

export const AD2IMAGE_URL = import.meta.env.VITE_AD2IMAGE_URL;
export const APPSWITCHER_URL = import.meta.env.VITE_APPSWITCHER_URL;

export const ROUTER_TYPE = "hash";

/**
 * EventBus Identifiers
 */
export const EV_EDIT_AD_DIALOG = "eventbus-dialog";
export const EV_SNACKBAR = "eventbus-snackbar";
export const EV_CLEAR_CACHE = "eventBus-clear-cache";
export const EV_UPDATE_AD_LIST = "eventBus-update-ad-list";

/**
 * Messages
 */
export const API_ERROR_MSG =
  "Ein Fehler ist aufgetreten. Bitte aktualisieren Sie die Seite oder versuchen Sie es später erneut.";
export const CREATE_AD_SUCCESS = "Die Anzeige wurde erfolgreich erstellt.";
export const UPDATE_AD_SUCCESS = "Die Anzeige wurde erfolgreich aktualisiert.";
export const FILE_TYPE_INCORRECT =
  "Das ausgewählte Bild wird nicht unterstützt.";
export const DELETE_AD_SUCCESS = "Die Anzeige wurde erfolgreich gelöscht.";
export const FILE_SIZE_TO_BIG = (size: string) =>
  "Die ausgewählte(n) Datei(n) ist / sind zu groß. Maximal " + size + "MB.";
export const TOO_MANY_FILES = (amount: string) =>
  "Die Anzahl der Dateien ist überschritten. Maximal " + amount + " Stück.";

/**
 * Injection Keys
 */
export const IK_IS_MYBOARD: InjectionKey<Readonly<Ref<boolean>>> = Symbol(
  "injection-key-my-board"
);

/**
 * Other constants
 */
export const ADMIN_AUTHORITIES = [
  "BACKEND_READ_THEENTITY",
  "BACKEND_WRITE_THEENTITY",
  "BACKEND_DELETE_THEENTITY",
];
export const ALLOWED_FILE_TYPES =
  "image/png, image/jpeg, image/jpg, application/pdf";
export const ALLOWED_IMAGE_TYPES = "image/png, image/jpeg, image/jpg";
export const AD_MAX_TITLE_LENGTH = 40;
export const DATE_DISPLAY_FORMAT = "DD.MM.YYYY"; // use this in conjunction with useDateFormat
export const DEFAULT_BOARD_QUERIES = {
  sortBy: "title",
  order: "asc",
};
export const PREVIEW_IMAGE_FILE_URI_PREFIX = "data:image/jpeg;base64,";
export const QUERY_NAME_ORDER = "order";
export const QUERY_NAME_SORTBY = "sortBy";
export const QUERY_NAME_TYPE = "type";
export const QUERY_NAME_CATEGORYID = "categoryId";
export const QUERY_NAME_USERID = "userId";

export const EMPTY_ADTO_OBJECT = {
  adType: "OFFER",
  swbUser: {} as SwbUserTO,
  adFiles: [],
  price: 1,
} as AdTO;
