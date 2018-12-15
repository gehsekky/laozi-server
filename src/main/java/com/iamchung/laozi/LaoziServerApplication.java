package com.iamchung.laozi;

import com.iamchung.laozi.db.dao.DocumentDao;
import com.iamchung.laozi.db.dao.DocumentTagDao;
import com.iamchung.laozi.db.dao.TagDao;
import com.iamchung.laozi.db.models.Document;
import com.iamchung.laozi.db.models.DocumentTag;
import com.iamchung.laozi.db.models.Tag;
import com.iamchung.laozi.db.models.User;
import com.iamchung.laozi.health.AppHealthCheck;
import com.iamchung.laozi.resources.AdminResource;
import com.iamchung.laozi.resources.DocumentResource;
import com.iamchung.laozi.resources.PingResource;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class LaoziServerApplication extends Application<LaoziServerConfiguration> {

    private final HibernateBundle<LaoziServerConfiguration> hibernate = new HibernateBundle<LaoziServerConfiguration>(
            Tag.class,
            User.class,
            Document.class,
            DocumentTag.class
    ) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(LaoziServerConfiguration laoziServerConfiguration) {
            return laoziServerConfiguration.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception {
        new LaoziServerApplication().run(args);
    }

    @Override
    public String getName() {
        return "LaoziServer";
    }

    @Override
    public void initialize(final Bootstrap<LaoziServerConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final LaoziServerConfiguration configuration,
                    final Environment environment) {

        // register health checks
        environment.healthChecks().register("appHealthCheck", new AppHealthCheck());

        // register resources
        environment.jersey().register(new PingResource());
        environment.jersey().register(new AdminResource(
                configuration,
                new DocumentDao(hibernate.getSessionFactory()),
                new TagDao(hibernate.getSessionFactory()),
                new DocumentTagDao(hibernate.getSessionFactory())
        ));
        environment.jersey().register(new DocumentResource(new DocumentDao(hibernate.getSessionFactory())));
    }

}
