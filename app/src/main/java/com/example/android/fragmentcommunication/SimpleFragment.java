package com.example.android.fragmentcommunication;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.android.fragmentcommunication.R;

public class SimpleFragment extends Fragment {

    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NONE = 2;

    public int mRadioButtonChoice = NONE;

    private static final String CHOICE = "choice";

    OnFragmentInteractionListener mListener;

    public SimpleFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_simple, container, false);

        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        if (getArguments().containsKey(CHOICE)) {

            mRadioButtonChoice = getArguments().getInt(CHOICE);

            if (mRadioButtonChoice != NONE) {
                radioGroup.check
                        (radioGroup.getChildAt(mRadioButtonChoice).getId());
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(RadioGroup group, int checkedId) {
                  View radioButton = radioGroup.findViewById(checkedId);
                  int index = radioGroup.indexOfChild(radioButton);
                  TextView textView =
                          rootView.findViewById(R.id.fragment_header);
                  switch (index) {
                      case YES:
                          textView.setText(R.string.yes_message);
                          mRadioButtonChoice = YES;
                          mListener.onRadioButtonChoice(YES);
                          break;
                      case NO:
                          textView.setText(R.string.no_message);
                          mRadioButtonChoice = NO;
                          mListener.onRadioButtonChoice(NO);
                          break;
                      default:
                          mRadioButtonChoice = NONE;
                          mListener.onRadioButtonChoice(NONE);
                          break;
                  }
              }
          });

        return rootView;
    }

    interface OnFragmentInteractionListener {
        void onRadioButtonChoice(int choice);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + getResources().getString(R.string.exception_message));
        }
    }

    public static SimpleFragment newInstance(int choice) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE, choice);
        fragment.setArguments(arguments);
        return fragment;
    }

}
