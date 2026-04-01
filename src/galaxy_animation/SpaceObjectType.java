package galaxy_animation;

/**
 * Definice výčtového typu představující jednotlivé typy vesmírných objektů.
 */
public enum SpaceObjectType {
    /**
     * typy vesmírných objektů
     */
    STAR("star"),
    PLANET("planet"),
    COMET("comet"),
    ROCKET("rocket");

    /**
     * globální proměnná znakové hodnoty typu vesmírného objektu
     */
    private final String name;

    /**
     * Konstruktor pro vyvtoření typu vesmírného objetku a přirození znakové hodnoty
     *
     * @param name jméno typu vesmírného objetku
     */
    SpaceObjectType(String name) {
        this.name = name;
    }

    /**
     * Metoda pro získaní znakové hodnoty typu vesmírného objetku
     *
     * @return name znaková hodnota typu vesmírného objetku
     */
    private String getSpaceObjectTypeName() {
        return this.name;
    }

    /**
     * Metoda pro získání hodnoty typu vesmírného objetku
     *
     * @param name jméno typu vesmírného objetku
     * @return honota typu vesmírného objetku
     */
    public static SpaceObjectType getSpaceObjectType(String name) {
        SpaceObjectType type = null;
        for (SpaceObjectType types : SpaceObjectType.values()) {
            if (types.getSpaceObjectTypeName().equals(name)) {
                type = types;
                break;
            }
        }
        return type;
    }
}
