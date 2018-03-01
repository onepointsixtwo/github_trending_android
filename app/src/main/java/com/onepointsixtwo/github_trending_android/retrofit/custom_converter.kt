package com.onepointsixtwo.github_trending_android.retrofit

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type

@Retention(AnnotationRetention.RUNTIME)
annotation class JSON

@Retention(AnnotationRetention.RUNTIME)
annotation class SCALAR

/**
 * This class is required because we have an endpoint which returns a plain string, so this allows
 * us to parse a string type or a JSON type based on the above annotations on the Retrofit interface
 */
class CustomConverterFactory(val scalarsConverterFactory: ScalarsConverterFactory,
                      val gsonConverterFactory: GsonConverterFactory): Converter.Factory() {

    private enum class AnnotationType {
        JSON,
        SCALAR
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out Annotation>?, methodAnnotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
        if (type != null && parameterAnnotations != null && methodAnnotations != null && retrofit != null) {
            val annotationType = annotationTypeFromAnnotations(methodAnnotations)
            if (annotationType == AnnotationType.JSON) {
                return gsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
            }
            return scalarsConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
        }
        return null
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        if (type != null && annotations != null && retrofit != null) {
            val annotationType = annotationTypeFromAnnotations(annotations)
            if (annotationType == AnnotationType.JSON) {
                return gsonConverterFactory.responseBodyConverter(type, annotations, retrofit)
            }
            return scalarsConverterFactory.responseBodyConverter(type, annotations, retrofit)
        }
        return null
    }

    private fun annotationTypeFromAnnotations(annotations: Array<out Annotation>?): AnnotationType {
        annotations?.forEach {
            if (it.annotationClass == JSON::class) {
                return AnnotationType.JSON
            }
        }
        return AnnotationType.SCALAR
    }
}