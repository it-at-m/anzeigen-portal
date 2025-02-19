import { createI18n } from "vue-i18n";

import de from "@/locales/de.json";

export const supportedLocales = ["deDe"];

export type SupportedLocale = (typeof supportedLocales)[number];

type MessageSchema = typeof de;

export const defaultLocale: SupportedLocale = "de";

const i18n = createI18n<[MessageSchema], SupportedLocale>({
  locale: defaultLocale,
  fallbackLocale: defaultLocale,
  messages: {
    de: Object.assign(de),
  },
});
export default i18n;

const setI18nLanguage = (locale: SupportedLocale) => {
  // any typecast needed because typescript does not know composition mode of i18n is used
  (i18n.global.locale as any).value = locale;
  document.querySelector("html")?.setAttribute("lang", locale);
};
