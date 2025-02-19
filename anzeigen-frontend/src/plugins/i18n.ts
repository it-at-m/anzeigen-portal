import { createI18n } from "vue-i18n";

import deDe from "@/locales/deDe.json";

export const supportedLocales = ["deDe"];

export type SupportedLocale = (typeof supportedLocales)[number];

type MessageSchema = typeof deDe;

export const defaultLocale: SupportedLocale = "swbrett";

const i18n = createI18n<[MessageSchema], SupportedLocale>({
  locale: defaultLocale,
  fallbackLocale: defaultLocale,
  messages: {
    swbrett: Object.assign(deDe),
  },
});
export default i18n;

const setI18nLanguage = (locale: SupportedLocale) => {
  // any typecast needed because typescript does not know composition mode of i18n is used
  (i18n.global.locale as any).value = locale;
  document.querySelector("html")?.setAttribute("lang", locale);
};
