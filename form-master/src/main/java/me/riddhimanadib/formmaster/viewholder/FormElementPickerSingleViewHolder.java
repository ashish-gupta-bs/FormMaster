package me.riddhimanadib.formmaster.viewholder;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import me.riddhimanadib.formmaster.R;
import me.riddhimanadib.formmaster.listener.ReloadListener;
import me.riddhimanadib.formmaster.model.BaseFormElement;
import me.riddhimanadib.formmaster.model.FormElementPickerSingle;

/**
 * Created by Riddhi - Rudra on 30-Jul-17.
 */

public class FormElementPickerSingleViewHolder extends BaseViewHolder {

    private AppCompatTextView mTextViewTitle;
    private AppCompatEditText mEditTextValue;
    private ReloadListener mReloadListener;
    private BaseFormElement mFormElement;
    private FormElementPickerSingle mFormElementPickerSingle;
    private BottomSheetAdapter mSheetAdapter;
    private BottomSheetDialog mBottomSheetDialog;
    private LayoutInflater mLayoutInflater;
    private int mPosition;

    public FormElementPickerSingleViewHolder(View v, Context context, ReloadListener reloadListener) {
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
        mFormElementPickerSingle = (FormElementPickerSingle) mFormElement;
        mTextViewTitle.setText(formElement.getTitle());
        mEditTextValue.setText(formElement.getValue());
        mEditTextValue.setHint(context.getResources().getString(R.string.please_select));
        mEditTextValue.setFocusableInTouchMode(false);

        // reformat the options in format needed
        final CharSequence[] options = new CharSequence[mFormElementPickerSingle.getOptions().size()];
        for (int i = 0; i < mFormElementPickerSingle.getOptions().size(); i++) {
            options[i] = mFormElementPickerSingle.getOptions().get(i);
        }


        mSheetAdapter = new BottomSheetAdapter(context, options);

        View inflateView = mLayoutInflater.inflate(R.layout.list_options, null);
        RecyclerView recyclerView = (RecyclerView) inflateView.findViewById(R.id.recycler_view);

        recyclerView.setPadding(0,0,0,0);
        recyclerView.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mSheetAdapter);
        mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setContentView(inflateView);

        mSheetAdapter.setOnItemClickListener(new BottomSheetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CharSequence value, int position) {
                mBottomSheetDialog.dismiss();
                mEditTextValue.setText(value);
                mFormElementPickerSingle.setValue(value.toString());
                mReloadListener.updateValue(mPosition, value.toString());
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

    }

}
