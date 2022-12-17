package com.mycompany.myapp.domain.idgenerator;

import java.io.Serializable;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

public class IDGenerator extends SequenceStyleGenerator {

    public static final String VALUE_PREFIX_PARAMETER = "prefix";
    public static final String VALUE_PREFIX_DEFAULT = "";
    private String prefix;

    public static final String NUMBER_FORMAT_PARAMETER = "format";
    public static final String NUMBER_FORMAT_DEFAULT = "%d";
    private String format;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        return prefix + String.format(format, super.generate(session, obj));
    }

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, properties, serviceRegistry);
        prefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER, properties, VALUE_PREFIX_DEFAULT);
        format = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, properties, NUMBER_FORMAT_DEFAULT);
    }
}
