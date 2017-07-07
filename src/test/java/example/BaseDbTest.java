package example;

import example.util.DataBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.unitils.UnitilsJUnit4;
import org.unitils.core.Unitils;
import org.unitils.core.dbsupport.DefaultSQLHandler;
import org.unitils.core.util.ConfigUtils;
import org.unitils.database.DatabaseModule;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.database.config.DataSourceFactory;
import org.unitils.dbmaintainer.DBMaintainer;
import org.unitils.dbmaintainer.clean.DBCleaner;
import org.unitils.dbmaintainer.structure.ConstraintsDisabler;
import org.unitils.dbmaintainer.util.DatabaseModuleConfigUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public class BaseDbTest extends UnitilsJUnit4 {
    @TestDataSource
    public DataSource dataSource;

  /*  @Rule
    public TestWatchman unitilsLuncher = new TestWatchman() {
        @Override
        public void starting(FrameworkMethod method) {
//             Unitils.getInstance().getTestListener().beforeTestSetUp(BaseDbTest.this, method.getMethod());
            Unitils.getInstance().getTestListener().beforeTestMethod(BaseDbTest.this, method.getMethod());
        }

        @Override
        public void finished(FrameworkMethod method) {
            Unitils.getInstance().getTestListener().afterTestMethod(BaseDbTest.this, method.getMethod(), null);
//            Unitils.getInstance().getTestListener().afterTestTearDown(BaseDbTest.this, method.getMethod());
        }
    };*/

    @Before
    public void baseSetUp() {
        if (dataSource == null) {
            Unitils unitils = Unitils.getInstance();
            Properties configuration = unitils.getConfiguration();
            configuration.setProperty(DBMaintainer.PROPKEY_KEEP_RETRYING_AFTER_ERROR_ENABLED, "TRUE");
            DataSourceFactory dataSourceFactory = ConfigUtils.getConfiguredInstanceOf(DataSourceFactory.class, configuration);
            dataSource = dataSourceFactory.createDataSource();

        }
        DataBase.setDs(dataSource);
    }

    @After
    public void baseTearDown() {
    }
}