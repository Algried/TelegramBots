import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;



/**
 * Created by nsmagin on 12.07.2017.
 */

public class DiceBot extends TelegramLongPollingBot {



    @Override
    public void onUpdateReceived(Update update) {




        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().matches("/r[\\d]*d[\\d]*|/r[\\d]*d[\\d]*[ ]*[\\+|\\-][ ]*[\\d]*") ) {

            String roll = update.getMessage().getText();
            int count = 1;
            count = roll.split("r")[1].split("d")[0].isEmpty()?1:Integer.valueOf(roll.split("r")[1].split("d")[0]);
            int dice = 1;
            int constant = 0 ;
            String oper = "";
            if (roll.contains("-")) {
                oper = "-";
                dice = Integer.valueOf(roll.split("r")[1].split("d")[1].split("-")[0].trim());
                constant=-Integer.valueOf(roll.split("\\-")[1].trim());
            } else if (roll.contains("+")) {
                oper = "+";
                dice = Integer.valueOf(roll.split("r")[1].split("d")[1].split("\\+")[0].trim());
                constant = Integer.valueOf(roll.split("\\+")[1].trim());
            }
            else
                dice = Integer.valueOf(roll.split("r")[1].split("d")[1]);


            SendMessage message;
            if (count > 0 && dice > 1) {
                HashMap<Integer, Integer> result = new HashMap<>();
                result = rollDice(result, count, dice);
                String resultString = " (";
                int resultNumber = 0;
                for (Integer i : result.values()) {
                    resultString += i + ", ";
                    resultNumber += i;
                }
                resultString = resultString.substring(0, resultString.length() - 2);
                resultString += ")";
                message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(oper.isEmpty()?String.valueOf(resultNumber)+"\r\n" +resultString:String.valueOf(resultNumber + constant) + " = "+ resultNumber + " " + oper + " " + Math.abs(constant) + "\r\n" +resultString );

                try {
                    sendMessage(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
            else {
//                message = new SendMessage() // Create a SendMessage object with mandatory fields
//                        .setChatId(update.getMessage().getChatId())
//                        .setText("Некорректный ввод");
            }


        }




    }

    private HashMap <Integer,Integer> rollDice(HashMap <Integer,Integer> map, int count, int dice) {
        if (count == 0)
            return map;
        else
        {
            map.put(count, ThreadLocalRandom.current().nextInt(1, dice + 1));
            return rollDice(map, count-1,dice);
        }
    }

    @Override
    public String getBotUsername() {
        return "DiceRolling_bot";
    }

    @Override
    public String getBotToken() {
        return "";
    }





}
