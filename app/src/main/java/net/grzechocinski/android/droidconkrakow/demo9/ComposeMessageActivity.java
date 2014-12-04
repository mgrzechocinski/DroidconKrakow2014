package net.grzechocinski.android.droidconkrakow.demo9;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.grzechocinski.android.droidconkrakow.R;
import rx.Observable;
import rx.android.events.OnTextChangeEvent;
import rx.android.observables.ViewObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

//https://github.com/andrewhr/rxjava-android-example
public class ComposeMessageActivity extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_message);

        final EditText phoneNumber = (EditText) findViewById(R.id.phone_number);
        final EditText messageBody = (EditText) findViewById(R.id.message_body);
        final TextView remainingCharacters = (TextView) findViewById(R.id.remaining_characters);
        final Button sendMessage = (Button) findViewById(R.id.send_message);

        final Observable<String> phoneNumberText  = Events.text(phoneNumber);
        final Observable<String> messageBodyText  = Events.text(messageBody);
        final Observable<Object> sendMessageClick = Events.click(sendMessage);

        phoneNumberText.subscribe(new Action1<String>() {
            @Override
            public void call(String text) {
                System.out.println(text);
            }
        });

        // send button is only enabled when we have some message body content
        messageBodyText
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String text) {
                        return !text.trim().equals("");
                    }
                })
                .subscribe(Properties.enabledFrom(sendMessage));

        // counts remaining characters of body
        final int maxBodyLength = 200;
        messageBodyText
                .map(new Func1<String, Integer>() {
                    @Override public Integer call(String text) {
                        return maxBodyLength - text.length();
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override public String call(Integer remainingChars) {
                        return getString(R.string.chars, remainingChars, maxBodyLength);
                    }
                })
                .subscribe(Properties.textFrom(remainingCharacters));

        // transforms clicks into messages, using the most recent information
        // for messages with blank phone number take focus to phone number edit text
        // for complete messages, we show it as a Toast and cleanup message body edit text
        // side-effects yay!
        sendMessageClick.subscribe(new Action1<Object>() {
            @Override
            public void call(Object onClickEvent) {
                System.out.println("Click!!!! : ");
            }
        });


        sendMessageClick
                .flatMap(new Func1<Object, Observable<Message>>() {
                    @Override
                    public Observable<Message> call(Object unused) {
                        return Observable.combineLatest(
                                phoneNumberText,
                                messageBodyText,
                                new Func2<String, String, Message>() {
                                    @Override
                                    public Message call(String phoneNumber, String messageBody) {
                                        return new Message(phoneNumber, messageBody);
                                    }
                                })
                                .take(1);
                    }
                })
                .subscribe(new Action1<Message>() {
                    @Override public void call(Message message) {
                        if (message.phoneNumber.toString().trim().equals("")) {
                            phoneNumber.requestFocus();
                        } else {
                            messageBody.setText("");
                            Toast.makeText(
                                    ComposeMessageActivity.this,
                                    message.toString(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
    }

    class Message {
        private final CharSequence phoneNumber;
        private final CharSequence messageBody;

        public Message(CharSequence phoneNumber, CharSequence messageBody) {
            this.phoneNumber = phoneNumber;
            this.messageBody = messageBody;
        }

        @Override public String toString() {
            return phoneNumber + " : " + messageBody;
        }
    }
}