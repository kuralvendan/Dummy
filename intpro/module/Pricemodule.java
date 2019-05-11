package com.binary2quantumtechbase.andapp.intpro.module;

import android.content.Context;

/**
 * Created by HP on 1/7/2019.
 */

public class Pricemodule {

    String usize,uthickness,uprice;
    Context context;

    public Pricemodule(String sthickness, String ssize, String sprice) {
        this.uthickness=sthickness;
        this.usize=ssize;
        this.uprice=sprice;
    }

    public String getUsize() {
        return usize;
    }

    public String getUthickness() {
        return uthickness;
    }

    public String getUprice() {
        return uprice;
    }

    public Context getContext() {
        return context;
    }

    public void setUsize(String usize) {
        this.usize = usize;
    }

    public void setUthickness(String uthickness) {
        this.uthickness = uthickness;
    }

    public void setUprice(String uprice) {
        this.uprice = uprice;
    }

    public void setContext(Context context) {
        this.context = context;
    }


}
