package me.riddhimanadib.formmaster.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import me.riddhimanadib.formmaster.R;
import me.riddhimanadib.formmaster.model.BaseFormElement;
import me.riddhimanadib.formmaster.model.FormCustomView;

public class FormElementCustom extends BaseViewHolder {

    private LinearLayout mLinearLayout;
    private BaseFormElement mFormElement;
    private FormCustomView mFormCustomView;

    public FormElementCustom(View v) {
        super(v);
        mLinearLayout = (LinearLayout) v.findViewById(R.id.formElementCustom);
    }

    @Override
    public void bind(int position, BaseFormElement formElement, final Context context) {
        mFormElement = formElement;
        mFormCustomView = (FormCustomView) mFormElement;
        mLinearLayout.addView(mFormCustomView.getCustomView());
    }

}
