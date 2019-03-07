package com.myapp.underscore.imc;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.myapp.underscore.imc.controller.TestActivity;


public class FormFragment extends Fragment {


    private onFormFragmentListener mListener;

    private EditText editText;
    private Button button;

    public FormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TestActivity.fragmentManager
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_form, container, false);
        editText = view.findViewById(R.id.edt_nom);
        button = view.findViewById(R.id.btn_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOkButtonClicked(editText.getText().toString());
            }
        });
        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onOkButtonClicked(String value) {
        if (mListener != null) {
            mListener.onFragmentInteraction(value);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onFormFragmentListener) {
            mListener = (onFormFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onFormFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface onFormFragmentListener {
        void onFragmentInteraction(String value);
    }
}
