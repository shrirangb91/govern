/**
 * 
 */
package com.govern.LiquibaseUtility.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.govern.LiquibaseUtility.utils.ConnUtil;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * @author Shrirang.Brahmapurikar
 *
 */
@Service
public class LiquibaseService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(LiquibaseService.class);
	
	@Autowired
	private ConnUtil connUtil;
	
	@Async(value = "taskExecutor")
	public CompletableFuture<Liquibase> implementLiquibaseChange(String sqlServerName, String dbName, String datasourceUsername, String datasourcePassword) throws Exception {
		LOGGER.debug("Creating connection to the dB with sqlServer : " + sqlServerName + ";dbName : " + dbName + ";username : " + datasourceUsername + ";password : " + datasourcePassword);
		String connectionString = connUtil.createConnectionString(sqlServerName, dbName);
		LOGGER.debug("Initializing connection object");
	    Connection c = DriverManager.getConnection(connectionString,datasourceUsername,datasourcePassword);
	    LOGGER.debug("Initializing database factory connection");
	    Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
	    Liquibase liquibase = new Liquibase("classpath:db/changelog/changelog-master.xml", new ClassLoaderResourceAccessor(), database);
	    LOGGER.debug("Liquibase object initialized");
	    liquibase.update("");
	    return CompletableFuture.completedFuture(liquibase);
	}
}
