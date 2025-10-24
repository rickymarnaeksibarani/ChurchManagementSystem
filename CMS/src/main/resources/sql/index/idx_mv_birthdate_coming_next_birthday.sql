CREATE UNIQUE INDEX idx_mv_birthdate_coming_id
ON materialized_view_birthdate_coming (id);

-- after create unique index dont forget to refresh before mv in query sql
-- execute this query:
-- REFRESH MATERIALIZED VIEW CONCURRENTLY materialized_view_birthdate_coming;