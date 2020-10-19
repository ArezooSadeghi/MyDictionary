package com.example.mydictionary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydictionary.R;
import com.example.mydictionary.database.WordDBHelper;
import com.example.mydictionary.model.Word;

import java.io.File;
import java.util.List;

public class WordListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private WordDBHelper mWordDBHelper;

    public WordListFragment() {

    }

    public static WordListFragment newInstance() {
        WordListFragment fragment = new WordListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word_list, container, false);
        findViews(view);
        checkDatebaseExists();
        initViews();
        setAdapter();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerview_word_list);
        mWordDBHelper = new WordDBHelper(getActivity());
    }

    private void checkDatebaseExists() {
        File database = getActivity().getApplicationContext().getDatabasePath(WordDBHelper.DB_NAME);
        if (database.exists() == false) {
            mWordDBHelper.getReadableDatabase();
            if (mWordDBHelper.copyDatabase(getActivity())) {
                Toast.makeText(getActivity(), "copy database success", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getActivity(), "copy database error", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        }
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setAdapter() {
        List<Word> words = mWordDBHelper.getWords();
        WordListAdapter adapter = new WordListAdapter(words);
        mRecyclerView.setAdapter(adapter);
    }

    private class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordListHolder> {
        private List<Word> mWords;

        public WordListAdapter(List<Word> words) {
            mWords = words;
        }

        public List<Word> getWords() {
            return mWords;
        }

        public void setWords(List<Word> words) {
            mWords = words;
        }

        @NonNull
        @Override
        public WordListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.row_item, parent, false);
            return new WordListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WordListHolder holder, int position) {
            holder.bindWord(mWords.get(position));
        }

        @Override
        public int getItemCount() {
            return mWords.size();
        }

        private class WordListHolder extends RecyclerView.ViewHolder {
            private TextView mTextViewWord;
            private Word mWord;

            public WordListHolder(@NonNull View itemView) {
                super(itemView);
                mTextViewWord = itemView.findViewById(R.id.txt_name_word);
            }

            public void bindWord(Word word) {
                mTextViewWord.setText(word.getMean());
                mWord = word;
            }
        }
    }
}