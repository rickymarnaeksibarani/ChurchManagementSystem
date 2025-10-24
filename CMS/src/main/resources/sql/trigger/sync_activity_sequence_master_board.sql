CREATE TRIGGER sync_activity_sequence_master_board
AFTER INSERT ON master_board
FOR EACH STATEMENT
EXECUTE FUNCTION sync_activity_sequence_master_board();
