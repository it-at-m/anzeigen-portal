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
        List<SwbUserTO> users = this.bootstrapUsers();
        List<AdCategory> adCategories = this.bootstrapAdCategories();
        this.bootstrapAds(users.get(0), adCategories);
        // this.bootstrapRandomAds(users.get(2), adCategories.get(0), 51);
        // this.bootstrapRandomAds(users.get(2), adCategories.get(4), 233);

        LOG.info("... Bootstrapping abgeschlossen");
    }

    private void bootstrapSettings() {
        LOG.info("  Bootstrap Settings...");

        SettingTO motd = new SettingTO();
        motd.setSettingName(SettingName.MOTD);
        settingService.createSetting(motd);

        SettingTO maxImageSize = new SettingTO();
        maxImageSize.setSettingName(SettingName.MAX_SWB_IMAGE_SIZE);
        maxImageSize.setNumberValue(3);
        settingService.createSetting(maxImageSize);

        SettingTO maxFileSize = new SettingTO();
        maxFileSize.setSettingName(SettingName.MAX_SWB_FILE_SIZE);
        maxFileSize.setNumberValue(3);
        settingService.createSetting(maxFileSize);

        SettingTO maxFilesLength = new SettingTO();
        maxFilesLength.setSettingName(SettingName.MAX_SWB_FILES_LENGTH);
        maxFilesLength.setNumberValue(5);
        settingService.createSetting(maxFilesLength);

        SettingTO maxExpiryRange = new SettingTO();
        maxExpiryRange.setSettingName(SettingName.MAX_EXPIRY_DATE_RANGE);
        maxExpiryRange.setNumberValue(8);
        settingService.createSetting(maxExpiryRange);

        SettingTO agb = new SettingTO();
        agb.setSettingName(SettingName.AGB_FILE);
        settingService.createSetting(agb);

        SettingTO datenschutzhinweise = new SettingTO();
        datenschutzhinweise.setSettingName(SettingName.DATENSCHUTZHINWEISE_FILE);
        settingService.createSetting(datenschutzhinweise);

        SettingTO maxPageSize = new SettingTO();
        maxPageSize.setSettingName(SettingName.MAX_PAGE_SIZE);
        maxPageSize.setNumberValue(20);
        settingService.createSetting(maxPageSize);

        SettingTO defaultSorting = new SettingTO();
        defaultSorting.setSettingName(SettingName.DEFAULT_SORTING);
        defaultSorting.setTextValue("creationDateTime");
        settingService.createSetting(defaultSorting);

        SettingTO defaultOrdering = new SettingTO();
        defaultOrdering.setSettingName(SettingName.DEFAULT_ORDERING);
        defaultOrdering.setTextValue("desc");
        settingService.createSetting(defaultOrdering);

        SettingTO maxArchiveRange = new SettingTO();
        maxArchiveRange.setSettingName(SettingName.MAX_ARCHIVE_DATE_RANGE);
        maxArchiveRange.setNumberValue(8);
        settingService.createSetting(maxArchiveRange);

        LOG.info("  ... Settings erstellt");
    }

    private List<SwbUserTO> bootstrapUsers() {
        LOG.info("  Bootstrap Test Users...");

        List<SwbUser> result = new ArrayList<>();

        {
            SwbUser u = new SwbUser();
            u.setLhmObjectId("112061642");
            u.setDisplayName("Bugs Bunny");
            result.add(userService.saveOrGet(u));
        }

        {
            SwbUser u = new SwbUser();
            u.setLhmObjectId("112061643");
            u.setDisplayName("Mr. Admin");
            result.add(userService.saveOrGet(u));
        }

        {
            SwbUser u = new SwbUser();
            u.setLhmObjectId("112061644");
            u.setDisplayName("Randall Random");
            result.add(userService.saveOrGet(u));
        }

        LOG.info("  ... Users erstellt");
        return result.stream().map(user -> userMapper.toSwbUserTO(user)).collect(Collectors.toList());
    }

    private List<AdCategory> bootstrapAdCategories() {
        LOG.info("  Bootstrap AdCategories...");

        AdCategory adCategory_1 = new AdCategory();
        adCategory_1.setName("Gegenstände");
        adCategoryService.createAdCategory(adCategory_1);

        AdCategory adCategory_2 = new AdCategory();
        adCategory_2.setName("Möbel");
        adCategoryService.createAdCategory(adCategory_2);

        AdCategory adCategory_3 = new AdCategory();
        adCategory_3.setName("Wohnungen");
        adCategoryService.createAdCategory(adCategory_3);

        AdCategory adCategory_4 = new AdCategory();
        adCategory_4.setName("Sport");
        adCategoryService.createAdCategory(adCategory_4);

        AdCategory adCategory_5 = new AdCategory();
        adCategory_5.setName("Sonstiges");
        adCategory_5.setStandard(true);
        adCategoryService.createAdCategory(adCategory_5);

        LOG.info("  ... " + adCategoryRepository.count() + " AdCategories erstellt");
        return adCategoryService.getAdCategories();
    }

    private void bootstrapAds(SwbUserTO user, List<AdCategory> adCategories) throws IOException, ValidationException {
        LOG.info("  Bootstrap Ads...");

        AdTO ad_1 = new AdTO();
        ad_1.setActive(true);
        ad_1.setSwbUser(user);
        ad_1.setAdCategory(adCategories.get(4));
        //        ad_1.setAdFiles(...);
        //        ad_1.setAdImg(...);
        ad_1.setAdType(AdType.SEEK);
        ad_1.setTitle("Suche Babysitter für meine fünf kleinen (1-5 J.)");
        ad_1.setDescription("Hallo kennt wer jemanden nettes? 20 pro Stunde, ca 1-2 mal im Monat");
        ad_1.setExpiryDate(LocalDate.now().plusWeeks(3));
        //        ad_1.setLink(...);
        ad_1.setPrice(20.00);
        ad_1.setPhone("123/4567890");
        ad_1.setEmail("frau@soundso.de");
        adService.createAd(ad_1);

        AdTO ad_2 = new AdTO();
        ad_2.setActive(true);
        ad_2.setSwbUser(user);
        ad_2.setAdCategory(adCategories.get(4));
        //        ad_2.setAdFiles(...);
        //        ad_2.setAdImg(...);
        ad_2.setAdType(AdType.SEEK);
        ad_2.setTitle("Nachhilfe für meinen Sohn in der Oberstufe Gymnasium");
        ad_2.setDescription("Sohn hat Probleme vor allem im naturwissenschaftlichen Bereich (Mathe, Physik, Chemie). Nachhilfe vor Ort bei uns in Sendling.");
        ad_2.setExpiryDate(LocalDate.now().plusWeeks(3));
        //        ad_2.setLink(...);
        ad_2.setPrice(25.00);
        ad_2.setPhone("+49 010101935987");
        ad_2.setEmail("frau@diesdas.de");
        adService.createAd(ad_2);

        AdTO ad_3 = new AdTO();
        ad_3.setActive(true);
        ad_3.setSwbUser(user);
        ad_3.setAdCategory(adCategories.get(2));
        //        ad_3.setAdFiles(...);
        //        ad_3.setAdImg(...);
        ad_3.setAdType(AdType.SEEK);
        ad_3.setTitle("Suche Wohnung mindestens 3 Zimmer nähe SWM");
        ad_3.setDescription("Vielleicht kennt jemand der jemanden kennt, der jemanden kennt...");
        ad_3.setExpiryDate(LocalDate.now().plusWeeks(3));
        //        ad_3.setLink(...);
        ad_3.setPrice(1500.00);
        ad_3.setPhone("0150 1349846");
        //        ad_3.setEmail("frau@diesdas.de");
        adService.createAd(ad_3);

        AdTO ad_4 = new AdTO();
        ad_4.setActive(true);
        ad_4.setSwbUser(user);
        ad_4.setAdCategory(adCategories.get(2));
        //        ad_4.setAdFiles(...);
        //        ad_4.setAdImg(...);
        ad_4.setAdType(AdType.SEEK);
        ad_4.setTitle("Suche super Wohnung!");
        ad_4.setDescription("Suche 10 Zimmer Wohnung bitte billig");
        ad_4.setExpiryDate(LocalDate.now().plusWeeks(3));
        //        ad_4.setLink(...);
        ad_4.setPrice(0.00);
        ad_4.setPhone("0150 9999999999");
        //        ad_4.setEmail("frau@diesdas.de");
        adService.createAd(ad_4);

        AdTO ad_5 = new AdTO();
        ad_5.setActive(true);
        ad_5.setSwbUser(user);
        ad_5.setAdCategory(adCategories.get(2));
        //        ad_5.setAdFiles(...);
        //        ad_5.setAdImg(...);
        ad_5.setAdType(AdType.OFFER);
        ad_5.setTitle("3 Zimmer am Ostbahnhof");
        ad_5.setDescription("tauschen wäre cool");
        ad_5.setExpiryDate(LocalDate.now().plusWeeks(4));
        //        ad_5.setLink(...);
        ad_5.setPrice(0.00);
        //        ad_5.setPhone("0150 9999999999");
        ad_5.setEmail("asdf.asdf@as.df");
        adService.createAd(ad_5);

        AdTO ad_6 = new AdTO();
        ad_6.setActive(true);
        ad_6.setSwbUser(user);
        ad_6.setAdCategory(adCategories.get(2));
        //        ad_6.setAdFiles(...);
        //        ad_6.setAdImg(...);
        ad_6.setAdType(AdType.SEEK);
        ad_6.setTitle("Suche dringend ein asdf!");
        ad_6.setDescription("Bitte schnell schnell");
        ad_6.setExpiryDate(LocalDate.now().plusWeeks(2));
        //        ad_6.setLink(...);
        ad_6.setPrice(0.00);
        ad_6.setPhone("010 10 10 0101010");
        ad_6.setEmail("aaaa@dev.com");
        adService.createAd(ad_6);

        AdTO ad_7 = new AdTO();
        ad_7.setActive(true);
        ad_7.setSwbUser(user);
        ad_7.setAdCategory(adCategories.get(0));
        //        ad_7.setAdFiles(...);
        //        ad_7.setAdImg(...);
        ad_7.setAdType(AdType.OFFER);
        ad_7.setTitle("Skier 150cm");
        ad_7.setPrice(75.00);
        ad_7.setDescription("Die Skier sind in einemm guten gebrauchten Zustand mit normalen Gebrauchsspuren.\n" +
                "Sehr wenig gefahren.\n" +
                "Service Wachs und Kante NEU");
        ad_7.setExpiryDate(LocalDate.now().plusWeeks(3));
        //        ad_7.setLink(...);
        //        ad_7.setPhone("010 10 10 0101010");
        ad_7.setEmail("der.aus.dem@nord.en");
        adService.createAd(ad_7);

        AdTO ad_8 = new AdTO();
        ad_8.setActive(true);
        ad_8.setSwbUser(user);
        ad_8.setAdCategory(adCategories.get(0));
        //        ad_8.setAdFiles(...);
        //        ad_8.setAdImg(...);
        ad_8.setAdType(AdType.OFFER);
        ad_8.setPrice(60.00);
        ad_8.setTitle("Kinderwagen gebraucht");
        ad_8.setDescription("Hallo, \n" +
                "unser Kind ist bereits zu groß.\n" +
                "Funktioniert einwandfrei, lässt sich zusammenklappen. Einzig bei einer Tasche ist ein kleines Loch.");
        ad_8.setLink(
                "https://www.google.com/search?q=appartment&rlz=1C1GCEA_enDE874DE874&oq=appartment&aqs=chrome..69i57j0l5.1360j0j7&sourceid=chrome&ie=UTF-8");
        ad_8.setExpiryDate(LocalDate.now().plusWeeks(6));
        //        ad_8.setLink(...);
        ad_8.setPhone("089/123456");
        ad_8.setEmail("a.schwarzenegger@muenchena.de");
        adService.createAd(ad_8);

        AdTO ad_9 = new AdTO();
        ad_9.setActive(true);
        ad_9.setSwbUser(user);
        ad_9.setAdCategory(adCategories.get(0));
        //        ad_9.setAdFiles(...);
        //        ad_9.setAdImg(...);
        ad_9.setAdType(AdType.OFFER);
        ad_9.setPrice(560.00);
        ad_9.setTitle("Scott Addict Rennrad Grösse M 7 kg");
        ad_9.setDescription(
                "Bei ernsthaftem Interesse kann das Bike gerne vor Ort begutachtet werden. Ich bin mir sicher der neue Besitzer wird enorm viel Spaß mit diesem Bike haben. ");
        ad_9.setExpiryDate(LocalDate.now().plusWeeks(6));
        //        ad_9.setLink(...);
        ad_9.setPhone("01234 77777");
        ad_9.setEmail("mbappe@psg.fr");
        adService.createAd(ad_9);

        AdTO ad_10 = new AdTO();
        ad_10.setActive(true);
        ad_10.setSwbUser(user);
        ad_10.setAdCategory(adCategories.get(0));
        //        ad_10.setAdFiles(...);
        //        ad_10.setAdImg(...);
        ad_10.setAdType(AdType.OFFER);
        ad_10.setPrice(20.00);
        ad_10.setTitle("Oxford Advanced Learners Dictionary 9th Edition");
        ad_10.setDescription("Hallo,\n" +
                "\n" +
                "mein Sohn ist von der Schule geflogen.\n" +
                "\n" +
                "Englisch Wörterbuch für Abitur und Studium\n" +
                "Zustand: gebraucht/gut");
        ad_10.setExpiryDate(LocalDate.now().plusWeeks(6));
        //        ad_10.setLink(...);
        //        ad_10.setPhone("01234 77777");
        ad_10.setEmail("heung.min.son@tot.en");
        adService.createAd(ad_10);

        LOG.info("  ... " + adRepository.count() + " Ads erstellt");
    }

    private void bootstrapRandomAds(SwbUserTO user, AdCategory adCategory, int numberOfRandomAds) throws IOException, ValidationException {
        LOG.info("  Bootstrap Random Ads...");

        for (int i = 0; i < numberOfRandomAds; i++) {
            AdTO random_Ad = new AdTO();
            random_Ad.setSwbUser(user);
            random_Ad.setAdCategory(adCategory);
            random_Ad.setAdType(AdType.OFFER);
            random_Ad.setTitle(RandomString.make(8));
            random_Ad.setDescription(RandomString.make(200));
            double randomPrice = (int) Math.round(new Random().nextDouble() * 10000) / 100.0;
            random_Ad.setPrice(randomPrice);
            random_Ad.setPhone("089 7654321");
            random_Ad.setEmail("mr.random@swbrett.com");
            random_Ad.setCreationDateTime(generateRandomDate());

            adService.createAd(random_Ad);
        }

        LOG.info("  ... " + adRepository.count() + " Ads erstellt");
    }

    private LocalDateTime generateRandomDate() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int randomMonth = today.minusMonths(1).getMonthValue(); // not this month for better demo

        if (randomMonth == 12) {
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
