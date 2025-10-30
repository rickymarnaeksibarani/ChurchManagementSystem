package ChurchManagementSystem.CMS.modules.authentication.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class BlackListToken {
    private final Set<String> blackList = ConcurrentHashMap.newKeySet();


    public void add(String token) {
        blackList.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blackList.contains(token);
    }
}
