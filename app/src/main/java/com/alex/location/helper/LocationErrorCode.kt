package com.alex.location.helper

import androidx.annotation.IntDef


@IntDef(
    LocationErrorCode.UNKNOWN_EXCEPTION,
    LocationErrorCode.PERMISSION_EXCEPTION,
    LocationErrorCode.PROVIDER_EXCEPTION
)
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.CLASS,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FUNCTION
)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class LocationErrorCode {

    companion object {

        /**
         * Unknown Exception
         */
        const val UNKNOWN_EXCEPTION = 1001

        /**
         * Permission Exception (No permission to get location information)
         */
        const val PERMISSION_EXCEPTION = 1002

        /**
         * Provider Exception (This problem usually occurs when the location information master switch is off.)
         */
        const val PROVIDER_EXCEPTION = 1003

    }
}
