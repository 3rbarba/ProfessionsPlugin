package br.com.jobs.profissions.miner.breakArea;

public enum BreakAreaSizeEnum {
    SIZE_2X1("2x1", 0, 0, -1, -1),
    SIZE_3X2("3x2", -1, 1, -1, 0),
    SIZE_3X3("3x3", -1, 1, -1, 1),
    SIZE_5X5("5x5", -2, 2, -1, 3);

    private final String configValue;
    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;

    BreakAreaSizeEnum(String configValue, int minX, int maxX, int minY, int maxY) {
        this.configValue = configValue;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public static BreakAreaSizeEnum fromString(String value) {
        for (BreakAreaSizeEnum size : values()) {
            if (size.getConfigValue().equals(value)) {
                return size;
            }
        }
        return SIZE_2X1;
    }
    public String getConfigValue() {
        return configValue;
    }
    public int getMinX() {
        return minX;
    }
    public int getMaxX() {
        return maxX;
    }
    public int getMinY() {
        return minY;
    }
    public int getMaxY() {
        return maxY;
    }
}
