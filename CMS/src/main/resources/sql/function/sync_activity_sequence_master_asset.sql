CREATE OR REPLACE FUNCTION sync_activity_sequence_master_asset()
RETURNS TRIGGER AS $$
BEGIN
  -- Update sequence agar sama dengan max(id)
  PERFORM setval('master_asset_seq', (SELECT MAX(id) FROM master_asset), true);
  RETURN NULL;
END;
$$ LANGUAGE plpgsql;
