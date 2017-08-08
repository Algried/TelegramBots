import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by nsmagin on 8/7/2017.
 */
public class Main {

    public static void main(String[] args) {


        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new DiceBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }
}
