package ChurchManagementSystem.CMS.core.utils;

import java.time.LocalDate;

public class GenerateAssetNumber {
    public static String generateAssetNumber(String assetCode, long countData) {
        return assetCode + "/" + LocalDate.now().getYear() + "/" + String.format("%03d", countData);
    }
}
