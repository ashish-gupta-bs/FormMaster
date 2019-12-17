package me.riddhimanadib.formmaster.model;

import android.view.View;

public class FormCustomView extends BaseFormElement {

    private View customView;
    public FormCustomView() {
    }

    /**
     * static method to create instance with title
     * @param customView
     * @return
     */
    public static FormCustomView createInstance(View customView) {
        FormCustomView formCustomView = new FormCustomView();
        formCustomView.setType(BaseFormElement.TYPE_CUSTOM);
        formCustomView.setCustomView(customView);
        return formCustomView;
    }

    // custom setter
    public FormCustomView setCustomView(View customView) {
        this.customView = customView;
        return this;
    }

    // custom getter
    public View getCustomView() {
        return this.customView;
    }

}
