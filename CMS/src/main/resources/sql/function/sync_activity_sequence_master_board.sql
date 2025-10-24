CREATE OR REPLACE FUNCTION sync_activity_sequence_master_board()
RETURNS TRIGGER AS $$
BEGIN
  -- Update sequence agar sama dengan max(id)
  PERFORM setval('master_board_id_seq', (SELECT MAX(id) FROM master_asset), true);
  RETURN NULL;
END;
$$ LANGUAGE plpgsql;
