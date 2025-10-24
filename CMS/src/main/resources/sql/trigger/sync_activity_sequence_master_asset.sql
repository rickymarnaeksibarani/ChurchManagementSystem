CREATE TRIGGER sync_activity_sequence_master_asset
AFTER INSERT ON master_asset
FOR EACH STATEMENT
EXECUTE FUNCTION sync_activity_sequence_master_asset();
