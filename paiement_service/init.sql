DO $$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_database
      WHERE datname = 'recommandationsdb'
   ) THEN
      CREATE DATABASE recommandationsdb;
   END IF;
END
$$;
