package com.mati.game.gui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mati.game.R;
import com.mati.game.gui.util.Utils;
import com.nvidia.devtech.NvEventQueueActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class NewChat {
    public Activity main;
    public RecyclerView chat;
    public ChatAdapter adapter;
    public ArrayList<String> chat_lines = new ArrayList<>();

    public NewChat(Activity activity) {
        this.main = activity;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        chat = activity.findViewById(R.id.testChat);
        chat.setLayoutManager(layoutManager);
        chat.setVisibility(View.GONE);

        adapter = new ChatAdapter(activity, chat_lines);
        chat.setAdapter(adapter);

        mLayoutManager.setStackFromEnd(true);
        chat.setLayoutManager(mLayoutManager);

        chat.setOnClickListener(view -> {
            NvEventQueueActivity.getInstance().onShowCppKeyboard();
        });
    }

    public void AddChatMessage(String text) {
        chat.setVisibility(View.VISIBLE);
        /*String optString = jSONObject.optString("i");*/
        adapter.addItem(text);
    }

    public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

        private final LayoutInflater inflater;
        private List<String> chat_lines;

        ChatAdapter(Context context, List<String> chat_lines) {
            this.chat_lines = chat_lines;
            this.inflater = LayoutInflater.from(context);
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.chatline, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String htmlString = chat_lines.get(position);
            holder.chat_line_text.setText(transfromColors(htmlString));

            holder.chatLineTest.setTextColor(Color.parseColor(extractColor(htmlString)));
        }

        @Override
        public int getItemCount() {
            return chat_lines.size();
        }

        public List getItems() {
            return chat_lines;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            final TextView chat_line_text, chatLineTest;
            ViewHolder(View view){
                super(view);
                chat_line_text = view.findViewById(R.id.chat_line_text);
                chatLineTest = view.findViewById(R.id.chat_line_test);

                chat_line_text.setOnClickListener(view1 -> {
                    NvEventQueueActivity.getInstance().onShowCppKeyboard();
                });
            }
        }

        public void addItem(String item) {
            main.runOnUiThread(() -> {
                if(this.chat_lines.size() > 40){
                    this.chat_lines.remove(0);
                    notifyItemRemoved(0);
                }
                this.chat_lines.add(" " + item + " ");
                notifyItemInserted(this.chat_lines.size() - 1);

                if(chat.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    chat.scrollToPosition(this.chat_lines.size() - 1);
                }
            });
        }
    }

    public static Spanned transfromColors(String str) {
        int i;
        LinkedList linkedList = new LinkedList();
        int i2 = 0;
        String str2 = str;
        int i3 = 0;
        for (int i4 = 0; i4 < str2.length(); i4++) {
            if (str2.charAt(i4) == '{' && (i = i4 + 7) < str2.length()) {
                StringBuilder sb = new StringBuilder();
                sb.append("#");
                int i5 = i4 + 1;
                sb.append(str2.substring(i5, i));
                linkedList.addLast(sb.toString());
                str2 = str2.substring(0, i5) + "repl" + i3 + str2.substring(i);
                i3++;
            }
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            if (i2 == 0) {
                str2 = str2.replaceAll(Pattern.quote("{repl" + i2 + "}"), "<font color='" + str3 + "'>");
            } else {
                str2 = str2.replaceAll(Pattern.quote("{repl" + i2 + "}"), "</font><font color='" + str3 + "'>");
            }
            i2++;
        }
        if (linkedList.size() >= 1) {
            str2 = str2 + "</font>";
        }
        return Html.fromHtml(str2.replaceAll(Pattern.quote("\n"), "<br>"));
    }

    private String extractColor(String input) {
        String colorValue = StringUtils.substringBetween(input, "{", "}");

        if (colorValue != null && colorValue.length() == 6) {
            return "#" + colorValue;
        } else {
            return "#ffff00";
        }
    }

    public void ShowChat() {
        Utils.ShowLayout(chat, true);
    }

    public void HideChat() {
        Utils.HideLayout(chat, false);
    }

}
