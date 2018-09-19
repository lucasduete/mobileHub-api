package io.github.lucasduete.mobileHubApi.controllers;

public class Feed {

    private String title;
    private String avatarUrl;

    public Feed() {

    }

    public Feed(String title, String avatarUrl) {
        this.title = title;
        this.avatarUrl = avatarUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feed feed = (Feed) o;

        if (title != null ? !title.equals(feed.title) : feed.title != null) return false;
        return avatarUrl != null ? avatarUrl.equals(feed.avatarUrl) : feed.avatarUrl == null;
    }

    @Override
    public int hashCode() {

        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder("Feed{");
        sb.append("title='").append(title).append('\'');
        sb.append(", avatarUrl='").append(avatarUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
