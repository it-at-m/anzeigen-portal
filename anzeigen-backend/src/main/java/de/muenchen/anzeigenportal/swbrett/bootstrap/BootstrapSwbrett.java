package de.muenchen.anzeigenportal.swbrett.bootstrap;

import de.muenchen.anzeigenportal.swbrett.ads.service.AdService;
import de.muenchen.anzeigenportal.swbrett.ads.service.AdCategoryService;
import de.muenchen.anzeigenportal.swbrett.ads.repository.AdCategoryRepository;
import de.muenchen.anzeigenportal.swbrett.ads.repository.AdRepository;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdCategory;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdTO;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdType;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingName;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingTO;
import de.muenchen.anzeigenportal.swbrett.settings.service.SettingService;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUserTO;
import de.muenchen.anzeigenportal.swbrett.users.service.UserService;
import de.muenchen.anzeigenportal.swbrett.users.service.UserMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import net.bytebuddy.utility.RandomString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class BootstrapSwbrett implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(BootstrapSwbrett.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdService adService;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private AdCategoryService adCategoryService;

    @Autowired
    private AdCategoryRepository adCategoryRepository;

    @Autowired
    private SettingService settingService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (!this.settingService.getAllSettings().isEmpty()) {
            LOG.info("DB wurde schon gebootstrappt.");
            return;
        }

        LOG.info("Bootstrapping...");

        this.bootstrapSettings();
        final List<SwbUserTO> users = this.bootstrapUsers();
        final List<AdCategory> adCategories = this.bootstrapAdCategories();
        this.bootstrapAds(users.get(0), adCategories);
        // this.bootstrapRandomAds(users.get(2), adCategories.get(0), 51);
        // this.bootstrapRandomAds(users.get(2), adCategories.get(4), 233);

        LOG.info("... Bootstrapping abgeschlossen");
    }

    private void bootstrapSettings() {
        LOG.info("  Bootstrap Settings...");

        final SettingTO motd = new SettingTO();
        motd.setSettingName(SettingName.MOTD);
        settingService.createSetting(motd);

        final SettingTO maxImageSize = new SettingTO();
        maxImageSize.setSettingName(SettingName.MAX_SWB_IMAGE_SIZE);
        maxImageSize.setNumberValue(3);
        settingService.createSetting(maxImageSize);

        final SettingTO maxFileSize = new SettingTO();
        maxFileSize.setSettingName(SettingName.MAX_SWB_FILE_SIZE);
        maxFileSize.setNumberValue(3);
        settingService.createSetting(maxFileSize);

        final SettingTO maxFilesLength = new SettingTO();
        maxFilesLength.setSettingName(SettingName.MAX_SWB_FILES_LENGTH);
        maxFilesLength.setNumberValue(5);
        settingService.createSetting(maxFilesLength);

        final SettingTO maxExpiryRange = new SettingTO();
        maxExpiryRange.setSettingName(SettingName.MAX_EXPIRY_DATE_RANGE);
        maxExpiryRange.setNumberValue(8);
        settingService.createSetting(maxExpiryRange);

        final SettingTO agb = new SettingTO();
        agb.setSettingName(SettingName.AGB_FILE);
        settingService.createSetting(agb);

        final SettingTO datenschutzhinweise = new SettingTO();
        datenschutzhinweise.setSettingName(SettingName.DATENSCHUTZHINWEISE_FILE);
        settingService.createSetting(datenschutzhinweise);

        final SettingTO maxPageSize = new SettingTO();
        maxPageSize.setSettingName(SettingName.MAX_PAGE_SIZE);
        maxPageSize.setNumberValue(20);
        settingService.createSetting(maxPageSize);

        final SettingTO defaultSorting = new SettingTO();
        defaultSorting.setSettingName(SettingName.DEFAULT_SORTING);
        defaultSorting.setTextValue("creationDateTime");
        settingService.createSetting(defaultSorting);

        final SettingTO defaultOrdering = new SettingTO();
        defaultOrdering.setSettingName(SettingName.DEFAULT_ORDERING);
        defaultOrdering.setTextValue("desc");
        settingService.createSetting(defaultOrdering);

        final SettingTO maxArchiveRange = new SettingTO();
        maxArchiveRange.setSettingName(SettingName.MAX_ARCHIVE_DATE_RANGE);
        maxArchiveRange.setNumberValue(8);
        settingService.createSetting(maxArchiveRange);

        LOG.info("  ... Settings erstellt");
    }

    private List<SwbUserTO> bootstrapUsers() {
        LOG.info("  Bootstrap Test Users...");

        final List<SwbUser> result = new ArrayList<>();

        {
            final SwbUser u = new SwbUser();
            u.setLhmObjectId("112061642");
            u.setDisplayName("Bugs Bunny");
            result.add(userService.saveOrGet(u));
        }

        {
            final SwbUser u = new SwbUser();
            u.setLhmObjectId("112061643");
            u.setDisplayName("Mr. Admin");
            result.add(userService.saveOrGet(u));
        }

        {
            final SwbUser u = new SwbUser();
            u.setLhmObjectId("112061644");
            u.setDisplayName("Randall Random");
            result.add(userService.saveOrGet(u));
        }

        LOG.info("  ... Users erstellt");
        return result.stream().map(userMapper::toSwbUserTO).collect(Collectors.toList());
    }

    private List<AdCategory> bootstrapAdCategories() {
        LOG.info("  Bootstrap AdCategories...");

        final AdCategory adCategory1 = new AdCategory();
        adCategory1.setName("Gegenstände");
        adCategoryService.createAdCategory(adCategory1);

        final AdCategory adCategory2 = new AdCategory();
        adCategory2.setName("Möbel");
        adCategoryService.createAdCategory(adCategory2);

        final AdCategory adCategory3 = new AdCategory();
        adCategory3.setName("Wohnungen");
        adCategoryService.createAdCategory(adCategory3);

        final AdCategory adCategory4 = new AdCategory();
        adCategory4.setName("Sport");
        adCategoryService.createAdCategory(adCategory4);

        final AdCategory adCategory5 = new AdCategory();
        adCategory5.setName("Sonstiges");
        adCategory5.setStandard(true);
        adCategoryService.createAdCategory(adCategory5);

        LOG.info("  ... " + adCategoryRepository.count() + " AdCategories erstellt");
        return adCategoryService.getAdCategories();
    }

    private void bootstrapAds(final SwbUserTO user, final List<AdCategory> adCategories) throws IOException {
        LOG.info("  Bootstrap Ads...");

        final AdTO ad1 = new AdTO();
        ad1.setActive(true);
        ad1.setSwbUser(user);
        ad1.setAdCategory(adCategories.get(4));
        //        ad_1.setAdFiles(...);
        //        ad_1.setAdImg(...);
        ad1.setAdType(AdType.SEEK);
        ad1.setTitle("Suche Babysitter für meine fünf kleinen (1-5 J.)");
        ad1.setDescription("Hallo kennt wer jemanden nettes? 20 pro Stunde, ca 1-2 mal im Monat");
        ad1.setExpiryDate(LocalDate.now().plusWeeks(3));
        //        ad_1.setLink(...);
        ad1.setPrice(20.00);
        ad1.setPhone("123/4567890");
        ad1.setEmail("frau@soundso.de");
        adService.createAd(ad1);

        final AdTO ad2 = new AdTO();
        ad2.setActive(true);
        ad2.setSwbUser(user);
        ad2.setAdCategory(adCategories.get(4));
        //        ad_2.setAdFiles(...);
        //        ad_2.setAdImg(...);
        ad2.setAdType(AdType.SEEK);
        ad2.setTitle("Nachhilfe für meinen Sohn in der Oberstufe Gymnasium");
        ad2.setDescription("Sohn hat Probleme vor allem im naturwissenschaftlichen Bereich (Mathe, Physik, Chemie). Nachhilfe vor Ort bei uns in Sendling.");
        ad2.setExpiryDate(LocalDate.now().plusWeeks(3));
        //        ad_2.setLink(...);
        ad2.setPrice(25.00);
        ad2.setPhone("+49 010101935987");
        ad2.setEmail("frau@diesdas.de");
        adService.createAd(ad2);

        final AdTO ad3 = new AdTO();
        ad3.setActive(true);
        ad3.setSwbUser(user);
        ad3.setAdCategory(adCategories.get(2));
        //        ad_3.setAdFiles(...);
        //        ad_3.setAdImg(...);
        ad3.setAdType(AdType.SEEK);
        ad3.setTitle("Suche Wohnung mindestens 3 Zimmer nähe SWM");
        ad3.setDescription("Vielleicht kennt jemand der jemanden kennt, der jemanden kennt...");
        ad3.setExpiryDate(LocalDate.now().plusWeeks(3));
        //        ad_3.setLink(...);
        ad3.setPrice(1500.00);
        ad3.setPhone("0150 1349846");
        //        ad_3.setEmail("frau@diesdas.de");
        adService.createAd(ad3);

        final AdTO ad4 = new AdTO();
        ad4.setActive(true);
        ad4.setSwbUser(user);
        ad4.setAdCategory(adCategories.get(2));
        //        ad_4.setAdFiles(...);
        //        ad_4.setAdImg(...);
        ad4.setAdType(AdType.SEEK);
        ad4.setTitle("Suche super Wohnung!");
        ad4.setDescription("Suche 10 Zimmer Wohnung bitte billig");
        ad4.setExpiryDate(LocalDate.now().plusWeeks(3));
        //        ad_4.setLink(...);
        ad4.setPrice(0.00);
        ad4.setPhone("0150 9999999999");
        //        ad_4.setEmail("frau@diesdas.de");
        adService.createAd(ad4);

        final AdTO ad5 = new AdTO();
        ad5.setActive(true);
        ad5.setSwbUser(user);
        ad5.setAdCategory(adCategories.get(2));
        //        ad_5.setAdFiles(...);
        //        ad_5.setAdImg(...);
        ad5.setAdType(AdType.OFFER);
        ad5.setTitle("3 Zimmer am Ostbahnhof");
        ad5.setDescription("tauschen wäre cool");
        ad5.setExpiryDate(LocalDate.now().plusWeeks(4));
        //        ad_5.setLink(...);
        ad5.setPrice(0.00);
        //        ad_5.setPhone("0150 9999999999");
        ad5.setEmail("asdf.asdf@as.df");
        adService.createAd(ad5);

        final AdTO ad6 = new AdTO();
        ad6.setActive(true);
        ad6.setSwbUser(user);
        ad6.setAdCategory(adCategories.get(2));
        //        ad_6.setAdFiles(...);
        //        ad_6.setAdImg(...);
        ad6.setAdType(AdType.SEEK);
        ad6.setTitle("Suche dringend ein asdf!");
        ad6.setDescription("Bitte schnell schnell");
        ad6.setExpiryDate(LocalDate.now().plusWeeks(2));
        //        ad_6.setLink(...);
        ad6.setPrice(0.00);
        ad6.setPhone("010 10 10 0101010");
        ad6.setEmail("aaaa@dev.com");
        adService.createAd(ad6);

        final AdTO ad7 = new AdTO();
        ad7.setActive(true);
        ad7.setSwbUser(user);
        ad7.setAdCategory(adCategories.get(0));
        //        ad_7.setAdFiles(...);
        //        ad_7.setAdImg(...);
        ad7.setAdType(AdType.OFFER);
        ad7.setTitle("Skier 150cm");
        ad7.setPrice(75.00);
        ad7.setDescription("Die Skier sind in einemm guten gebrauchten Zustand mit normalen Gebrauchsspuren.\n" +
                "Sehr wenig gefahren.\n" +
                "Service Wachs und Kante NEU");
        ad7.setExpiryDate(LocalDate.now().plusWeeks(3));
        //        ad_7.setLink(...);
        //        ad_7.setPhone("010 10 10 0101010");
        ad7.setEmail("der.aus.dem@nord.en");
        adService.createAd(ad7);

        final AdTO ad8 = new AdTO();
        ad8.setActive(true);
        ad8.setSwbUser(user);
        ad8.setAdCategory(adCategories.get(0));
        //        ad_8.setAdFiles(...);
        //        ad_8.setAdImg(...);
        ad8.setAdType(AdType.OFFER);
        ad8.setPrice(60.00);
        ad8.setTitle("Kinderwagen gebraucht");
        ad8.setDescription("Hallo, \n" +
                "unser Kind ist bereits zu groß.\n" +
                "Funktioniert einwandfrei, lässt sich zusammenklappen. Einzig bei einer Tasche ist ein kleines Loch.");
        ad8.setLink(
                "https://www.google.com/search?q=appartment&rlz=1C1GCEA_enDE874DE874&oq=appartment&aqs=chrome..69i57j0l5.1360j0j7&sourceid=chrome&ie=UTF-8");
        ad8.setExpiryDate(LocalDate.now().plusWeeks(6));
        //        ad_8.setLink(...);
        ad8.setPhone("089/123456");
        ad8.setEmail("a.schwarzenegger@muenchena.de");
        adService.createAd(ad8);

        final AdTO ad9 = new AdTO();
        ad9.setActive(true);
        ad9.setSwbUser(user);
        ad9.setAdCategory(adCategories.get(0));
        //        ad_9.setAdFiles(...);
        //        ad_9.setAdImg(...);
        ad9.setAdType(AdType.OFFER);
        ad9.setPrice(560.00);
        ad9.setTitle("Scott Addict Rennrad Grösse M 7 kg");
        ad9.setDescription(
                "Bei ernsthaftem Interesse kann das Bike gerne vor Ort begutachtet werden. Ich bin mir sicher der neue Besitzer wird enorm viel Spaß mit diesem Bike haben. ");
        ad9.setExpiryDate(LocalDate.now().plusWeeks(6));
        //        ad_9.setLink(...);
        ad9.setPhone("01234 77777");
        ad9.setEmail("mbappe@psg.fr");
        adService.createAd(ad9);

        final AdTO ad10 = new AdTO();
        ad10.setActive(true);
        ad10.setSwbUser(user);
        ad10.setAdCategory(adCategories.get(0));
        //        ad_10.setAdFiles(...);
        //        ad_10.setAdImg(...);
        ad10.setAdType(AdType.OFFER);
        ad10.setPrice(20.00);
        ad10.setTitle("Oxford Advanced Learners Dictionary 9th Edition");
        ad10.setDescription("Hallo,\n" +
                "\n" +
                "mein Sohn ist von der Schule geflogen.\n" +
                "\n" +
                "Englisch Wörterbuch für Abitur und Studium\n" +
                "Zustand: gebraucht/gut");
        ad10.setExpiryDate(LocalDate.now().plusWeeks(6));
        //        ad_10.setLink(...);
        //        ad_10.setPhone("01234 77777");
        ad10.setEmail("heung.min.son@tot.en");
        adService.createAd(ad10);

        LOG.info("  ... " + adRepository.count() + " Ads erstellt");
    }

    private void bootstrapRandomAds(final SwbUserTO user, final AdCategory adCategory, final int numberOfRandomAds) throws IOException {
        LOG.info("  Bootstrap Random Ads...");

        for (int i = 0; i < numberOfRandomAds; i++) {
            final AdTO randomAd = new AdTO();
            randomAd.setSwbUser(user);
            randomAd.setAdCategory(adCategory);
            randomAd.setAdType(AdType.OFFER);
            randomAd.setTitle(RandomString.make(8));
            randomAd.setDescription(RandomString.make(200));
            final double randomPrice = (int) Math.round(new Random().nextDouble() * 10000) / 100.0;
            randomAd.setPrice(randomPrice);
            randomAd.setPhone("089 7654321");
            randomAd.setEmail("mr.random@swbrett.com");
            randomAd.setCreationDateTime(generateRandomDate());

            adService.createAd(randomAd);
        }

        LOG.info("  ... " + adRepository.count() + " Ads erstellt");
    }

    private LocalDateTime generateRandomDate() {
        final LocalDate today = LocalDate.now();
        int year = today.getYear();
        int randomMonth = today.minusMonths(1).getMonthValue(); // not this month for better demo

        if (randomMonth == 12) { //NOPMD
            year = today.minusYears(1).getYear();
        }

        randomMonth = new Random().nextInt(randomMonth) + 1;
        int randomDay = today.getDayOfMonth();
        if (randomMonth == LocalDate.now().getMonthValue()) {
            randomDay = new Random().nextInt(randomDay) + 1;
        } else {
            randomDay = new Random().nextInt(28) + 1;
        }

        return LocalDateTime.of(LocalDate.of(year, randomMonth, randomDay), LocalTime.now());
    }
}
