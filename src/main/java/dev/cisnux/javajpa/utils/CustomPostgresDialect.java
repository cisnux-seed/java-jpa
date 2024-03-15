package dev.cisnux.javajpa.utils;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.jdbc.BinaryJdbcType;
import org.hibernate.type.descriptor.jdbc.LongVarcharJdbcType;
import java.sql.Types;

public class CustomPostgresDialect extends PostgreSQLDialect {

        public CustomPostgresDialect() {
            super();
        }

        @Override
        protected String columnType(int sqlTypeCode) {
            if (SqlTypes.BLOB == sqlTypeCode) {
                return "bytea";
            }
            if (SqlTypes.CLOB == sqlTypeCode) {
                return "text";
            }
            return super.columnType(sqlTypeCode);
        }

        @Override
        protected String castType(int sqlTypeCode) {
            if (SqlTypes.BLOB == sqlTypeCode) {
                return "bytea";
            }
            if (SqlTypes.CLOB == sqlTypeCode) {
                return "text";
            }
            return super.castType(sqlTypeCode);
        }

        @Override
        public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
            super.contributeTypes(typeContributions, serviceRegistry);
            final var jdbcTypeRegistry = typeContributions.getTypeConfiguration().getJdbcTypeRegistry();
            jdbcTypeRegistry.addDescriptor(Types.BLOB, BinaryJdbcType.INSTANCE);
            jdbcTypeRegistry.addDescriptor(Types.CLOB, LongVarcharJdbcType.INSTANCE);
        }
}
