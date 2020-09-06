package ru.flystar.travelrk.tools;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.flystar.travelrk.domain.persistents.User;
import ru.flystar.travelrk.service.UserService;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 19.03.2018.
 */
@Log4j
@Component
public class StringTool {
    private static UserService userService;
    private static final char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static String genPath() {
        return genRandomLowerStr(8);
    }

    public static String genRandomLowerStr(int len) {
        StringBuilder pathSB = new StringBuilder();
        for (int i = 0; i <= len-1; i++) {
            pathSB.append(alphabet[(int) (Math.random() * alphabet.length)]);
        }
        return pathSB.toString();
    }

    public static String redeableSize(long size) {
        if (size==0) return "0 B";
        String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int unitIndex = (int) (Math.log10(size) / 3);
        double unitValue = 1 << (unitIndex * 10);
        return new DecimalFormat("#,##0.#").format(size / unitValue) + " " + units[unitIndex];
    }

    public static Long diffDays(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String getFreeSize() {
        File file = new File("/");
        long totalSpace = file.getTotalSpace() / (1024 * 1024);
        long freeSpace = file.getFreeSpace() / (1024 * 1024);
        return "Свободно: "+freeSpace+" / "+totalSpace+" Mb";
    }

    public static String getCurrentUserFio() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        String fio = "";
        log.info(login);
        if (userService!=null && login != null) {
            User user = userService.getUserByLogin(login);
            log.info(user);
            fio = user.getFio();
            log.info(fio);
        }
        return fio;
    }

    @Autowired
    public void setUserService(UserService userService) {
        StringTool.userService = userService;
    }
}
