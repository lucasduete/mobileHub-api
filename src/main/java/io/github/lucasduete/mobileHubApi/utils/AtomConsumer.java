package io.github.lucasduete.mobileHubApi.utils;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import io.github.lucasduete.mobileHubApi.controllers.Feed;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AtomConsumer {

    public List<Feed> consume(String feedUrl) throws IOException, FeedException {

        URL url = new URL(feedUrl);

        SyndFeedInput syndFeedInput = new SyndFeedInput();
        SyndFeed feed = syndFeedInput.build(new XmlReader(url));

        List<Feed> feedList = new ArrayList<>();

        System.out.println("Feed Title: " + feed.getTitle());

        for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {

            Feed feedData = new Feed();

            feedData.setTitle(entry.getTitle());

            SyndContentImpl content = (SyndContentImpl) entry.getContents().get(0);
            String contentValue = content.getValue();

            String avatar = contentValue.split("<img class=\"avatar\" src=\"")[1];
            avatar = avatar.split("\"")[0];

            feedData.setAvatarUrl(avatar);
        }

        return feedList;
    }
}
