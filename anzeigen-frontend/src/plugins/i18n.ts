import { nextTick } from "vue";
import { createI18n } from "vue-i18n";

import de from "@/locales/de.json";

export const supportedLocales = ["deDe"];

export type SupportedLocale = (typeof supportedLocales)[number];

type MessageSchema = typeof de;

export const defaultLocale: SupportedLocale = "de";

const loadedLanguages: SupportedLocale[] = [defaultLocale];

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
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  (i18n.global.locale as any).value = locale;
  document.querySelector("html")?.setAttribute("lang", locale);
};

/**
 * load another language on demand - language needs to be registered
 * @param locale one of the registered languages
 */
export const loadLanguage = async (locale: SupportedLocale) => {
  if (!supportedLocales.includes(locale)) {
    throw new Error("Language not supported");
  }

  // any typecast needed because typescript does not know composition mode of i18n is used
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  if ((i18n.global.locale as any).value === locale) {
    return nextTick();
  }

  if (loadedLanguages.includes(locale)) {
    setI18nLanguage(locale);
    return nextTick();
  }

  const messages = await import(`@/locales/${locale}.json`);
  i18n.global.setLocaleMessage<MessageSchema>(locale, messages.default);
  setI18nLanguage(locale);

  return nextTick();
};
