package net.grzechocinski.android.droidconkrakow.demo9;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.grzechocinski.android.droidconkrakow.R;
import rx.Observable;
import rx.Subscription;

//https://github.com/andrewhr/rxjava-android-example with modifications
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

        Subscription subscribe = messageBodyText
                .map(text -> !text.trim().equals(""))
                .subscribe(Properties.enabledFrom(sendMessage));

        final int maxBodyLength = 200;
        messageBodyText
                .map(text -> maxBodyLength - text.length())
                .map(remainingChars -> getString(R.string.chars, remainingChars, maxBodyLength))
                .subscribe(Properties.textFrom(remainingCharacters));

        sendMessageClick
                .flatMap(unused -> Observable.combineLatest(
                        phoneNumberText,
                        messageBodyText,
                        (phoneNumber1, messageBody1) -> new Message(phoneNumber1, messageBody1))
                        .take(1))
                .subscribe(message -> {
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