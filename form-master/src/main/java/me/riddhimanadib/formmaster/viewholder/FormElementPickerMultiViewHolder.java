package me.riddhimanadib.formmaster.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import me.riddhimanadib.formmaster.R;
import me.riddhimanadib.formmaster.listener.ReloadListener;
import me.riddhimanadib.formmaster.model.BaseFormElement;
import me.riddhimanadib.formmaster.model.FormElementPickerMulti;

/**
 * Created by Riddhi - Rudra on 30-Jul-17.
 */

public class FormElementPickerMultiViewHolder extends BaseViewHolder {

    private AppCompatTextView mTextViewTitle;
    private AppCompatEditText mEditTextValue;
    private ReloadListener mReloadListener;
    private BaseFormElement mFormElement;
    private FormElementPickerMulti mFormElementPickerMulti;
    private BottomSheetAdapter mSheetAdapter;
    private BottomSheetDialog mBottomSheetDialog;
    private LayoutInflater mLayoutInflater;
    private int mPosition;

    public FormElementPickerMultiViewHolder(View v, Context context, ReloadListener reloadListener) {
        super(v);
        mTextViewTitle = (AppCompatTextView) v.findViewById(R.id.formElementTitle);
        mEditTextValue = (AppCompatEditText) v.findViewById(R.id.formElementValue);
        mReloadListener = reloadListener;
        mLayoutInflater = ((Activity)context).getLayoutInflater();
    }

    @Override
    public void bind(final int position, BaseFormElement formElement, final Context context) {
        mFormElement = formElement;
        mPosition = position;
        mFormElementPickerMulti = (FormElementPickerMulti) mFormElement;

        mTextViewTitle.setText(formElement.getTitle());
        mEditTextValue.setText(formElement.getValue());
        mEditTextValue.setHint(context.getResources().getString(R.string.please_select));
        mEditTextValue.setFocusableInTouchMode(false);

        // reformat the options in format needed
        final CharSequence[] options = new CharSequence[mFormElementPickerMulti.getOptions().size()];
        final boolean[] optionsSelected = new boolean[mFormElementPickerMulti.getOptions().size()];
        final ArrayList<Integer> mSelectedItems = new ArrayList();

        for (int i = 0; i < mFormElementPickerMulti.getOptions().size(); i++) {
            options[i] = mFormElementPickerMulti.getOptions().get(i);
            optionsSelected[i] = false;

            if (mFormElementPickerMulti.getOptionsSelected().contains(options[i])) {
                optionsSelected[i] = true;
                mSelectedItems.add(i);
            }
        }

        mSheetAdapter = new BottomSheetAdapter(context, options, optionsSelected);

        View inflateView = mLayoutInflater.inflate(R.layout.list_options, null);
        RecyclerView recyclerView = (RecyclerView) inflateView.findViewById(R.id.recycler_view);

        recyclerView.setPadding(0, 0, 0, 80);
        inflateView.findViewById(R.id.done_button).setVisibility(View.VISIBLE);
        recyclerView.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mSheetAdapter);
        mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setContentView(inflateView);

        mSheetAdapter.setOnItemClickListener(new BottomSheetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CharSequence value, int itemPosition) {
               if (mSelectedItems.contains(itemPosition)) {
                    // Else, if the item is already in the array, remove it
                    mSelectedItems.remove(Integer.valueOf(itemPosition));
                }else{
                    mSelectedItems.add(itemPosition);
                }
            }
        });

        mEditTextValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) v.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBottomSheetDialog.show();
                    }
                },100);

            }
        });


        inflateView.findViewById(R.id.done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "";
                for (int i = 0; i < mSelectedItems.size(); i++) {
                    s += options[mSelectedItems.get(i)];

                    if (i < mSelectedItems.size() - 1) {
                        s += ", ";
                    }
                }
                mBottomSheetDialog.dismiss();
                mEditTextValue.setText(s);
                mReloadListener.updateValue(mPosition, s);
            }
        });
    }

}
