package io.github.lucasduete.mobileHubApi.entities;

public class PinRepository {

    private String name;
    private int pullsCount;
    private int commitsCount;
    private int openIssuesCount;

    public PinRepository() {

    }

    public PinRepository(String name, int pullsCount, int commitsCount, int openIssuesCount) {
        this.name = name;
        this.pullsCount = pullsCount;
        this.commitsCount = commitsCount;
        this.openIssuesCount = openIssuesCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPullsCount() {
        return pullsCount;
    }

    public void setPullsCount(int pullsCount) {
        this.pullsCount = pullsCount;
    }

    public int getCommitsCount() {
        return commitsCount;
    }

    public void setCommitsCount(int commitsCount) {
        this.commitsCount = commitsCount;
    }

    public int getOpenIssuesCount() {
        return openIssuesCount;
    }

    public void setOpenIssuesCount(int openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PinRepository that = (PinRepository) o;

        if (pullsCount != that.pullsCount) return false;
        if (commitsCount != that.commitsCount) return false;
        if (openIssuesCount != that.openIssuesCount) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {

        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + pullsCount;
        result = 31 * result + commitsCount;
        result = 31 * result + openIssuesCount;
        return result;
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder("PinRepository{");
        sb.append("name='").append(name).append('\'');
        sb.append(", pullsCount=").append(pullsCount);
        sb.append(", commitsCount=").append(commitsCount);
        sb.append(", openIssuesCount=").append(openIssuesCount);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(PinRepository pinRepository) {

        if(     this.getName().equals(pinRepository.getName()) &&
                this.getCommitsCount() == pinRepository.getCommitsCount() &&
                this.getOpenIssuesCount() == pinRepository.getOpenIssuesCount() &&
                this.getPullsCount() == pinRepository.getPullsCount()) {
            return true;
        } else {
            return false;
        }
    }
}
