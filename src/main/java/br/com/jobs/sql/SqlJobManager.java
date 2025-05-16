package br.com.jobs.sql;
import java.sql.Connection;
import java.util.UUID;
import static br.com.jobs.utils.TextUtils.warnLoggers;
import static br.com.jobs.utils.messages.MessagesHandle.InvalidUUID;

public class SqlJobManager {
    private final JobsDataRepository repository;
    private static SqlJobManager instance;


    public static void init(Connection connection) {
        if (instance == null) {
            instance = new SqlJobManager(connection);
        }
    }


    public static SqlJobManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Error in database");
        }
        return instance;
    }

    public SqlJobManager(Connection connection) {
        this.repository = DatabaseFactory.createJobRepository(new SimpleConnectionProvider(connection) {
            @Override
            public boolean isConnectionValid() {
                return false;
            }
        });
    }

    public void setPlayerProfession(UUID uuid, String name, String profession) {
        repository.setPlayerProfession(uuid, name, profession);
    }

    public void setPlayerWorking(UUID uuid, String working) {
        repository.setPlayerWorking(uuid, working);
    }

    public boolean hasPlayer(UUID uuid) {
        return repository.hasPlayer(uuid);
    }

    public boolean hasProfession(String uuidString) {
        try {
            UUID uuid = UUID.fromString(uuidString);
            String profession = repository.getPlayerProfession(uuid);
            return profession != null && !profession.isEmpty();
        } catch (IllegalArgumentException e) {
            warnLoggers(InvalidUUID + uuidString);
            return false;
        }
    }
    public String hasWorking(UUID playerUUID) {
        return repository.hasWorking(playerUUID);
    }

    public String getPlayerProfession(UUID playerUUID) {
        return repository.getPlayerProfession(playerUUID);
    }

    private static abstract class SimpleConnectionProvider implements DatabaseManager {
        private final Connection connection;

        public SimpleConnectionProvider(Connection connection) {
            this.connection = connection;
        }

        @Override
        public Connection getConnection() {
            return connection;
        }

        @Override
        public void connect() {
        }

        @Override
        public void disconnect() {
        }
    }
}