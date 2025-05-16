package br.com.jobs.sql;
import java.io.File;

public interface DatabaseBackupService {
    File createBackup(File backupDir, String filename);
    boolean restoreFromBackup(File backupFile);
    File[] listBackups(File backupDir);
}
