public class SimpleMovie {
    private String title;
    private String cast;
    public SimpleMovie(String title,String cast)
    {
        this.title=title;
        this.cast=cast;
    }
    public boolean isCopy(SimpleMovie m)
    {
        return (this.title.equals(m.getTitle()) && this.cast.equals(m.getCast()));
    }

    public String getCast() {
        return cast;
    }

    public String getTitle() {
        return title;
    }
}
