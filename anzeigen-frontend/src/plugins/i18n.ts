// src/plugins/i18n.ts
import { nextTick } from "vue";
import { createI18n } from "vue-i18n";

import de from "@/locales/de.json";

// Variante anhand der Environment-Variable oder des Build-Modes
//const VARIANT = import.meta.env.VITE_APP_VARIANT as "gbr" | "swb" | undefined;

// Alle verfügbaren Überschreibungen im Variantenordner laden (falls vorhanden)
const variantOverrides = import.meta.glob("@variants/locales/*.json", {
  eager: true,
  import: "default",
}) as Record<string, Record<string, unknown>>;

function resolveVariantMessages(locale: string): Record<string, unknown> {
  const match = Object.keys(variantOverrides).find((p) =>
    p.endsWith(`/${locale}.json`)
  );
  return match ? variantOverrides[match] : {};
}

export const supportedLocales = ["deDe"];
export type SupportedLocale = (typeof supportedLocales)[number];

export const defaultLocale: SupportedLocale = "de";

//const loadedLanguages: SupportedLocale[] = [defaultLocale];

type MessageSchema = typeof de;

// load variant overrides (if any)
const variantLocales = import.meta.glob<Record<string, unknown>>(
  "@variants/locales/*.json",
  {
    eager: true,
    import: "default",
  }
);

function getVariantOverrides(locale: string) {
  const entry = Object.keys(variantLocales).find((p) =>
    p.endsWith(`/${locale}.json`)
  );
  return entry ? variantLocales[entry] : {};
}

const i18n = createI18n<[MessageSchema], SupportedLocale>({
  locale: defaultLocale,
  fallbackLocale: defaultLocale,
  messages: {
    de,
  },
});

const overrides = getVariantOverrides("de");
if (Object.keys(overrides).length > 0) {
  i18n.global.mergeLocaleMessage("de", overrides);
}

export default i18n;

const setI18nLanguage = (locale: SupportedLocale) => {
  (i18n.global.locale as unknown as { value: SupportedLocale }).value = locale;
  document.documentElement.setAttribute("lang", locale);
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

  if (i18n.global.availableLocales.includes(locale)) {
    setI18nLanguage(locale);
    return nextTick();
  }
  const messagesModule = await import(`@/locales/${locale}.json`);
  const baseMessages = messagesModule.default as MessageSchema;
  const overrides = resolveVariantMessages(locale) as Partial<MessageSchema>;
  i18n.global.setLocaleMessage(locale, { ...baseMessages, ...overrides });
  setI18nLanguage(locale);
  return nextTick();
};
