package br.com.jobs.sql;

import java.util.UUID;

public interface JobsDataRepository {
    void setPlayerProfession(UUID uuid, String name, String profession);
    void setPlayerWorking(UUID uuid, String working);
    boolean hasPlayer(UUID uuid);
    boolean hasProfession(String profession);
    String hasWorking(UUID playerUUID);
    String getPlayerProfession(UUID playerUUID);
}