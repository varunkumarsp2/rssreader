package com.apptasticsoftware.integrationtest;

import com.apptasticsoftware.rssreader.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.npathai.hamcrestopt.OptionalMatchers.*;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


class RssReaderIntegrationTest {

    @Test
    void nullHttpClient() {
        assertThrows(NullPointerException.class, () ->
                new RssReader(null));
    }


    @Disabled("Investigating")
    @Test
    void rssRiksbanken() throws IOException {
        RssReader reader = new RssReader();
        List<Item> items = reader.read("https://www.riksbank.se/sv/rss/pressmeddelanden").collect(Collectors.toList());

        assertFalse(items.isEmpty());

        for (Item item : items) {
            // Validate channel
            Channel channel = item.getChannel();
            assertNotNull(channel);
            assertThat(channel.getTitle(), is("Pressmeddelanden - Riksbanken"));
            assertThat(channel.getDescription(), is("Pressmeddelanden från Sveriges riksbank som RSS-flöde."));
            assertThat(channel.getLanguage(), isPresentAndIs("sv"));
            assertThat(channel.getLink(), is("https://www.riksbank.se/sv/rss/pressmeddelanden/"));
            assertThat(channel.getCopyright(), isEmpty());
            assertThat(channel.getGenerator(), isEmpty());
            assertThat(channel.getLastBuildDate(), isPresentAnd(not(emptyString())));

            // Validate item
            assertNotNull(item);
            assertThat(item.getGuid(), isPresentAnd(not(emptyString())));
            assertThat(item.getIsPermaLink(), isPresentAnd(is(false)));
            assertThat(item.getTitle(), isPresentAnd(not(emptyString())));
            assertThat(item.getDescription(), isPresentAnd(not(emptyString())));
            assertThat(item.getPubDate(), isPresentAnd(not(emptyString())));
            assertThat(item.getLink(), isPresentAnd(not(emptyString())));
        }
    }


    @Test
    void rssKonjunkturinstitutet() throws IOException {
        RssReader reader = new RssReader();
        List<Item> items = reader.read("https://www.konj.se/4.2de5c57614f808a95afcc13f/12.2de5c57614f808a95afcc354.portlet?state=rss&sv.contenttype=text/xml;charset=UTF-8").collect(Collectors.toList());

        assertFalse(items.isEmpty());

        for (Item item : items) {
            // Validate channel
            Channel channel = item.getChannel();
            assertNotNull(channel);
            assertThat(channel.getTitle(), is("Publikationer från Konjunkturinstitutet"));
            assertThat(channel.getDescription(), is("Rapportutgåvor publicerade på konj.se"));
            assertThat(channel.getLanguage(), isPresentAndIs("sv"));
            assertThat(channel.getLink(), is("https://www.konj.se/om-ki/aktuellt/publikationer.html"));
            assertThat(channel.getCopyright(), isPresentAndIs("Konjunkturinstitutet"));
            assertThat(channel.getGenerator(), isPresentAnd(startsWith("Sitevision")));
            assertThat(channel.getLastBuildDate(), isEmpty());

            // Validate item
            assertNotNull(item);
            assertThat(item.getGuid(), isPresent());
            assertThat(item.getIsPermaLink(), isEmpty());
            assertThat(item.getTitle(), isPresent());
            assertThat(item.getDescription(), isPresent());
            assertThat(item.getPubDate(), isPresent());
            assertThat(item.getLink(), isPresent());
        }
    }


    @Test
    void rssScb() throws IOException {
        RssReader reader = new RssReader();
        List<Item> items = reader.read("https://www.scb.se/Feed/statistiknyheter/").collect(Collectors.toList());

        assertFalse(items.isEmpty());

        for (Item item : items) {
            // Validate channel
            Channel channel = item.getChannel();
            assertNotNull(channel);
            assertThat(channel.getTitle(), is("Statistiska centralbyrån - Statistiknyheter"));
            assertThat(channel.getDescription(), is("Statistiknyheter via RSS"));
            assertThat(channel.getLanguage(), isEmpty());
            assertThat(channel.getLink().toLowerCase(), is("http://www.scb.se/feed/statistiknyheter/"));
            assertThat(channel.getCopyright(), isEmpty());
            assertThat(channel.getGenerator(), isEmpty());
            assertThat(channel.getLastBuildDate(), isPresent());

            // Validate item
            assertNotNull(item);
            assertThat(item.getGuid(), isPresent());
            assertThat(item.getIsPermaLink(), isEmpty());
            assertThat(item.getTitle(), isPresent());
            assertThat(item.getDescription(), isPresent());
            assertThat(item.getPubDate(), isPresent());
            assertThat(item.getLink(), isPresent());
        }
    }


    @Test
    void rssPlacera() throws IOException {
        RssReader reader = new RssReader();
        List<Item> items = reader.read("https://www.avanza.se/placera/forstasidan.rss.xml").collect(Collectors.toList());

        assertFalse(items.isEmpty());

        for (Item item : items) {
            // Validate channel
            Channel channel = item.getChannel();
            assertNotNull(channel);
            assertThat(channel.getTitle(), is("Placera.se"));
            assertThat(channel.getDescription(), is(not(emptyString())));
            assertThat(channel.getLink(), is("https://www.placera.se"));
            assertThat(channel.getCopyright(), isEmpty());
            assertThat(channel.getGenerator(), isEmpty());
            assertThat(channel.getLastBuildDate(), isEmpty());

            // Validate item
            assertNotNull(item);
            assertThat(item.getGuid(), isPresentAnd(not(emptyString())));
            assertThat(item.getIsPermaLink(), isPresentAndIs(true));
            assertThat(item.getTitle(), isPresentAnd(not(emptyString())));
            assertThat(item.getDescription(), isPresentAnd(not(emptyString())));
            assertThat(item.getPubDate(), isPresentAnd(not(emptyString())));
            assertThat(item.getLink(), isPresentAnd(not(emptyString())));
        }
    }


    @Test
    void rssPlaceraString() throws IOException, InterruptedException {
        String rssText = getRssFeedAsString("https://www.avanza.se/placera/forstasidan.rss.xml");
        InputStream inputStream = new ByteArrayInputStream(rssText.getBytes(StandardCharsets.UTF_8));

        RssReader reader = new RssReader();
        List<Item> items = reader.read(inputStream).collect(Collectors.toList());

        assertFalse(items.isEmpty());

        for (Item item : items) {
            // Validate channel
            Channel channel = item.getChannel();
            assertNotNull(channel);
            assertThat(channel.getTitle(), is("Placera.se"));
            assertThat(channel.getDescription(), is(not(emptyString())));
            assertThat(channel.getLink(), is("https://www.placera.se"));
            assertThat(channel.getCopyright(), isEmpty());
            assertThat(channel.getGenerator(), isEmpty());
            assertThat(channel.getLastBuildDate(), isEmpty());

            // Validate item
            assertNotNull(item);
            assertThat(item.getGuid(), isPresentAnd(not(emptyString())));
            assertThat(item.getIsPermaLink(), isPresentAndIs(true));
            assertThat(item.getTitle(), isPresentAnd(not(emptyString())));
            assertThat(item.getDescription(), isPresentAnd(not(emptyString())));
            assertThat(item.getPubDate(), isPresentAnd(not(emptyString())));
            assertThat(item.getLink(), isPresentAnd(not(emptyString())));
        }
    }


    @Test
    void rssBreakit() throws IOException {
        RssReader reader = new RssReader();
        List<Item> items = reader.read("https://www.breakit.se/feed/artiklar").collect(Collectors.toList());

        DayOfWeek dayOfWeek = DayOfWeek.of(LocalDate.now().get(ChronoField.DAY_OF_WEEK));
        if (items.isEmpty() && dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return; // Brakit articles are removed after one day and no articles published on Saturday or Sunday
        }

        assertFalse(items.isEmpty());

        for (Item item : items) {
            // Validate channel
            Channel channel = item.getChannel();
            assertNotNull(channel);
            assertThat(channel.getTitle(), is("breakit.se"));
            assertThat(channel.getDescription(), is("Breakit är Sveriges nyhetssajt om techbolag och startups."));
            assertThat(channel.getLanguage(), isPresentAndIs("sv"));
            assertThat(channel.getLink(), is("http://breakit.se"));
            assertThat(channel.getCopyright(), isEmpty());
            assertThat(channel.getGenerator(), isEmpty());
            assertThat(channel.getLastBuildDate(), isPresentAnd(not(emptyString())));

            // Validate item
            assertNotNull(item);
            assertThat(item.getGuid(), isPresentAnd(not(emptyString())));
            assertThat(item.getIsPermaLink(), isPresentAndIs(true));
            assertThat(item.getTitle(), isPresentAnd(not(emptyString())));
            assertThat(item.getDescription(), isPresentAnd(not(emptyString())));
            assertThat(item.getPubDate(), isPresentAnd(not(emptyString())));
            assertThat(item.getLink(), isPresentAnd(not(emptyString())));
        }
    }


    @Test
    void rssRealtid() throws IOException {
        RssReader reader = new RssReader();
        List<Item> items = reader.read("https://www.realtid.se/rss/senaste").collect(Collectors.toList());

        assertFalse(items.isEmpty());

        for (Item item : items) {
            // Validate channel
            Channel channel = item.getChannel();
            assertNotNull(channel);
            assertThat(channel.getTitle(), is("Realtid"));
            assertThat(channel.getDescription(), is(""));
            assertThat(channel.getLanguage(), isPresentAndIs("sv"));
            assertThat(channel.getLink(), is("https://www.realtid.se/rss/senaste"));
            assertThat(channel.getCopyright(), isEmpty());
            assertThat(channel.getGenerator(), isEmpty());
            assertThat(channel.getLastBuildDate(), isEmpty());

            // Validate item
            assertNotNull(item);
            assertThat(item.getGuid(), isPresentAnd(not(emptyString())));
            assertThat(item.getIsPermaLink(), isPresentAndIs(false));
            assertThat(item.getTitle(), isPresentAnd(not(emptyString())));
            //assertThat(item.getDescription(), isPresentAnd(not(emptyString())));
            assertThat(item.getPubDate(), isPresentAnd(not(emptyString())));
            assertThat(item.getLink(), isPresentAnd(not(emptyString())));
        }
    }


    @Test
    void rssVAFinansBadUrl() {
        RssReader reader = new RssReader();
        assertThrows(IOException.class, () ->
                reader.read("https://www.vafinans2.se/rss/nyheter"));
    }


    @Test
    void investingcom() throws IOException {
        RssReader reader = new RssReader();
        List<Item> items = reader.read("https://se.investing.com/rss/news.rss").collect(Collectors.toList());

        assertFalse(items.isEmpty());

        for (Item item : items) {
            // Validate channel
            Channel channel = item.getChannel();
            assertNotNull(channel);
            assertThat(channel.getTitle(), is("Alla nyheter"));
            assertThat(channel.getDescription(), is(""));
            assertThat(channel.getLanguage(), isEmpty());
            assertThat(channel.getLink(), is("https://se.investing.com"));
            assertThat(channel.getCopyright(), isEmpty());
            assertThat(channel.getGenerator(), isEmpty());
            assertThat(channel.getLastBuildDate(), isEmpty());

            // Validate item
            assertNotNull(item);
            assertThat(item.getGuid(), isEmpty());
            assertThat(item.getIsPermaLink(), isEmpty());
            assertThat(item.getTitle(), isPresentAnd(not(emptyString())));
            assertThat(item.getDescription(), anyOf(isEmpty(), isPresentAnd(not(emptyString()))));
            assertThat(item.getPubDate(), isPresent());
            assertThat(item.getLink(), isPresent());
        }
    }


    @Test
    void investingcom_mest_lasta() throws IOException {
        RssReader reader = new RssReader();
        List<Item> items = reader.read("https://se.investing.com/rss/news_285.rss").collect(Collectors.toList());

        assertFalse(items.isEmpty());

        for (Item item : items) {
            // Validate channel
            Channel channel = item.getChannel();
            assertNotNull(channel);
            assertThat(channel.getTitle(), is("Mest lästa nyheter"));
            assertThat(channel.getDescription(), is(""));
            assertThat(channel.getLanguage(), isEmpty());
            assertThat(channel.getLink(), is("https://se.investing.com"));
            assertThat(channel.getCopyright(), isEmpty());
            assertThat(channel.getGenerator(), isEmpty());
            assertThat(channel.getLastBuildDate(), isEmpty());

            // Validate item
            assertNotNull(item);
            assertThat(item.getGuid(), isEmpty());
            assertThat(item.getIsPermaLink(), isEmpty());
            assertThat(item.getTitle(), isPresentAnd(not(emptyString())));
            assertThat(item.getDescription(), anyOf(isEmpty(), isPresentAnd(not(emptyString()))));
            assertThat(item.getPubDate(), isPresent());
            assertThat(item.getLink(), isPresent());
            if(item.getEnclosure().isPresent()) {
                assertNotNull(item.getEnclosure().get().getUrl());
                assertNotNull(item.getEnclosure().get().getType());
            }
        }
    }


    @Test
    void diDigital() throws IOException {
        RssReader reader = new RssReader();
        List<Item> items = reader.read("https://digital.di.se/rss").collect(Collectors.toList());

        assertFalse(items.isEmpty());

        for (Item item : items) {
            // Validate channel
            Channel channel = item.getChannel();
            assertNotNull(channel);
            assertThat(channel.getTitle(), is("Di Digital - Senaste nytt"));
            assertThat(channel.getDescription(), is(""));
            assertThat(channel.getLanguage(), isEmpty());
            assertThat(channel.getLink(), is("https://digital.di.se/rss"));
            assertThat(channel.getCopyright(), isEmpty());
            assertThat(channel.getGenerator(), isEmpty());
            assertThat(channel.getLastBuildDate(), isEmpty());

            // Validate item
            assertNotNull(item);
            assertThat(item.getGuid(), isPresentAnd(not(emptyString())));
            assertThat(item.getIsPermaLink(), isEmpty());
            assertThat(item.getTitle(), isPresentAnd(not(emptyString())));
            assertThat(item.getDescription(), anyOf(isEmpty(), isPresentAnd(not(emptyString()))));
            assertThat(item.getPubDate(), isPresent());
            assertThat(item.getLink(), isPresent());
        }
    }


    @SuppressWarnings("java:S5961")
    @Test
    void rssWorldOfTank() throws IOException {
        RssReader reader = new RssReader();
        List<Item> items = reader.read("https://worldoftanks.eu/en/rss/news/").collect(Collectors.toList());
        assertFalse(items.isEmpty());

        for (Item item : items) {
            // Validate channel
            Channel channel = item.getChannel();
            assertNotNull(channel);
            assertThat(channel.getTitle(), containsString("World of Tanks"));
            assertThat(channel.getDescription(), anything());
            assertThat(channel.getLanguage(), isPresentAndIs("en"));
            assertThat(channel.getLink(), is("https://worldoftanks.eu/en/news/"));
            assertThat(channel.getPubDate(), isPresent());
            assertThat(channel.getPubDateZonedDateTime(), isPresent());
            assertThat(channel.getImage(), isPresent());
            assertThat(channel.getImage().map(Image::getTitle).orElse(null), containsString("World of Tanks"));
            assertThat(channel.getImage().map(Image::getLink).orElse(null), is("https://worldoftanks.eu/en/news/"));
            assertThat(channel.getImage().map(Image::getUrl), isPresentAnd(not(emptyString())));
            assertThat(channel.getImage().get().getDescription(), isEmpty());
            assertThat(channel.getImage().get().getWidth(), isEmpty());
            assertThat(channel.getImage().get().getHeight(), isEmpty());

            // Validate item
            assertNotNull(item);
            assertThat(item.getGuid(), isPresentAnd(not(emptyString())));
            assertThat(item.getIsPermaLink(), isPresentAnd(is(true)));
            assertThat(item.getTitle(), isPresentAnd(not(emptyString())));
            assertThat(item.getDescription(), isPresentAnd(not(emptyString())));
            assertThat(item.getPubDate(), isPresentAnd(not(emptyString())));
            assertThat(item.getLink(), isPresentAnd(not(emptyString())));
            assertThat(item.getEnclosure(), isPresent());
            assertThat(item.getEnclosure().get().getUrl(), is(not(emptyString())));
            assertThat(item.getEnclosure().get().getType(), is(not(emptyString())));
            assertThat(item.getEnclosure().get().getLength(), isPresent());
        }
    }


    @Test
    void zonedDateTime() throws IOException {
        RssReader reader = new RssReader();
        List<Item> items = reader.read("https://www.breakit.se/feed/artiklar").collect(Collectors.toList());

        assertFalse(items.isEmpty());

        ZonedDateTime dateTime = items.stream()
                                      .sorted()
                                      .findFirst()
                                      .flatMap(Item::getPubDateZonedDateTime)
                                      .orElse(null);
        assertNotNull(dateTime);
    }


    @Test
    void dateTime() throws IOException {
        RssReader reader = new RssReader();
        List<Item> items = reader.read("https://www.breakit.se/feed/artiklar").collect(Collectors.toList());

        assertFalse(items.isEmpty());

        Optional<ZonedDateTime> dateTime = items.stream()
                                                .findFirst()
                                                .flatMap(Item::getPubDateZonedDateTime);

        assertThat(dateTime, isPresent());
    }


    @Test
    void httpClient() throws IOException, KeyManagementException, NoSuchAlgorithmException {
        SSLContext context = SSLContext.getInstance("TLSv1.3");
        context.init(null, null, null);

        HttpClient httpClient = HttpClient.newBuilder()
                .sslContext(context)
                .connectTimeout(Duration.ofSeconds(15))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        RssReader reader = new RssReader(httpClient);
        List<Item> items = reader.read("https://www.breakit.se/feed/artiklar").collect(Collectors.toList());

        assertFalse(items.isEmpty());

        for (Item item : items) {
            // Validate channel
            Channel channel = item.getChannel();
            assertNotNull(channel);
            assertThat(channel.getTitle(), is("breakit.se"));
            assertThat(channel.getDescription(), is("Breakit är Sveriges nyhetssajt om techbolag och startups."));
            assertThat(channel.getLanguage(), isPresentAndIs("sv"));
            assertThat(channel.getLink(), is("http://breakit.se"));
            assertThat(channel.getCopyright(), isEmpty());
            assertThat(channel.getGenerator(), isEmpty());
            assertThat(channel.getLastBuildDate(), isPresent());
            assertThat(channel.getLastBuildDateZonedDateTime(), isPresent());

            // Validate item
            assertNotNull(item);
            assertThat(item.getGuid(), isPresentAnd(not(emptyString())));
            assertThat(item.getIsPermaLink(), isPresentAnd(is(true)));
            assertThat(item.getTitle(), isPresentAnd(not(emptyString())));
            assertThat(item.getDescription(), anyOf(isEmpty(), isPresentAnd(not(emptyString()))));
            assertThat(item.getPubDate(), isPresent());
            assertThat(item.getPubDateZonedDateTime(), isPresent());
            assertThat(item.getLink(), isPresent());
        }
    }

    private String getRssFeedAsString(String url) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(25))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }


    @Test
    void testItemExtension() throws IOException {
        List<Item> items = new RssReader().addItemExtension("dc:creator", Item::setAuthor)
                                          .addItemExtension("dc:date", Item::setPubDate)
                                          .addItemExtension("dc:date", "href", Item::setPubDate)
                                          .read("https://lwn.net/headlines/rss")
                                          .collect(Collectors.toList());

        for (Item item : items) {
            assertThat(item.getAuthor(), isPresentAnd(not(emptyString())));
            assertThat(item.getPubDate(), isPresentAnd(not(emptyString())));
        }
    }


    @Test
    void testChannelExtension() throws IOException {
        List<Item> items = new RssReader().addChannelExtension("syn:updatePeriod", Channel::addCategory)
                                          .addChannelExtension("syn:updatePeriod", "href", Channel::addCategory)
                                          .read("https://lwn.net/headlines/rss")
                                          .collect(Collectors.toList());

        for (Item item : items) {
            assertThat(item.getChannel().getCategories().get(0), is(not(emptyString())));
        }
    }


    @Test
    void testAttributeExtension() throws IOException {
        List<Item> items = new RssReader()
                .addChannelExtension("atom:link", "rel", Channel::addCategory)
                .addChannelExtension("atom:link", "type", Channel::setManagingEditor)
                .addItemExtension("atom:link", "rel", Item::setAuthor)
                .read("https://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml")
                .collect(Collectors.toList());

        for (Item item : items) {
            assertThat(item.getChannel().getCategories().get(0), is("self"));
            assertThat(item.getChannel().getManagingEditor(), isPresentAnd(not(emptyString())));
            assertThat(item.getAuthor(), isPresentAnd(not(emptyString())));
        }
    }

    @Test
    void testItemExtensionNoNamespace() throws IOException {
        List<Item> items = new RssReader()
                .addItemExtension("name", Item::setComments)
                .addItemExtension("email", Item::setComments)
                .read("https://github.com/openjdk/jdk/commits.atom")
                .collect(Collectors.toList());

        for (Item item : items) {
            assertThat(item.getComments(), isPresentAnd(not(emptyString())));
        }
    }

    @Test
    void testUserAgent() throws IOException {
        var list = new RssReader()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                .read("https://www.sciencedaily.com/rss/top.xml")
                .sorted()
                .collect(Collectors.toList());
        assertTrue(list.size() > 10);
    }

    @Test
    void testHttpHeader() throws IOException {
        List<Item> items = new RssReader().addHeader("If-None-Match", "response_version1")
                                          .read("https://lwn.net/headlines/rss")
                                          .collect(Collectors.toList());

        for (Item item : items) {
            assertThat(item.getChannel().getTitle(), is("LWN.net"));
        }
    }

    @Test
    void testClose() throws IOException {
        Stream<Item> stream = new RssReader().read("https://lwn.net/headlines/rss");
        stream.close();

        assertThrows(IllegalStateException.class, stream::count);
    }

    @Test
    void testAutoClose() throws IOException {
        try (Stream<Item> stream = new RssReader().read("https://lwn.net/headlines/rss")) {
            var list = stream.limit(2).collect(Collectors.toList());
            assertEquals(2, list.size());
        }
    }

    @Test
    void testCloseTwice() throws IOException {
        try (Stream<Item> stream = new RssReader().read("https://lwn.net/headlines/rss")) {
            var list = stream.collect(Collectors.toList());
            assertEquals(15, list.size());
        }
    }

    @Test
    void testAtomFeed() {
        var items = new RssReader().read(fromFile("atom-feed.xml"))
                                   .collect(Collectors.toList());

        assertEquals(3, items.size());

        assertEquals("dive into mark", items.get(0).getChannel().getTitle());
        assertEquals(65, items.get(0).getChannel().getDescription().length());
        assertEquals("http://example.org/feed.atom", items.get(0).getChannel().getLink());
        assertEquals("Copyright (c) 2003, Mark Pilgrim", items.get(0).getChannel().getCopyright().orElse(null));
        assertEquals("Example Toolkit", items.get(0).getChannel().getGenerator().orElse(null));
        assertEquals("2005-07-31T12:29:29Z", items.get(0).getChannel().getLastBuildDate().orElse(null));

        assertEquals("Atom-Powered Robots Run Amok", items.get(1).getTitle().orElse(null));
        assertNull(items.get(1).getAuthor().orElse(null));
        assertEquals("http://example.org/2003/12/13/atom03", items.get(1).getLink().orElse(null));
        assertEquals("urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a", items.get(1).getGuid().orElse(null));
        assertEquals("2003-12-13T18:30:02Z", items.get(1).getPubDate().orElse(null));
        assertEquals(211, items.get(1).getDescription().orElse("").length());

        assertEquals("Atom-Powered Robots Run Amok 2", items.get(2).getTitle().orElse(null));
        assertNull(items.get(2).getAuthor().orElse(null));
        assertEquals("http://example.org/2003/12/13/atom04", items.get(2).getLink().orElse(null));
        assertEquals("urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6b", items.get(2).getGuid().orElse(null));
        assertEquals("2003-12-13T18:30:01Z", items.get(2).getPubDate().orElse(null));
        assertEquals(47, items.get(2).getDescription().orElse("").length());
    }

    @Test
    void testReadFromFile() {
        long count = new RssReader().read(fromFile("itunes-podcast.xml")).count();
        assertEquals(9, count);
    }
    
    @Test
    void testBadEnclosureInfo() {
        long count = new RssReader().read(fromFile("podcast-with-bad-enclosure.xml")).count();
        assertEquals(1, count);
    }

    @Test
    void testMultipleCategories() {
        var list = new RssReader().read(fromFile("multiple-categories.xml")).collect(Collectors.toList());

        assertFalse(list.isEmpty());
        var item = list.get(0);
        assertTrue(item.getChannel().getCategories().size() > 1);
        assertTrue(item.getChannel().getCategory().isPresent());
        assertTrue(item.getCategories().size() > 1);
        assertTrue(item.getCategory().isPresent());
    }

    @Test
    void testImageBadWidthHeight() {
        var list = new RssReader().read(fromFile("bad-image-width-height.xml")).collect(Collectors.toList());
        assertEquals(1, list.size());
        assertTrue(list.get(0).getChannel().getImage().isPresent());
    }

    @Test
    void skipEmptyCategory() {
        var list = new RssReader().read(fromFile("empty-category.xml")).collect(Collectors.toList());
        assertEquals(1, list.size());
        assertTrue(list.get(0).getCategories().isEmpty());
    }

    private InputStream fromFile(String fileName) {
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }
}
