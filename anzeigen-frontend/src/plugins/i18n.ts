import { createI18n } from "vue-i18n";

import swbrett from "@/locales/swbrett.json";

export const supportedLocales = ["swbrett", "gebrauchtwarenboerse"];

export type SupportedLocale = (typeof supportedLocales)[number];

type MessageSchema = typeof swbrett;

export const defaultLocale: SupportedLocale = "swbrett";

const i18n = createI18n<[MessageSchema], SupportedLocale>({
  locale: defaultLocale,
  fallbackLocale: defaultLocale,
  messages: {
    swbrett: Object.assign(swbrett),
  },
});
export default i18n;

const setI18nLanguage = (locale: SupportedLocale) => {
  // any typecast needed because typescript does not know composition mode of i18n is used
  (i18n.global.locale as any).value = locale;
  document.querySelector("html")?.setAttribute("lang", locale);
};
