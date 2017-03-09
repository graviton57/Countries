package com.havrylyuk.countries.model;

import android.support.annotation.Nullable;

/**
 * Created by Igor Havrylyuk on 08.03.2017.
 */

public class ApiResponse {

    @Nullable
    private Status status;

    public ApiResponse() {
    }

    @Nullable
    public Status getStatus() {
        return status;
    }


}
