public enum Award {
    BEST_POSTER,
    BEST_ORAL,
    PEOPLE_S_CHOICE;

    public String getDisplayName() {
        switch (this) {
            case BEST_POSTER:
                return "Best Poster";
            case BEST_ORAL:
                return "Best Oral";
            case PEOPLE_S_CHOICE:
                return "People's Choice";
            default:
                return this.name();
        }
    }
}
