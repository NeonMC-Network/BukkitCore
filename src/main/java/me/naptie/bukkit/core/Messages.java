package me.naptie.bukkit.core;

import me.naptie.bukkit.core.utils.CU;
import me.naptie.bukkit.player.utils.ConfigManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Messages {

    public static final String[] ENGLISH_BAD_WORDS = {"anus", "arse", "arsehole", "ass", "axwound", "bampot", "bastard", "beaner", "bitch", "blow job", "blowjob", "bollocks", "bollox", "boner", "butt plug", "butt-pirate", "camel toe", "carpetmuncher", "chesticle", "chinc", "chink", "choad", "chode", "clit", "cock", "coochie", "coochy", "coon", "cooter", "cracker", "cum", "cunnie", "cunnilingus", "cunt", "dago", "damn", "deggo", "dick", "dike", "dildo", "doochbag", "dookie", "douche", "dyte", "fag", "fellatio", "feltch", "flamer", "fuck", "fudgepacker", "gay", "gooch", "gook", "gringo", "guido", "handjob", "hard on", "heeb", "homo", "honkey", "humping", "jagoff", "jerk off", "jigaboo", "jizz", "jungle bunny", "junglebunny", "kike", "kooch", "kootch", "kraut", "kunt", "kyte", "lesbian", "lesbo", "lezzie", "mcfagget", "mick", "minge", "muff", "munging", "negro", "nigaboo", "nigga", "nigger", "niglet", "nmsl", "nut sack", "nutsack", "paki", "panooch", "pecker", "penis", "piss", "polesmoker", "pollock", "poon", "porch monkey", "porchmonkey", "prick", "punanny", "punta", "pussies", "pussy", "puto", "queef", "queer", "renob", "rimjob", "ruski", "sand nigger", "sandnigger", "schlong", "scrote", "shit", "shiz", "skank", "skeet", "slut", "smeg", "snatch", "spic", "splooge", "tard", "testicle", "thundercunt", "tit", "twat", "va-j-j", "vag", "vajayjay", "vjayjay", "wank", "wetback", "whore", "wop"};
    public static final String[] CHINESE_BAD_WORDS = {"cnm", "faq", "fvck", "婊", "肏", "操你妈", "草泥马", "草你妈", "艹你妈", "啪啪啪", "操你媽", "草泥馬", "草你媽", "艹你媽", "他妈的", "他媽的", "tmd", "打飞机", "打飛機", "傻逼", "傻比", "妈死", "媽死", "nmsl", "全家死", "全家都死"};

    public static String getMessage(String language, String message) {
        return CU.t(YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder(), language + ".yml")).getString(message));
    }

    public static String getMessage(OfflinePlayer player, String message) {
        return CU.t(getLanguage(player).getString(message));
    }

    private static YamlConfiguration getLanguage(OfflinePlayer player) {
        File locale = new File(Main.getInstance().getDataFolder(), ConfigManager.getLanguageName(player) + ".yml");
        return YamlConfiguration.loadConfiguration(locale);
    }

}
