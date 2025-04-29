package br.com.jobs.Sql;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static br.com.jobs.Jobs.getSqlConnectionYML;

public class SqlConnection {
    public static void main() {
        ConfigurationSection cf = getSqlConnectionYML().getConfig().getConfigurationSection("MySql");
        String url = cf.getString("URL" + ":").toUpperCase();
        String user = cf.getString("USER").toUpperCase();
        String pass = cf.getString("PASSWORD");
        String port = cf.getString("PORT");
        //TODO Remover após os testes
        if (cf == null) Bukkit.getConsoleSender().sendMessage("§cErr. path MySql não encontrado");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(STR."JDBC:MYSQL://\{url}\{port}", user, pass);
        } catch (ClassNotFoundException e) {
            Bukkit.getConsoleSender().sendMessage("Não existe esse drive. Class.SqlConnection ");
        } catch (SQLException e){
            Bukkit.getConsoleSender().sendMessage(STR."Ocorreu um erro: \{e.getMessage()}");
        }
    }

}
